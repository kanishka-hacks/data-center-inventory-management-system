import { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./AssetForm.css";

function AssetFormPage1() {
  const navigate = useNavigate();
  const location = useLocation();
  const incoming = location.state || null;
  const isEditMode = incoming?.editMode === true;

  const [formData, setFormData] = useState({
    projectName: incoming?.projectName || "",
    departmentName: incoming?.departmentName || "",
    oicName: incoming?.oicName || "",
    oicContact: incoming?.oicContact || "",
    oicEmail: incoming?.oicEmail || "",
    vendorName: incoming?.vendorName || "",
    vendorContactPerson: incoming?.vendorContactPerson || "",
    vendorContactNumber: incoming?.vendorContactNumber || "",
    vendorEmail: incoming?.vendorEmail || "",
    remark: incoming?.remark || "",
  });

  const [errors, setErrors] = useState({});

  function handleChange(event) {
    const fieldName = event.target.name;
    const typedValue = event.target.value;
    setFormData({ ...formData, [fieldName]: typedValue });
    setErrors({ ...errors, [fieldName]: false });
  }

  function handleNext() {
    const newErrors = {};
    if (!formData.projectName) newErrors.projectName = true;
    if (!formData.departmentName) newErrors.departmentName = true;
    if (!formData.oicName) newErrors.oicName = true;
    if (!formData.oicContact) newErrors.oicContact = true;
    if (!formData.oicEmail) newErrors.oicEmail = true;
    if (!formData.vendorName) newErrors.vendorName = true;
    setErrors(newErrors);
    if (Object.keys(newErrors).length > 0) return;

    if (isEditMode) {
      const merged = { ...incoming, ...formData };
      delete merged.editMode;
      navigate("/assets/preview", { state: merged });
    } else {
      navigate("/assets/details", { state: formData });
    }
  }

  function ErrorText({ show }) {
    if (!show) return null;
    return <span className="field-error">This field is required</span>;
  }

  return (
    <div className="form-page">
      <h1 className="form-title">Asset Registration</h1>
      <p className="required-note">
        <span className="required">*</span> indicates required fields
      </p>

      <div className="form-section">
        <h2 className="section-title">1. Project OIC / Owner Details / Department Details</h2>
        <div className="form-grid">
          <div className="form-field">
            <label>Project Name <span className="required">*</span></label>
            <input type="text" name="projectName" placeholder="Enter project name" value={formData.projectName} onChange={handleChange} />
            <ErrorText show={errors.projectName} />
          </div>
          <div className="form-field">
            <label>Department Name <span className="required">*</span></label>
            <input type="text" name="departmentName" placeholder="Enter department name" value={formData.departmentName} onChange={handleChange} />
            <ErrorText show={errors.departmentName} />
          </div>
          <div className="form-field">
            <label>OIC Name <span className="required">*</span></label>
            <input type="text" name="oicName" placeholder="Enter officer-in-charge name" value={formData.oicName} onChange={handleChange} />
            <ErrorText show={errors.oicName} />
          </div>
          <div className="form-field">
            <label>OIC Contact Number <span className="required">*</span></label>
            <input type="text" name="oicContact" placeholder="Enter mobile number" value={formData.oicContact} onChange={handleChange} />
            <ErrorText show={errors.oicContact} />
          </div>
          <div className="form-field">
            <label>OIC Email Address <span className="required">*</span></label>
            <input type="text" name="oicEmail" placeholder="Enter email address" value={formData.oicEmail} onChange={handleChange} />
            <ErrorText show={errors.oicEmail} />
          </div>
        </div>
      </div>

      <div className="form-section">
        <h2 className="section-title">2. Vendor / Supplier Details</h2>
        <div className="form-grid">
          <div className="form-field">
            <label>Vendor Name <span className="required">*</span></label>
            <input type="text" name="vendorName" placeholder="Enter vendor name" value={formData.vendorName} onChange={handleChange} />
            <ErrorText show={errors.vendorName} />
          </div>
          <div className="form-field">
            <label>Vendor Contact Person</label>
            <input type="text" name="vendorContactPerson" placeholder="Enter contact person name" value={formData.vendorContactPerson} onChange={handleChange} />
          </div>
          <div className="form-field">
            <label>Vendor Contact Number</label>
            <input type="text" name="vendorContactNumber" placeholder="Enter phone number" value={formData.vendorContactNumber} onChange={handleChange} />
          </div>
          <div className="form-field">
            <label>Vendor Email Address</label>
            <input type="text" name="vendorEmail" placeholder="Enter email address" value={formData.vendorEmail} onChange={handleChange} />
          </div>
          <div className="form-field form-field-wide">
            <label>Remark</label>
            <input type="text" name="remark" placeholder="Any additional remarks" value={formData.remark} onChange={handleChange} />
          </div>
        </div>
      </div>

      <div className="form-actions">
        <a href="/" className="back-button">Back</a>
        <button className="save-button" onClick={handleNext}>
          {isEditMode ? "Save & Back to Preview" : "Next"}
        </button>
      </div>
    </div>
  );
}

export default AssetFormPage1;