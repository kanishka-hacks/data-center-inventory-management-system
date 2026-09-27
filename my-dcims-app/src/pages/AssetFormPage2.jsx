import { useState } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import "./AssetForm.css";

function AssetFormPage2() {
  const navigate = useNavigate();
  const location = useLocation();
  const incoming = location.state || {};
  const isEditMode = incoming.editMode === true;

  const [formData, setFormData] = useState({
    deviceType: incoming.deviceType || "",
    otherDeviceType: incoming.otherDeviceType || "",
    make: incoming.make || "",
    model: incoming.model || "",
    serialNumber: incoming.serialNumber || "",
    deviceSerialNumber: incoming.deviceSerialNumber || "",
    purchaseOrderNumber: incoming.purchaseOrderNumber || "",
    numProcessors: incoming.numProcessors || "",
    memory: incoming.memory || "",
    hardDisk: incoming.hardDisk || "",
    location: incoming.location || "",
    rowRackNumber: incoming.rowRackNumber || "",
    installationDate: incoming.installationDate || "",
    status: incoming.status || "",
    purchaseDate: incoming.purchaseDate || "",
    warrantyStartDate: incoming.warrantyStartDate || "",
    warrantyExpiryDate: incoming.warrantyExpiryDate || "",
    endOfSupport: incoming.endOfSupport || "",
    endOfLife: incoming.endOfLife || "",
    warrantyCertificateName: incoming.warrantyCertificateName || "",
    operatingSystem: incoming.operatingSystem || "",
    osVersion: incoming.osVersion || "",
    licenseType: incoming.licenseType || "",
    licenseKey: incoming.licenseKey || "",
    licenseExpiryDate: incoming.licenseExpiryDate || "",
  });

  const [errors, setErrors] = useState({});

  function handleChange(event) {
    const fieldName = event.target.name;
    const typedValue = event.target.value;
    setFormData({ ...formData, [fieldName]: typedValue });
    setErrors({ ...errors, [fieldName]: false });
  }

  function handleFileChange(event) {
    const file = event.target.files[0];
    setFormData({ ...formData, warrantyCertificateName: file ? file.name : "" });
  }

  function handleReview() {
    const newErrors = {};

    if (!formData.deviceType) newErrors.deviceType = true;
    if (formData.deviceType === "Other" && !formData.otherDeviceType) newErrors.otherDeviceType = true;
    if (!formData.make) newErrors.make = true;
    if (!formData.model) newErrors.model = true;
    if (!formData.serialNumber) newErrors.serialNumber = true;
    if (!formData.location) newErrors.location = true;
    if (!formData.rowRackNumber) newErrors.rowRackNumber = true;
    if (!formData.status) newErrors.status = true;
    if (!formData.warrantyStartDate) newErrors.warrantyStartDate = true;
    if (!formData.warrantyExpiryDate) newErrors.warrantyExpiryDate = true;

    setErrors(newErrors);

    if (Object.keys(newErrors).length > 0) {
      return;
    }

    const merged = { ...incoming, ...formData };
    delete merged.editMode;
    navigate("/assets/preview", { state: merged });
  }

  function ErrorText({ show }) {
    if (!show) return null;
    return <span className="field-error">This field is required</span>;
  }

  return (
    <div className="form-page">
      <h1 className="form-title">Asset Registration - Hardware Details</h1>
      <p className="required-note">
        <span className="required">*</span> indicates required fields
      </p>

      <div className="form-section">
        <h2 className="section-title">1. Hardware Details</h2>
        <div className="form-grid">
          <div className="form-field">
            <label>Device Type <span className="required">*</span></label>
            <select name="deviceType" value={formData.deviceType} onChange={handleChange}>
              <option value="">-- Select --</option>
              <option value="Server">Server</option>
              <option value="Storage Array">Storage Array</option>
              <option value="Network Switch">Network Switch</option>
              <option value="Router">Router</option>
              <option value="Firewall">Firewall</option>
              <option value="UPS">UPS</option>
              <option value="Other">Other</option>
            </select>
            <ErrorText show={errors.deviceType} />
            {formData.deviceType === "Other" && (
              <>
                <input
                  type="text"
                  name="otherDeviceType"
                  placeholder="Please specify"
                  value={formData.otherDeviceType}
                  onChange={handleChange}
                />
                <ErrorText show={errors.otherDeviceType} />
              </>
            )}
          </div>
          <div className="form-field">
            <label>Make <span className="required">*</span></label>
            <input type="text" name="make" placeholder="e.g. Dell, HPE, Cisco" value={formData.make} onChange={handleChange} />
            <ErrorText show={errors.make} />
          </div>
          <div className="form-field">
            <label>Model <span className="required">*</span></label>
            <input type="text" name="model" placeholder="Enter model number" value={formData.model} onChange={handleChange} />
            <ErrorText show={errors.model} />
          </div>
          <div className="form-field">
            <label>Serial Number <span className="required">*</span></label>
            <input type="text" name="serialNumber" placeholder="Enter serial number" value={formData.serialNumber} onChange={handleChange} />
            <ErrorText show={errors.serialNumber} />
          </div>
          <div className="form-field">
            <label>Device Serial Number</label>
            <input type="text" name="deviceSerialNumber" placeholder="Enter device serial number" value={formData.deviceSerialNumber} onChange={handleChange} />
          </div>
          <div className="form-field">
            <label>Purchase Order Number</label>
            <input type="text" name="purchaseOrderNumber" placeholder="Enter purchase order number" value={formData.purchaseOrderNumber} onChange={handleChange} />
          </div>
          <div className="form-field">
            <label>Number of Processors</label>
            <input type="text" name="numProcessors" placeholder="e.g. 2" value={formData.numProcessors} onChange={handleChange} />
          </div>
          <div className="form-field">
            <label>Memory (RAM)</label>
            <input type="text" name="memory" placeholder="e.g. 128 GB" value={formData.memory} onChange={handleChange} />
          </div>
          <div className="form-field">
            <label>Hard Disk</label>
            <input type="text" name="hardDisk" placeholder="e.g. 2 x 1TB SSD" value={formData.hardDisk} onChange={handleChange} />
          </div>
        </div>
      </div>

      <div className="form-section">
        <h2 className="section-title">2. Installation Information</h2>
        <div className="form-grid">
          <div className="form-field">
            <label>Location / Site <span className="required">*</span></label>
            <input type="text" name="location" placeholder="e.g. State Data Centre, Jaipur" value={formData.location} onChange={handleChange} />
            <ErrorText show={errors.location} />
          </div>
          <div className="form-field">
            <label>Row / Rack Number <span className="required">*</span></label>
            <input type="text" name="rowRackNumber" placeholder="e.g. Row 4 / Rack 12" value={formData.rowRackNumber} onChange={handleChange} />
            <ErrorText show={errors.rowRackNumber} />
          </div>
          <div className="form-field">
            <label>Installation Date</label>
            <input type="date" name="installationDate" value={formData.installationDate} onChange={handleChange} />
          </div>
          <div className="form-field">
            <label>Status <span className="required">*</span></label>
            <select name="status" value={formData.status} onChange={handleChange}>
              <option value="">-- Select --</option>
              <option>Active</option>
              <option>Inactive</option>
              <option>Under Maintenance</option>
              <option>Decommissioned</option>
            </select>
            <ErrorText show={errors.status} />
          </div>
        </div>
      </div>

      <div className="form-section">
        <h2 className="section-title">3. Warranty Information</h2>
        <div className="form-grid">
          <div className="form-field">
            <label>Purchase Date</label>
            <input type="date" name="purchaseDate" value={formData.purchaseDate} onChange={handleChange} />
          </div>
          <div className="form-field">
            <label>Warranty Start Date <span className="required">*</span></label>
            <input type="date" name="warrantyStartDate" value={formData.warrantyStartDate} onChange={handleChange} />
            <ErrorText show={errors.warrantyStartDate} />
          </div>
          <div className="form-field">
            <label>Warranty Expiry Date <span className="required">*</span></label>
            <input type="date" name="warrantyExpiryDate" value={formData.warrantyExpiryDate} onChange={handleChange} />
            <ErrorText show={errors.warrantyExpiryDate} />
          </div>
          <div className="form-field">
            <label>End of Support (EOS)</label>
            <input type="date" name="endOfSupport" value={formData.endOfSupport} onChange={handleChange} />
          </div>
          <div className="form-field">
            <label>End of Life (EOL)</label>
            <input type="date" name="endOfLife" value={formData.endOfLife} onChange={handleChange} />
          </div>
          <div className="form-field">
            <label>Warranty Certificate</label>
            <input type="file" name="warrantyCertificate" onChange={handleFileChange} />
            {formData.warrantyCertificateName && (
              <span className="file-name-hint">{formData.warrantyCertificateName}</span>
            )}
          </div>
        </div>
      </div>

      <div className="form-section">
        <h2 className="section-title">4. Software Details (Optional)</h2>
        <div className="form-grid">
          <div className="form-field">
            <label>Operating System</label>
            <input type="text" name="operatingSystem" placeholder="e.g. Windows Server 2022" value={formData.operatingSystem} onChange={handleChange} />
          </div>
          <div className="form-field">
            <label>OS Version / Build</label>
            <input type="text" name="osVersion" placeholder="Enter version number" value={formData.osVersion} onChange={handleChange} />
          </div>
          <div className="form-field">
            <label>License Type</label>
            <select name="licenseType" value={formData.licenseType} onChange={handleChange}>
              <option value="">-- Select --</option>
              <option>OEM</option>
              <option>Volume License</option>
              <option>Open Source</option>
              <option>Subscription</option>
              <option>Perpetual</option>
            </select>
          </div>
          <div className="form-field">
            <label>License Key / ID</label>
            <input type="text" name="licenseKey" placeholder="Enter license key or reference" value={formData.licenseKey} onChange={handleChange} />
          </div>
          <div className="form-field">
            <label>License Expiry Date</label>
            <input type="date" name="licenseExpiryDate" value={formData.licenseExpiryDate} onChange={handleChange} />
          </div>
        </div>
      </div>

      <div className="form-actions">
        <Link to="/assets" className="back-button">Back</Link>
        <button className="save-button" onClick={handleReview}>
          {isEditMode ? "Save & Back to Preview" : "Review"}
        </button>
      </div>
    </div>
  );
}

export default AssetFormPage2;