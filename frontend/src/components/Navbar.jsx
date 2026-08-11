import { NavLink, Link } from 'react-router-dom';

const navItems = [
  { to: '/dashboard', label: 'Dashboard' },
  { to: '/supplier-impact', label: 'Supplier Impact' },
  { to: '/component-impact', label: 'Component Impact' },
  { to: '/why-affected', label: 'Why Affected' },
];

export default function Navbar() {
  return (
    <header className="topbar">
      <Link to="/dashboard" className="brand" aria-label="Supply Chain Explorer home">
        <span className="brand__mark">SC</span>
        <span className="brand__copy">
          <span className="brand__title">Supply Chain Explorer</span>
          <span className="brand__subtitle">Graph-Based Supply Chain Impact Analysis</span>
        </span>
      </Link>

      <nav className="nav" aria-label="Primary">
        {navItems.map((item) => (
          <NavLink
            key={item.to}
            to={item.to}
            className={({ isActive }) =>
              `nav__link${isActive ? ' nav__link--active' : ''}`
            }
          >
            {item.label}
          </NavLink>
        ))}
      </nav>
    </header>
  );
}
