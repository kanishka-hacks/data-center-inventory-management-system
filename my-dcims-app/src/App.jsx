import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import AssetFormPage1 from "./pages/AssetFormPage1";
import AssetFormPage2 from "./pages/AssetFormPage2";
import AssetPreview from "./pages/AssetPreview";
import AssetsList from "./pages/AssetsList";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/assets" element={<AssetFormPage1 />} />
        <Route path="/assets/details" element={<AssetFormPage2 />} />
        <Route path="/assets/preview" element={<AssetPreview />} />
        <Route path="/assets/list" element={<AssetsList />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

