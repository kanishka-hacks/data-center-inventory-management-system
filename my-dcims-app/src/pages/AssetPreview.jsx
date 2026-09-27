import { useNavigate, useLocation } from "react-router-dom";
import "./AssetPreview.css";

function AssetPreview() {
  const navigate = useNavigate();
  const location = useLocation();
  const data = location.state;

  if (!data) {
    return (
      <div className="preview-page">
        <p className="preview-empty">No data to preview. Please start from the form.</p>
        <button className="back-button" onClick={() => navigate("/assets")}>
          Back to Form
        </button>
      </div>
    );
  }

  function handleEditSection(targetPath) {
    navigate(targetPath, { state: { ...data, editMode: true } });
  }

  async function handleConfirmSave() {
    try {
      const oicVendorPayload = {
        projectName: data.projectName,
        departmentName: data.departmentName,
        oicName: data.oicName,
        oicContact: data.oicContact,
        oicEmail: data.oicEmail,
        vendorName: data.vendorName,
        vendorContactPerson: data.vendorContactPerson,
        vendorContactNumber: data.vendorContactNumber,
        vendorEmail: data.vendorEmail,
        remark: data.remark,
      };

      const oicVendorResponse = await fetch("http://localhost:8080/api/oic-vendor", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(oicVendorPayload),
      });
      const savedOicVendor = await oicVendorResponse.json();

      const hardwareAssetPayload = {
        deviceType: data.deviceType,
        make: data.make,
        model: data.model,
        serialNumber: data.serialNumber,
        deviceSerialNumber: data.deviceSerialNumber,
        numProcessors: data.numProcessors,
        memory: data.memory,
        hardDisk: data.hardDisk,
        location: data.location,
        rowRackNumber: data.rowRackNumber,
        installationDate: data.installationDate,
        status: data.status,
        purchaseDate: data.purchaseDate,
        warrantyStartDate: data.warrantyStartDate,
        warrantyExpiryDate: data.warrantyExpiryDate,
        endOfSupport: data.endOfSupport,
        endOfLife: data.endOfLife,
        operatingSystem: data.operatingSystem,
        osVersion: data.osVersion,
        licenseType: data.licenseType,
        licenseKey: data.licenseKey,
        licenseExpiryDate: data.licenseExpiryDate,
        oicVendorId: savedOicVendor.id,
      };

      const hardwareResponse = await fetch("http://localhost:8080/api/hardware-assets", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(hardwareAssetPayload),
      });
      await hardwareResponse.json();

      alert("Asset saved successfully!");
      navigate("/assets/list");
    } catch (error) {
      console.error("Error saving asset:", error);
      alert("Something went wrong while saving. Check the console for details.");
    }
  }

  return (
    <div className="preview-page">
      <h1 className="preview-title">Review Asset Details</h1>
      <p className="preview-subtitle">Please check everything before saving.</p>

      <div className="preview-section">
        <div className="preview-section-header">
          <h2 className="preview-section-title">Project OIC / Owner Details</h2>
          <button className="edit-section-button" onClick={() => handleEditSection("/assets")}>Edit</button>
        </div>
        <div className="preview-grid">
          <div className="preview-field"><span className="preview-label">Project Name</span><span className="preview-value">{data.projectName || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Department Name</span><span className="preview-value">{data.departmentName || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">OIC Name</span><span className="preview-value">{data.oicName || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">OIC Contact</span><span className="preview-value">{data.oicContact || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">OIC Email</span><span className="preview-value">{data.oicEmail || "-"}</span></div>
        </div>
      </div>

      <div className="preview-section">
        <div className="preview-section-header">
          <h2 className="preview-section-title">Vendor / Supplier Details</h2>
          <button className="edit-section-button" onClick={() => handleEditSection("/assets")}>Edit</button>
        </div>
        <div className="preview-grid">
          <div className="preview-field"><span className="preview-label">Vendor Name</span><span className="preview-value">{data.vendorName || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Vendor Contact Person</span><span className="preview-value">{data.vendorContactPerson || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Vendor Contact Number</span><span className="preview-value">{data.vendorContactNumber || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Vendor Email</span><span className="preview-value">{data.vendorEmail || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Remark</span><span className="preview-value">{data.remark || "-"}</span></div>
        </div>
      </div>

      <div className="preview-section">
        <div className="preview-section-header">
          <h2 className="preview-section-title">Hardware Details</h2>
          <button className="edit-section-button" onClick={() => handleEditSection("/assets/details")}>Edit</button>
        </div>
        <div className="preview-grid">
          <div className="preview-field"><span className="preview-label">Device Type</span><span className="preview-value">{data.deviceType || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Make</span><span className="preview-value">{data.make || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Model</span><span className="preview-value">{data.model || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Serial Number</span><span className="preview-value">{data.serialNumber || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Device Serial Number</span><span className="preview-value">{data.deviceSerialNumber || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Processors</span><span className="preview-value">{data.numProcessors || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Memory</span><span className="preview-value">{data.memory || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Hard Disk</span><span className="preview-value">{data.hardDisk || "-"}</span></div>
        </div>
      </div>

      <div className="preview-section">
        <div className="preview-section-header">
          <h2 className="preview-section-title">Installation Information</h2>
          <button className="edit-section-button" onClick={() => handleEditSection("/assets/details")}>Edit</button>
        </div>
        <div className="preview-grid">
          <div className="preview-field"><span className="preview-label">Location / Site</span><span className="preview-value">{data.location || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Row / Rack Number</span><span className="preview-value">{data.rowRackNumber || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Installation Date</span><span className="preview-value">{data.installationDate || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Status</span><span className="preview-value">{data.status || "-"}</span></div>
        </div>
      </div>

      <div className="preview-section">
        <div className="preview-section-header">
          <h2 className="preview-section-title">Warranty Information</h2>
          <button className="edit-section-button" onClick={() => handleEditSection("/assets/details")}>Edit</button>
        </div>
        <div className="preview-grid">
          <div className="preview-field"><span className="preview-label">Purchase Date</span><span className="preview-value">{data.purchaseDate || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Warranty Start</span><span className="preview-value">{data.warrantyStartDate || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">Warranty Expiry</span><span className="preview-value">{data.warrantyExpiryDate || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">End of Support</span><span className="preview-value">{data.endOfSupport || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">End of Life</span><span className="preview-value">{data.endOfLife || "-"}</span></div>
        </div>
      </div>

      <div className="preview-section">
        <div className="preview-section-header">
          <h2 className="preview-section-title">Software Details</h2>
          <button className="edit-section-button" onClick={() => handleEditSection("/assets/details")}>Edit</button>
        </div>
        <div className="preview-grid">
          <div className="preview-field"><span className="preview-label">Operating System</span><span className="preview-value">{data.operatingSystem || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">OS Version</span><span className="preview-value">{data.osVersion || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">License Type</span><span className="preview-value">{data.licenseType || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">License Key</span><span className="preview-value">{data.licenseKey || "-"}</span></div>
          <div className="preview-field"><span className="preview-label">License Expiry</span><span className="preview-value">{data.licenseExpiryDate || "-"}</span></div>
        </div>
      </div>

      <div className="preview-actions">
        <button className="confirm-button" onClick={handleConfirmSave}>Confirm & Save</button>
      </div>
    </div>
  );
}

export default AssetPreview;