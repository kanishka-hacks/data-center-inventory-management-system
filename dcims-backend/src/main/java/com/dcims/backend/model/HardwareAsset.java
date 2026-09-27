package com.dcims.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "hardware_assets")
public class HardwareAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long oicVendorId;

    @Column(length = 50)
    private String deviceType;

    @Column(length = 50)
    private String make;

    @Column(length = 50)
    private String model;

    @Column(length = 50)
    private String serialNumber;

    @Column(length = 50)
    private String deviceSerialNumber;

    @Column(length = 50)
    private String numProcessors;

    @Column(length = 50)
    private String memory;

    @Column(length = 50)
    private String hardDisk;

    @Column(length = 50)
    private String location;

    @Column(length = 50)
    private String rowRackNumber;

    private LocalDate installationDate;

    @Column(length = 50)
    private String status;

    private LocalDate purchaseDate;
    private LocalDate warrantyStartDate;
    private LocalDate warrantyExpiryDate;
    private LocalDate endOfSupport;
    private LocalDate endOfLife;

    @Column(length = 50)
    private String operatingSystem;

    @Column(length = 50)
    private String osVersion;

    @Column(length = 50)
    private String licenseType;

    @Column(length = 50)
    private String licenseKey;

    private LocalDate licenseExpiryDate;

    // Getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOicVendorId() { return oicVendorId; }
    public void setOicVendorId(Long oicVendorId) { this.oicVendorId = oicVendorId; }

    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getDeviceSerialNumber() { return deviceSerialNumber; }
    public void setDeviceSerialNumber(String deviceSerialNumber) { this.deviceSerialNumber = deviceSerialNumber; }

    public String getNumProcessors() { return numProcessors; }
    public void setNumProcessors(String numProcessors) { this.numProcessors = numProcessors; }

    public String getMemory() { return memory; }
    public void setMemory(String memory) { this.memory = memory; }

    public String getHardDisk() { return hardDisk; }
    public void setHardDisk(String hardDisk) { this.hardDisk = hardDisk; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getRowRackNumber() { return rowRackNumber; }
    public void setRowRackNumber(String rowRackNumber) { this.rowRackNumber = rowRackNumber; }

    public LocalDate getInstallationDate() { return installationDate; }
    public void setInstallationDate(LocalDate installationDate) { this.installationDate = installationDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }

    public LocalDate getWarrantyStartDate() { return warrantyStartDate; }
    public void setWarrantyStartDate(LocalDate warrantyStartDate) { this.warrantyStartDate = warrantyStartDate; }

    public LocalDate getWarrantyExpiryDate() { return warrantyExpiryDate; }
    public void setWarrantyExpiryDate(LocalDate warrantyExpiryDate) { this.warrantyExpiryDate = warrantyExpiryDate; }

    public LocalDate getEndOfSupport() { return endOfSupport; }
    public void setEndOfSupport(LocalDate endOfSupport) { this.endOfSupport = endOfSupport; }

    public LocalDate getEndOfLife() { return endOfLife; }
    public void setEndOfLife(LocalDate endOfLife) { this.endOfLife = endOfLife; }

    public String getOperatingSystem() { return operatingSystem; }
    public void setOperatingSystem(String operatingSystem) { this.operatingSystem = operatingSystem; }

    public String getOsVersion() { return osVersion; }
    public void setOsVersion(String osVersion) { this.osVersion = osVersion; }

    public String getLicenseType() { return licenseType; }
    public void setLicenseType(String licenseType) { this.licenseType = licenseType; }

    public String getLicenseKey() { return licenseKey; }
    public void setLicenseKey(String licenseKey) { this.licenseKey = licenseKey; }

    public LocalDate getLicenseExpiryDate() { return licenseExpiryDate; }
    public void setLicenseExpiryDate(LocalDate licenseExpiryDate) { this.licenseExpiryDate = licenseExpiryDate; }
}