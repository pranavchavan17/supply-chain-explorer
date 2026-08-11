import { Navigate, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';
import SupplierImpact from './pages/SupplierImpact';
import ComponentImpact from './pages/ComponentImpact';
import WhyAffected from './pages/WhyAffected';

export default function App() {
  return (
    <div className="app-shell">
      <div className="app-glow app-glow--one" aria-hidden="true" />
      <div className="app-glow app-glow--two" aria-hidden="true" />

      <Navbar />

      <main className="app-main">
        <Routes>
          <Route path="/" element={<Navigate to="/dashboard" replace />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/supplier-impact" element={<SupplierImpact />} />
          <Route path="/component-impact" element={<ComponentImpact />} />
          <Route path="/why-affected" element={<WhyAffected />} />
          <Route path="*" element={<Navigate to="/dashboard" replace />} />
        </Routes>
      </main>
    </div>
  );
}
