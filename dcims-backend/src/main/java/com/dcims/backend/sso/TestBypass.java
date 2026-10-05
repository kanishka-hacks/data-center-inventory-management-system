package com.dcims.backend.sso;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TestBypass {

    @Value("${test.oic.sso.ids:}")
    private String testOicSsoIds;

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    public boolean isTestOicBypass(String ssoId) {
        if ("production".equals(activeProfile)) return false;

        List<String> testIds = Arrays.stream(testOicSsoIds.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .filter(s -> !s.isEmpty())
                .toList();

        return testIds.contains(String.valueOf(ssoId == null ? "" : ssoId).trim().toUpperCase());
    }
}