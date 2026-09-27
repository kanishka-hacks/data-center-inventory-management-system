import "./Home.css";

function Home() {
  return (
    <div className="page">
      <div className="header">
        <h1 className="header-title">DATA CENTER</h1>
        <h2 className="header-subtitle">INVENTORY MANAGEMENT SYSTEM</h2>
      </div>

      <div className="card">
        <h3 className="card-title">
          Welcome to the Data Center Inventory Management Portal
        </h3>
        <a href="/assets" className="manage-button">
          Manage Inventory
        </a>
      </div>

      <div className="instructions">
        <p className="instructions-title">
          Please read the instructions carefully before proceeding.
        </p>
        <ol>
          <li>All data center assets must be registered.</li>
          <li>Asset ID will be generated automatically.</li>
          <li>Enter accurate asset information.</li>
          <li>Maintain warranty and AMC information.</li>
          <li>Select the correct asset category.</li>
        </ol>
      </div>
    </div>
  );
}

export default Home;  //let other files borrow this home function



