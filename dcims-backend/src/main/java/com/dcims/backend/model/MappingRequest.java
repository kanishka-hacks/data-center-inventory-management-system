package com.dcims.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
public class MappingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    private String ssoId;
    private String fullName;
    private String mobile;
    private String department;
    private String designation;
    private String email;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    private String approvalLetterPath;
    private LocalDateTime submittedAt = LocalDateTime.now();
    private String status = "pending";
    private Boolean isApproved = false;

    @Column(columnDefinition = "TEXT")
    private String objectionRemarks;

    private LocalDateTime reviewedAt;

    public Long getRequestId() { return requestId; }
    public void setRequestId(Long requestId) { this.requestId = requestId; }

    public String getSsoId() { return ssoId; }
    public void setSsoId(String ssoId) { this.ssoId = ssoId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getApprovalLetterPath() { return approvalLetterPath; }
    public void setApprovalLetterPath(String approvalLetterPath) { this.approvalLetterPath = approvalLetterPath; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Boolean getIsApproved() { return isApproved; }
    public void setIsApproved(Boolean isApproved) { this.isApproved = isApproved; }

    public String getObjectionRemarks() { return objectionRemarks; }
    public void setObjectionRemarks(String objectionRemarks) { this.objectionRemarks = objectionRemarks; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
}