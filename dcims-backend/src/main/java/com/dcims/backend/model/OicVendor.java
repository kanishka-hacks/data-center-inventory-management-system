package com.dcims.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "oic_vendor")
public class OicVendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String projectName;

    @Column(length = 50)
    private String departmentName;

    @Column(length = 50)
    private String oicName;

    @Column(length = 50)
    private String oicContact;

    @Column(length = 50)
    private String oicEmail;

    @Column(length = 50)
    private String vendorName;

    @Column(length = 50)
    private String vendorContactPerson;

    @Column(length = 50)
    private String vendorContactNumber;

    @Column(length = 50)
    private String vendorEmail;

    private String remark;

    // Getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getOicName() { return oicName; }
    public void setOicName(String oicName) { this.oicName = oicName; }

    public String getOicContact() { return oicContact; }
    public void setOicContact(String oicContact) { this.oicContact = oicContact; }

    public String getOicEmail() { return oicEmail; }
    public void setOicEmail(String oicEmail) { this.oicEmail = oicEmail; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public String getVendorContactPerson() { return vendorContactPerson; }
    public void setVendorContactPerson(String vendorContactPerson) { this.vendorContactPerson = vendorContactPerson; }

    public String getVendorContactNumber() { return vendorContactNumber; }
    public void setVendorContactNumber(String vendorContactNumber) { this.vendorContactNumber = vendorContactNumber; }

    public String getVendorEmail() { return vendorEmail; }
    public void setVendorEmail(String vendorEmail) { this.vendorEmail = vendorEmail; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
