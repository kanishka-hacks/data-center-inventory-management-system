//Backend se OIC/Vendor aur Hardware Asset data fetch karna -> dono ko ID ke through connect karna
// -> table mein saare registered assets dikhana -> new asset add karne ka option dena

import { useState, useEffect } from "react";//useEffect: yahan iska use backend se data fetch karne ke liye hua hai
import { useNavigate } from "react-router-dom";
import ConfirmDialog from "../components/ConfirmDialog";
import "./AssetsList.css";

function AssetsList() {
  const navigate = useNavigate();
  const [oicVendors, setOicVendors] = useState([]);//initially oicVendors is an empty array, but backend se data aane ke baadh now it is setOicVendors(oicData)
  const [hardwareAssets, setHardwareAssets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showDialog, setShowDialog] = useState(false);
  const [lastOicVendor, setLastOicVendor] = useState(null);

  useEffect(() => {
    async function fetchData() {
      //ek asyncc function banaye h because backend API se response aane mein time lagta hai
      try {
        //backend se saare OIC/Vendor records lo
        const oicResponse = await fetch("http://localhost:8080/api/oic-vendor");
        const oicData = await oicResponse.json();
        setOicVendors(oicData);

        const hardwareResponse = await fetch("http://localhost:8080/api/hardware-assets");
        //response ko JSON se JavaScript object/array mein convert kiya
        const hardwareData = await hardwareResponse.json();
        setHardwareAssets(hardwareData);
      } catch (error) {
        console.error("Error fetching assets:", error);
      } finally {
        setLoading(false);
      }
    }

    fetchData();
  }, []);

  function findOicVendorRecord(oicVendorId) {
    return oicVendors.find((ov) => ov.id === oicVendorId);
  }

  //yeh funct tabh chalenga jabh user add new asset par click karenga
  function handleAddNewAsset() {
    if (oicVendors.length === 0) {
      navigate("/assets");
      return;
    }

    const mostRecent = oicVendors[oicVendors.length - 1];
    setLastOicVendor(mostRecent);
    setShowDialog(true);
  }

  function handleUseSameOicVendor() {
    setShowDialog(false);
    navigate("/assets/details", { state: lastOicVendor });
  }

  function handleUseDifferentOicVendor() {
    setShowDialog(false);
    navigate("/assets");
  }

  if (loading) {
    return (
      <div className="list-page">
        <p className="loading-text">Loading assets...</p>
      </div>
    );
  }

  return (
    <div className="list-page">
      <h1 className="list-title">All Registered Assets</h1>

      <div className="list-actions">
        <button className="add-button" onClick={handleAddNewAsset}>
          + Add New Asset
        </button>
      </div>

      <div className="table-wrapper">
        <table className="assets-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Device Type</th>
              <th>Make</th>
              <th>Model</th>
              <th>Device Serial Number</th>
              <th>Location</th>
              <th>Status</th>
              <th>Warranty Expiry</th>
              <th>Project Name</th>
              <th>Department</th>
              <th>OIC Name</th>
              <th>OIC Contact</th>
              <th>OIC Email</th>
              <th>Vendor Name</th>
              <th>Vendor Contact</th>
            </tr>
          </thead>
          <tbody>
            {hardwareAssets.length === 0 ? (
              <tr>
                <td colSpan="15" className="empty-message">
                  No assets registered yet.
                </td>
              </tr>
            ) : (
              hardwareAssets.map((asset) => {
                const oicVendor = findOicVendorRecord(asset.oicVendorId);
                return (
                  <tr key={asset.id}>
                    <td>{asset.id}</td>
                    <td>{asset.deviceType}</td>
                    <td>{asset.make}</td>
                    <td>{asset.model}</td>
                    <td>{asset.deviceSerialNumber || "-"}</td>
                    <td>{asset.location}</td>
                    <td>{asset.status}</td>
                    <td>{asset.warrantyExpiryDate || "-"}</td>
                    <td>{oicVendor?.projectName || "-"}</td>
                    <td>{oicVendor?.departmentName || "-"}</td>
                    <td>{oicVendor?.oicName || "-"}</td>
                    <td>{oicVendor?.oicContact || "-"}</td>
                    <td>{oicVendor?.oicEmail || "-"}</td>
                    <td>{oicVendor?.vendorName || "-"}</td>
                    <td>{oicVendor?.vendorContactNumber || "-"}</td>
                  </tr>
                );
              })
            )}
          </tbody>
        </table>
      </div>

      {showDialog && (
        <ConfirmDialog
          message={`Use the same OIC/Vendor as last time?\n\nOIC: ${lastOicVendor?.oicName}\nVendor: ${lastOicVendor?.vendorName}`}
          onYes={handleUseSameOicVendor}
          onNo={handleUseDifferentOicVendor}
        />
      )}
    </div>
  );
}

export default AssetsList;