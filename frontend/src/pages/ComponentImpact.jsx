import { useState } from 'react';
import ErrorMessage from '../components/ErrorMessage';
import ImpactPath from '../components/ImpactPath';
import Loading from '../components/Loading';
import { getApiErrorMessage, getComponentImpact } from '../services/api';
import { buildComponentImpactGroups } from '../utils/impact';

const componentOptions = ['CMP-001', 'CMP-002', 'CMP-003', 'CMP-004', 'CMP-005'];

export default function ComponentImpact() {
  const [componentId, setComponentId] = useState(componentOptions[0]);
  const [groups, setGroups] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [submittedComponent, setSubmittedComponent] = useState('');
  const [hasSearched, setHasSearched] = useState(false);

  async function handleAnalyze() {
    setLoading(true);
    setError('');
    setHasSearched(true);

    try {
      const data = await getComponentImpact(componentId);
      const nextGroups = buildComponentImpactGroups(Array.isArray(data) ? data : []);
      setGroups(nextGroups);
      setSubmittedComponent(componentId);
    } catch (caughtError) {
      setGroups([]);
      setError(getApiErrorMessage(caughtError, 'Unable to load component impact data.'));
      setSubmittedComponent(componentId);
    } finally {
      setLoading(false);
    }
  }

  return (
    <section className="page">
      <header className="page__hero">
        <span className="eyebrow">Component Impact</span>
        <h1 className="page__title">Component Impact Analysis</h1>
        <p className="page__subtitle">
          Trace how a component propagates through products, warehouses, and regions using the
          live backend response.
        </p>
      </header>

      <section className="page__controls">
        <div className="control">
          <label className="control__label" htmlFor="component-id">
            Component ID
          </label>
          <select
            id="component-id"
            className="select"
            value={componentId}
            onChange={(event) => setComponentId(event.target.value)}
          >
            {componentOptions.map((option) => (
              <option key={option} value={option}>
                {option}
              </option>
            ))}
          </select>
        </div>

        <div className="control">
          <span className="control__label">Seed data</span>
          <div className="chip-row" aria-label="Available component IDs">
            {componentOptions.map((option) => (
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

      {loading ? <Loading label="Loading component impact" /> : null}
      {error ? <ErrorMessage message={error} onRetry={handleAnalyze} /> : null}

      {!loading && !error && hasSearched ? (
        groups.length ? (
          <div className="supplier-grid">
            <div className="panel">
              <div className="panel__header">
                <div>
                  <h2 className="panel__title">Impact paths for {submittedComponent}</h2>
                  <p className="panel__subtitle">
                    The component label is shown once, then each downstream route is rendered as a
                    separate path.
                  </p>
                </div>
              </div>

              <ImpactPath
                groups={groups}
                emptyMessage="No downstream paths were returned for this component."
              />
            </div>
          </div>
        ) : (
          <section className="empty-state">
            <h3 className="empty-state__title">No downstream impact found</h3>
            <p className="empty-state__text">
              The selected component exists, but the backend did not return any downstream paths.
            </p>
          </section>
        )
      ) : null}
    </section>
  );
}
