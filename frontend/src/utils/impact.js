const ARROW_SPLIT_RE = /\s*(?:→|â†’|->)\s*/u;

export function splitPathString(pathValue) {
  if (typeof pathValue !== 'string') {
    return [];
  }

  return pathValue
    .split(ARROW_SPLIT_RE)
    .map((segment) => segment.trim())
    .filter(Boolean);
}

export function buildSupplierImpactGroups(rows) {
  const groups = new Map();

  rows.forEach((row, index) => {
    const componentKey = row.componentId || `component-${index}`;

    if (!groups.has(componentKey)) {
      groups.set(componentKey, {
        id: componentKey,
        title: row.componentName || 'Component',
        subtitle: row.componentId || '',
        paths: [],
      });
    }

    const group = groups.get(componentKey);
    group.paths.push({
      id: `${componentKey}-path-${group.paths.length + 1}`,
      steps: [
        { label: 'Supplier', value: row.supplierName },
        { label: 'Component', value: row.componentName },
        { label: 'Product', value: row.productName },
        { label: 'Warehouse', value: row.warehouseName },
        { label: 'Region', value: row.regionName },
      ],
    });
  });

  return Array.from(groups.values());
}

export function buildComponentImpactGroups(rows) {
  if (!rows.length) {
    return [];
  }

  const firstRow = rows[0];

  return [
    {
      id: firstRow.componentId || 'component-impact',
      title: firstRow.componentName || 'Component',
      subtitle: firstRow.componentId || '',
      paths: rows.map((row, index) => ({
        id: `${row.componentId || 'component-impact'}-path-${index + 1}`,
        steps: [
          { label: 'Component', value: row.componentName },
          { label: 'Product', value: row.productName },
          { label: 'Warehouse', value: row.warehouseName },
          { label: 'Region', value: row.regionName },
        ],
      })),
    },
  ];
}

export function buildWhyAffectedGroups(paths) {
  return paths.map((pathValue, index) => {
    const segments = splitPathString(pathValue);

    return {
      id: `why-path-${index + 1}`,
      title: `Path ${index + 1}`,
      subtitle: segments[segments.length - 1] || '',
      paths: [
        {
          id: `why-path-${index + 1}-detail`,
          steps: segments.map((segment, stepIndex) => ({
            label:
              ['Supplier', 'Component', 'Product', 'Warehouse'][stepIndex] ||
              `Hop ${stepIndex + 1}`,
            value: segment,
          })),
        },
      ],
    };
  });
}
