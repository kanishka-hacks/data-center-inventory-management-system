//used for making confirmation popup box
import "./ConfirmDialog.css";

function ConfirmDialog({ message, onYes, onNo }) {
  return (
    <div className="dialog-overlay">
      <div className="dialog-box">
        <p className="dialog-message">{message}</p>
        <div className="dialog-buttons">
          <button className="dialog-yes" onClick={onYes}>Yes</button>
          <button className="dialog-no" onClick={onNo}>No</button>
        </div>
      </div>
    </div>
  );
}

export default ConfirmDialog;