package com.dcims.backend.filter;

import com.dcims.backend.sso.SsoResult;
import com.dcims.backend.sso.SsoSessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SsoSessionFilter extends OncePerRequestFilter {

    private final SsoSessionService sessionService;

    public SsoSessionFilter(SsoSessionService sessionService) {
        this.sessionService = sessionService;
    }

    private static final String[] PROTECTED_PREFIXES = {
            "/api/sso/mapping",
            "/api/sso/session",
            "/api/sso/1",
            "/api/sso/2",
            "/org",
            "/apps",
            "/infra",
            "/checklist"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        boolean needsProtection = false;
        for (String prefix : PROTECTED_PREFIXES) {
            if (path.startsWith(prefix)) {
                needsProtection = true;
                break;
            }
        }

        if (!needsProtection) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("SSO-TOKEN");

        if (token == null || token.isEmpty()) {
            sendError(response, 401, "Login required", "NO_TOKEN");
            return;
        }

        SsoResult result = sessionService.resolveToken(token);

        if (!result.isOk()) {
            if ("SSO_UNREACHABLE".equals(result.getReason())) {
                sendError(response, 503, "SSO service is temporarily unavailable", result.getReason());
            } else {
                sendError(response, 401, "Session expired. Please log in again through SSO.", result.getReason());
            }
            return;
        }

        request.setAttribute("ssoId", result.getIdentity().getSsoId());
        request.setAttribute("identity", result.getIdentity());
        request.setAttribute("ssoToken", token);

        String askedSsoId = request.getParameter("ssoId");
        if (askedSsoId != null && !askedSsoId.trim().isEmpty()) {
            String actual = result.getIdentity().getSsoId();
            if (!askedSsoId.trim().equalsIgnoreCase(actual)) {
                sendError(response, 403, "ssoId does not match the logged-in session", null);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse response, int status, String message, String reason) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");

        StringBuilder json = new StringBuilder();
        json.append("{\"success\":false,\"message\":\"").append(escapeJson(message)).append("\"");
        if (reason != null) {
            json.append(",\"reason\":\"").append(escapeJson(reason)).append("\"");
        }
        json.append("}");

        response.getWriter().write(json.toString());
    }

    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}