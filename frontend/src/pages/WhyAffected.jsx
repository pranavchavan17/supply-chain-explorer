import { useState } from 'react';
import ErrorMessage from '../components/ErrorMessage';
import ImpactPath from '../components/ImpactPath';
import Loading from '../components/Loading';
import { getApiErrorMessage, getWarehouseImpactPath } from '../services/api';
import { buildWhyAffectedGroups } from '../utils/impact';

const supplierOptions = ['SUP-001', 'SUP-002', 'SUP-003'];
const warehouseOptions = ['WH-001', 'WH-002', 'WH-003', 'WH-004'];

export default function WhyAffected() {
  const [supplierId, setSupplierId] = useState(supplierOptions[0]);
  const [warehouseId, setWarehouseId] = useState(warehouseOptions[0]);
  const [groups, setGroups] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [hasSearched, setHasSearched] = useState(false);
  const [selectionLabel, setSelectionLabel] = useState('');
  const [emptyMessage, setEmptyMessage] = useState(
    'Choose a supplier and warehouse to look for a real backend impact path.'
  );

  async function handleAnalyze() {
    setLoading(true);
    setError('');
    setHasSearched(true);

    try {
      const data = await getWarehouseImpactPath(supplierId, warehouseId);
      const nextGroups = buildWhyAffectedGroups(Array.isArray(data) ? data : []);
      setGroups(nextGroups);
      setSelectionLabel(`${supplierId} + ${warehouseId}`);
      setEmptyMessage('No impact path found for the selected supplier and warehouse.');
    } catch (caughtError) {
      setGroups([]);
      setError(getApiErrorMessage(caughtError, 'Unable to load warehouse impact data.'));
      setSelectionLabel(`${supplierId} + ${warehouseId}`);
    } finally {
      setLoading(false);
    }
  }

  return (
    <section className="page">
      <header className="page__hero">
        <span className="eyebrow">Why Affected</span>
        <h1 className="page__title">Why Is This Warehouse Affected?</h1>
        <p className="page__subtitle">
          Pick a supplier and warehouse to reveal the exact backend path that explains the
          downstream impact.
        </p>
      </header>

      <section className="page__controls">
        <div className="control">
          <label className="control__label" htmlFor="why-supplier-id">
            Supplier ID
          </label>
          <select
            id="why-supplier-id"
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
          <label className="control__label" htmlFor="why-warehouse-id">
            Warehouse ID
          </label>
          <select
            id="why-warehouse-id"
            className="select"
            value={warehouseId}
            onChange={(event) => setWarehouseId(event.target.value)}
          >
            {warehouseOptions.map((option) => (
              <option key={option} value={option}>
                {option}
              </option>
            ))}
          </select>
        </div>

        <div className="control control--actions">
          <span className="control__label">Analyze</span>
          <button type="button" className="button" onClick={handleAnalyze} disabled={loading}>
            {loading ? 'Finding path...' : 'Find Impact Path'}
          </button>
        </div>
      </section>

      <div className="chip-row">
        <span className="chip">Suppliers: SUP-001, SUP-002, SUP-003</span>
        <span className="chip">Warehouses: WH-001, WH-002, WH-003, WH-004</span>
      </div>

      {loading ? <Loading label="Loading warehouse impact path" /> : null}
      {error ? <ErrorMessage message={error} onRetry={handleAnalyze} /> : null}

      {!loading && !error && hasSearched ? (
        groups.length ? (
          <div className="supplier-grid">
            <div className="panel">
              <div className="panel__header">
                <div>
                  <h2 className="panel__title">Why is {warehouseId} affected?</h2>
                  <p className="panel__subtitle">
                    Live backend path for the selected pair: {selectionLabel}.
                  </p>
                </div>
              </div>

              <ImpactPath groups={groups} emptyMessage={emptyMessage} />
            </div>
          </div>
        ) : (
          <section className="empty-state">
            <h3 className="empty-state__title">No impact path found</h3>
            <p className="empty-state__text">{emptyMessage}</p>
          </section>
        )
      ) : (
        <section className="status-card">
          <h3 className="status-card__title">Ready to trace a warehouse</h3>
          <p className="status-card__text">
            Use the seed IDs above, then click <strong>Find Impact Path</strong>. Empty arrays are
            treated as a valid success response and will not show an error.
          </p>
        </section>
      )}
    </section>
  );
}
