package com.dcims.backend.sso;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SsoSessionService {

    @Value("${sso.api.base}")
    private String ssoApiBase;

    @Value("${sso.username}")
    private String ssoUsername;

    @Value("${sso.password}")
    private String ssoPassword;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final long TTL_MILLIS = 5 * 60 * 1000;
    private static final long LOGIN_CODE_TTL_MILLIS = 60 * 1000;

    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private final Map<String, LoginCodeEntry> loginCodes = new ConcurrentHashMap<>();

    private static class CacheEntry {
        SsoIdentity identity;
        long expiresAt;
    }

    private static class LoginCodeEntry {
        String token;
        long expiresAt;
    }

    private String basicAuthHeader() {
        String raw = ssoUsername + ":" + ssoPassword;
        return "Basic " + Base64.getEncoder().encodeToString(raw.getBytes());
    }

    private HttpHeaders ssoHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("SSO-TOKEN", token);
        headers.set("Authorization", basicAuthHeader());
        headers.set("Accept", "application/json");
        return headers;
    }

    public SsoResult fetchIdentity(String token) {
        Map<String, Object> tokenData;
        try {
            HttpEntity<Void> entity = new HttpEntity<>(ssoHeaders(token));
            ResponseEntity<Map> response = restTemplate.exchange(
                    ssoApiBase + "/TokenDetail", HttpMethod.GET, entity, Map.class
            );
            tokenData = response.getBody();
        } catch (Exception e) {
            return SsoResult.failure("SSO_UNREACHABLE");
        }

        if (tokenData == null) {
            return SsoResult.failure("BAD_RESPONSE");
        }

        String ssoId = (String) tokenData.get("sAMAccountName");
        if (ssoId == null) {
            return SsoResult.failure("NO_SSO_ID");
        }

        SsoIdentity identity = new SsoIdentity();
        identity.setSsoId(ssoId);
        identity.setUserType(String.valueOf(tokenData.getOrDefault("UserType", "")).trim().toUpperCase());

        try {
            HttpEntity<Void> entity = new HttpEntity<>(ssoHeaders(token));
            ResponseEntity<Map> profileResponse = restTemplate.exchange(
                    ssoApiBase + "/Profile/" + ssoId, HttpMethod.GET, entity, Map.class
            );
            Map<String, Object> p = profileResponse.getBody();
            if (p != null) {
                identity.setDepartment(String.valueOf(p.getOrDefault("department", "")).trim());
                identity.setDesignation(String.valueOf(p.getOrDefault("designation", "")).trim());
                String mail = p.get("mailOfficial") != null
                        ? String.valueOf(p.get("mailOfficial"))
                        : String.valueOf(p.getOrDefault("mailPersonal", ""));
                identity.setMailId(mail.trim());
                identity.setProfileOk(true);
            }
        } catch (Exception e) {
            // Profile fetch failed; profileOk stays false, matching the original behavior
        }

        return SsoResult.success(identity);
    }

    public SsoResult establishSession(String token) {
        if (token == null || token.isEmpty()) return SsoResult.failure("NO_TOKEN");

        SsoResult result = fetchIdentity(token);
        if (!result.isOk()) {
            cache.remove(token);
            return result;
        }

        if (result.getIdentity().isProfileOk()) {
            CacheEntry entry = new CacheEntry();
            entry.identity = result.getIdentity();
            entry.expiresAt = Instant.now().toEpochMilli() + TTL_MILLIS;
            cache.put(token, entry);
        } else {
            cache.remove(token);
        }

        return result;
    }

    public SsoResult resolveToken(String token) {
        if (token == null || token.isEmpty()) return SsoResult.failure("NO_TOKEN");

        CacheEntry hit = cache.get(token);
        if (hit != null && hit.expiresAt > Instant.now().toEpochMilli()) {
            return SsoResult.success(hit.identity);
        }

        return establishSession(token);
    }

    public void dropSession(String token) {
        if (token != null) cache.remove(token);
    }

    public String issueLoginCode(String token) {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        String code = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        LoginCodeEntry entry = new LoginCodeEntry();
        entry.token = token;
        entry.expiresAt = Instant.now().toEpochMilli() + LOGIN_CODE_TTL_MILLIS;
        loginCodes.put(code, entry);

        return code;
    }

    public String redeemLoginCode(String code) {
        if (code == null || code.length() > 200) return null;

        LoginCodeEntry entry = loginCodes.remove(code);
        if (entry == null || entry.expiresAt <= Instant.now().toEpochMilli()) {
            return null;
        }

        return entry.token;
    }
}
