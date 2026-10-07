package com.dcims.backend.controller;

import com.dcims.backend.sso.SsoIdentity;
import com.dcims.backend.sso.SsoResult;
import com.dcims.backend.sso.SsoSessionService;
import com.dcims.backend.sso.TestBypass;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class SsoLoginController {

    private final SsoSessionService sessionService;
    private final TestBypass testBypass;

    @Value("${frontend.url}")
    private String frontendUrl;

    public SsoLoginController(SsoSessionService sessionService, TestBypass testBypass) {
        this.sessionService = sessionService;
        this.testBypass = testBypass;
    }

    @PostMapping("/")
    public void ssoLanding(@RequestParam(value = "userdetails", required = false) String token,
                           HttpServletResponse response) throws IOException {

        if (token == null || token.isEmpty()) {
            response.sendRedirect(frontendUrl + "/sso/failed?reason=no_token");
            return;
        }

        SsoResult session = sessionService.establishSession(token);

        if (!session.isOk()) {
            String reason = "SSO_UNREACHABLE".equals(session.getReason())
                    ? "sso_unreachable"
                    : "token_detail_failed";
            response.sendRedirect(frontendUrl + "/sso/failed?reason=" + reason);
            return;
        }

        SsoIdentity id = session.getIdentity();

        if (!"GOVT".equals(id.getUserType())) {
            response.sendRedirect(frontendUrl + "/sso/failed?reason=NOT_G2G");
            return;
        }

        boolean isCitizen = "CITIZEN".equalsIgnoreCase(id.getDesignation());
        if (isCitizen && !testBypass.isTestOicBypass(id.getSsoId())) {
            response.sendRedirect(frontendUrl + "/sso/failed?reason=NOT_G2G");
            return;
        }

        String code = sessionService.issueLoginCode(token);
        String encodedCode = URLEncoder.encode(code, StandardCharsets.UTF_8);
        response.sendRedirect(frontendUrl + "/sso/success?code=" + encodedCode);
    }
}
