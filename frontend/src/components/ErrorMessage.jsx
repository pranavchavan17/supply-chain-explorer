export default function ErrorMessage({ message, onRetry, title = 'Request failed' }) {
  return (
    <section className="error-card" role="alert" aria-live="assertive">
      <p className="error-card__title">{title}</p>
      <p className="error-card__message">{message}</p>

      {onRetry ? (
        <div className="error-card__actions">
          <button type="button" className="button button--secondary" onClick={onRetry}>
            Try again
          </button>
        </div>
      ) : null}
    </section>
  );
}
