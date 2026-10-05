package com.dcims.backend.sso;

public class SsoResult {
    private boolean ok;
    private String reason;
    private SsoIdentity identity;

    public static SsoResult success(SsoIdentity identity) {
        SsoResult result = new SsoResult();
        result.ok = true;
        result.identity = identity;
        return result;
    }

    public static SsoResult failure(String reason) {
        SsoResult result = new SsoResult();
        result.ok = false;
        result.reason = reason;
        return result;
    }

    public boolean isOk() { return ok; }
    public String getReason() { return reason; }
    public SsoIdentity getIdentity() { return identity; }
}