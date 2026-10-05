package com.dcims.backend.sso;

public class SsoIdentity {
    private String ssoId;
    private String userType;
    private String department = "";
    private String designation = "";
    private String mailId = "";
    private boolean profileOk = false;

    public String getSsoId() { return ssoId; }
    public void setSsoId(String ssoId) { this.ssoId = ssoId; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getMailId() { return mailId; }
    public void setMailId(String mailId) { this.mailId = mailId; }

    public boolean isProfileOk() { return profileOk; }
    public void setProfileOk(boolean profileOk) { this.profileOk = profileOk; }
}