import { useState } from 'react';
import ErrorMessage from '../components/ErrorMessage';
import ImpactPath from '../components/ImpactPath';
import Loading from '../components/Loading';
import { getApiErrorMessage, getSupplierImpact } from '../services/api';
import { buildSupplierImpactGroups } from '../utils/impact';

const supplierOptions = ['SUP-001', 'SUP-002', 'SUP-003'];

export default function SupplierImpact() {
  const [supplierId, setSupplierId] = useState(supplierOptions[0]);
  const [groups, setGroups] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [submittedSupplier, setSubmittedSupplier] = useState('');
  const [hasSearched, setHasSearched] = useState(false);

  async function handleAnalyze() {
    setLoading(true);
    setError('');
    setHasSearched(true);

    try {
      const data = await getSupplierImpact(supplierId);
      const nextGroups = buildSupplierImpactGroups(Array.isArray(data) ? data : []);
      setGroups(nextGroups);
      setSubmittedSupplier(supplierId);
    } catch (caughtError) {
      setGroups([]);
      setError(getApiErrorMessage(caughtError, 'Unable to load supplier impact data.'));
      setSubmittedSupplier(supplierId);
    } finally {
      setLoading(false);
    }
  }

  return (
    <section className="page">
      <header className="page__hero">
        <span className="eyebrow">Supplier Impact</span>
        <h1 className="page__title">Supplier Impact Analysis</h1>
        <p className="page__subtitle">
          Select a supplier and trace the live downstream path from supplier to component,
          product, warehouse, and region.
        </p>
      </header>

      <section className="page__controls">
        <div className="control">
          <label className="control__label" htmlFor="supplier-id">
            Supplier ID
          </label>
          <select
            id="supplier-id"
            className="select"
            value={supplierId}
            onChange={(event) => setSupplierId(event.target.value)}
          >
            {supplierOptions.map((option) => (
              <option key={option} value={option}>
                {option}
              </option>
            ))}
          </select>
        </div>

        <div className="control">
          <span className="control__label">Seed data</span>
          <div className="chip-row" aria-label="Available supplier IDs">
            {supplierOptions.map((option) => (
              <span key={option} className="chip">
                {option}
              </span>
            ))}
          </div>
        </div>

        <div className="control control--actions">
          <span className="control__label">Analyze</span>
          <button type="button" className="button" onClick={handleAnalyze} disabled={loading}>
            {loading ? 'Analyzing...' : 'Analyze Impact'}
          </button>
        </div>
      </section>

      {loading ? <Loading label="Loading supplier impact" /> : null}
      {error ? <ErrorMessage message={error} onRetry={handleAnalyze} /> : null}

      {!loading && !error && hasSearched ? (
        groups.length ? (
          <div className="supplier-grid">
            <div className="panel">
              <div className="panel__header">
                <div>
                  <h2 className="panel__title">Impact paths for {submittedSupplier}</h2>
                  <p className="panel__subtitle">
                    The backend returns the full supplier-to-region chain for each downstream
                    route.
                  </p>
                </div>
              </div>

              <ImpactPath
                groups={groups}
                emptyMessage="No downstream paths were returned for this supplier."
              />
            </div>
          </div>
        ) : (
          <section className="empty-state">
            <h3 className="empty-state__title">No downstream impact found</h3>
            <p className="empty-state__text">
              The selected supplier exists, but the backend did not return any downstream paths.
            </p>
          </section>
        )
      ) : null}
    </section>
  );
}
