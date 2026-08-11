export default function ImpactPath({ groups = [], emptyMessage = 'No impact data found.' }) {
  if (!groups.length) {
    return (
      <section className="empty-state">
        <h3 className="empty-state__title">No impact data to show yet</h3>
        <p className="empty-state__text">{emptyMessage}</p>
      </section>
    );
  }

  return (
    <div className="stack">
      {groups.map((group) => (
        <section key={group.id} className="impact-group-card">
          <div className="impact-group-card__header">
            <div>
              <p className="impact-group-card__kicker">
                {group.kicker || 'Impact route'}
              </p>
              <h3 className="impact-group-card__title">{group.title}</h3>
              {group.subtitle ? (
                <p className="impact-group-card__subtitle">{group.subtitle}</p>
              ) : null}
            </div>
            {group.badge ? <span className="metric-badge">{group.badge}</span> : null}
          </div>

          <div className="stack">
            {group.paths.map((path, pathIndex) => (
              <article key={path.id || pathIndex} className="impact-path">
                {group.paths.length > 1 ? (
                  <span className="impact-path__label">Path {pathIndex + 1}</span>
                ) : null}

                <div className="timeline">
                  {path.steps.map((step, stepIndex) => (
                    <div key={`${step.label}-${stepIndex}`} className="timeline__step">
                      <span className="timeline__dot" aria-hidden="true">
                        {stepIndex + 1}
                      </span>
                      <div className="timeline__content">
                        <span className="timeline__label">{step.label}</span>
                        <div className="timeline__value">{step.value}</div>
                      </div>
                    </div>
                  ))}
                </div>
              </article>
            ))}
          </div>
        </section>
      ))}
    </div>
  );
}
