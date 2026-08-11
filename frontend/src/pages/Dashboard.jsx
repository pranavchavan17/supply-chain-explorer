import { useEffect, useState } from 'react';
import ErrorMessage from '../components/ErrorMessage';
import Loading from '../components/Loading';
import SupplierCard from '../components/SupplierCard';
import { getApiErrorMessage, getHighImpactSuppliers } from '../services/api';

export default function Dashboard() {
  const [suppliers, setSuppliers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    let active = true;

    async function loadDashboard() {
      setLoading(true);
      setError('');

      try {
        const data = await getHighImpactSuppliers();
        if (active) {
          setSuppliers(Array.isArray(data) ? data : []);
        }
      } catch (caughtError) {
        if (active) {
          setError(
            getApiErrorMessage(
              caughtError,
              'Unable to load high-impact suppliers at the moment.'
            )
          );
        }
      } finally {
        if (active) {
          setLoading(false);
        }
      }
    }

    loadDashboard();

    return () => {
      active = false;
    };
  }, []);

  return (
    <section className="page">
      <header className="page__hero">
        <span className="eyebrow">Dashboard</span>
        <h1 className="page__title">Supply Chain Explorer</h1>
        <p className="page__subtitle">
          Graph-based supply chain impact analysis for tracing how suppliers ripple through
          components, products, warehouses, and regions using the live backend API.
        </p>
      </header>

      <div className="page__controls">
        <div className="control">
          <span className="control__label">High-impact supplier overview</span>
          <span className="control__hint">
            The dashboard reads from <code>/api/supply-chain/suppliers/high-impact</code>.
          </span>
        </div>
        <div className="control">
          <span className="control__label">Navigation</span>
          <span className="control__hint">
            Use the pages above to inspect supplier, component, and warehouse impact paths.
          </span>
        </div>
        <div className="control control--actions">
          <span className="control__label">Status</span>
          <span className="control__hint">Real backend data only. No mock responses.</span>
        </div>
      </div>

      {loading ? <Loading label="Loading high-impact suppliers" /> : null}
      {error ? <ErrorMessage message={error} /> : null}

      {!loading && !error ? (
        suppliers.length ? (
          <div className="dashboard-grid">
            {suppliers.map((supplier) => (
              <SupplierCard key={supplier.supplierId} supplier={supplier} />
            ))}
          </div>
        ) : (
          <section className="empty-state">
            <h3 className="empty-state__title">No high-impact suppliers returned</h3>
            <p className="empty-state__text">
              The backend responded successfully, but it did not return any suppliers for the
              current graph state.
            </p>
          </section>
        )
      ) : null}
    </section>
  );
}
