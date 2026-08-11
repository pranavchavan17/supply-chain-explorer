export default function Loading({ label = 'Loading data' }) {
  return (
    <div className="loader" role="status" aria-live="polite">
      <span className="loader__spinner" aria-hidden="true" />
      <span className="loader__text">{label}...</span>
    </div>
  );
}
