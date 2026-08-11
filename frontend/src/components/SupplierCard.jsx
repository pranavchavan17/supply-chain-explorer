export default function SupplierCard({ supplier }) {
  return (
    <article className="supplier-card">
      <div className="supplier-card__header">
        <div>
          <span className="eyebrow">Supplier</span>
          <h3 className="supplier-card__title">{supplier.supplierName}</h3>
          <p className="supplier-card__id">{supplier.supplierId}</p>
        </div>
        <span className="metric-badge">High impact</span>
      </div>

      <dl className="metric-grid">
        <div className="metric">
          <dt>Components Affected</dt>
          <dd>{supplier.componentsAffected}</dd>
        </div>
        <div className="metric">
          <dt>Products Affected</dt>
          <dd>{supplier.productsAffected}</dd>
        </div>
        <div className="metric">
          <dt>Warehouses Affected</dt>
          <dd>{supplier.warehousesAffected}</dd>
        </div>
      </dl>
    </article>
  );
}
