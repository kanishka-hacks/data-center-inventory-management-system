package com.dcims.backend.controller;

import com.dcims.backend.model.User;
import com.dcims.backend.sso.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sso")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class SsoController {

    private final SsoSessionService sessionService;
    private final TestBypass testBypass;
    private final UserRepository userRepository;

    @Value("${sso.portal.url}")
    private String ssoPortalUrl;

    @Value("${sso.signout.url}")
    private String ssoSignoutUrl;

    public SsoController(SsoSessionService sessionService, TestBypass testBypass, UserRepository userRepository) {
        this.sessionService = sessionService;
        this.testBypass = testBypass;
        this.userRepository = userRepository;
    }

    @PostMapping("/exchange")
    public Map<String, Object> exchangeLoginCode(@RequestBody Map<String, String> body) {
        String code = body.get("code");
        String token = sessionService.redeemLoginCode(code);

        Map<String, Object> response = new HashMap<>();

        if (token == null) {
            response.put("success", false);
            response.put("message", "Login code is invalid or expired. Please log in again.");
            response.put("reason", "INVALID_CODE");
            return response;
        }

        SsoResult result = sessionService.resolveToken(token);

        if (!result.isOk()) {
            response.put("success", false);
            response.put("message", "Session could not be verified. Please log in again.");
            response.put("reason", result.getReason());
            return response;
        }

        response.put("success", true);
        response.put("ssoId", result.getIdentity().getSsoId());
        response.put("token", token);
        return response;
    }

    @GetMapping("/1/user-basic")
    public Map<String, Object> getUserBasic(@RequestHeader("SSO-TOKEN") String token) {
        SsoResult result = sessionService.resolveToken(token);
        Map<String, Object> response = new HashMap<>();

        if (!result.isOk() || !result.getIdentity().isProfileOk()) {
            response.put("success", false);
            response.put("message", "Unable to fetch user profile from SSO right now");
            return response;
        }

        SsoIdentity id = result.getIdentity();
        response.put("success", true);
        Map<String, Object> data = new HashMap<>();
        data.put("department", id.getDepartment());
        data.put("designation", id.getDesignation());
        data.put("mailId", id.getMailId());
        response.put("data", data);
        return response;
    }

    @GetMapping("/2/user-details")
    public Map<String, Object> getUserDetails(@RequestHeader("SSO-TOKEN") String token) {
        SsoResult result = sessionService.resolveToken(token);
        Map<String, Object> response = new HashMap<>();

        if (!result.isOk()) {
            response.put("success", false);
            response.put("message", "Session could not be verified.");
            return response;
        }

        SsoIdentity id = result.getIdentity();

        if (testBypass.isTestOicBypass(id.getSsoId())) {
            response.put("success", true);
            response.put("access", "FORM");
            response.put("reason", "TEST_BYPASS");
            Map<String, Object> data = new HashMap<>();
            data.put("department", id.getDepartment());
            data.put("designation", "TEST-OIC");
            data.put("mailId", null);
            response.put("data", data);
            return response;
        }

        if (!id.isProfileOk()) {
            response.put("success", false);
            response.put("message", "SSO profile is temporarily unavailable. Please try again.");
            return response;
        }

        String currentDepartment = id.getDepartment().toUpperCase();
        String currentDesignation = id.getDesignation().toUpperCase();

        if (!"GOVT".equals(id.getUserType()) || "CITIZEN".equals(currentDesignation)) {
            response.put("success", true);
            response.put("access", "DENIED");
            response.put("reason", "NOT_G2G");
            response.put("data", null);
            return response;
        }

        Optional<User> existingUserOpt = userRepository.findBySsoId(id.getSsoId());

        if (existingUserOpt.isEmpty()) {
            response.put("success", true);
            response.put("access", "MAPPING");
            response.put("reason", "FIRST_TIME_USER");
            Map<String, Object> data = new HashMap<>();
            data.put("department", id.getDepartment());
            data.put("designation", id.getDesignation());
            data.put("mailId", id.getMailId());
            response.put("data", data);
            return response;
        }

        User existingUser = existingUserOpt.get();
        boolean isActiveUser = "active".equalsIgnoreCase(existingUser.getStatus() == null ? "" : existingUser.getStatus().trim());
        boolean isApprovedUser = Boolean.TRUE.equals(existingUser.getIsApproved());

        if (!isActiveUser) {
            response.put("success", true);
            response.put("access", "DENIED");
            response.put("reason", "ACCOUNT_INACTIVE");
            response.put("data", null);
            return response;
        }

        if (!isApprovedUser) {
            response.put("success", true);
            response.put("access", "DENIED");
            response.put("reason", "NOT_APPROVED");
            response.put("data", null);
            return response;
        }

        boolean hasAllowedRole = OicRoles.OIC_ROLES.contains(currentDesignation);

        if (hasAllowedRole) {
            response.put("success", true);
            response.put("access", "FORM");
            response.put("reason", "ROLE_MATCHED");
            response.put("data", userDataOf(existingUser));
            return response;
        }

        String savedDepartment = (existingUser.getDepartment() == null ? "" : existingUser.getDepartment()).trim().toUpperCase();
        String savedDesignation = (existingUser.getDesignation() == null ? "" : existingUser.getDesignation()).trim().toUpperCase();

        boolean detailsMatch = !currentDepartment.isEmpty() && !currentDesignation.isEmpty()
                && currentDepartment.equals(savedDepartment) && currentDesignation.equals(savedDesignation);

        if (detailsMatch) {
            response.put("success", true);
            response.put("access", "FORM");
            response.put("reason", "DEPT_DESIGNATION_MATCHED");
            response.put("data", userDataOf(existingUser));
            return response;
        }

        response.put("success", true);
        response.put("access", "MAPPING");
        response.put("reason", "USER_MAPPING_REQUIRED");
        response.put("data", userDataOf(existingUser));
        return response;
    }

    private Map<String, Object> userDataOf(User user) {
        Map<String, Object> data = new HashMap<>();
        data.put("department", user.getDepartment());
        data.put("designation", user.getDesignation());
        data.put("mailId", user.getEmail());
        return data;
    }

    @GetMapping("/session")
    public Map<String, Object> verifySession(@RequestHeader("SSO-TOKEN") String token) {
        SsoResult result = sessionService.resolveToken(token);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("authenticated", result.isOk());
        if (result.isOk()) response.put("ssoId", result.getIdentity().getSsoId());
        response.put("ssoPortalUrl", ssoPortalUrl);
        response.put("signoutUrl", ssoSignoutUrl);
        return response;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(@RequestHeader(value = "SSO-TOKEN", required = false) String token) {
        sessionService.dropSession(token);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Logged out");
        response.put("signoutUrl", ssoSignoutUrl);
        return response;
    }
}