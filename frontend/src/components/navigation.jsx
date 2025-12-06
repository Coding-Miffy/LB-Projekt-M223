// Importiert das NavLink-Element aus react-router-dom
// NavLink ermöglicht Navigation zwischen Routen und kennzeichnet aktive Links automatisch
import { NavLink } from "react-router-dom";

// Definiert die Navigations-Komponente
const Navigation = () => {
    return (
        // Wrapper-Element für die Navigation mit CSS-Klasse für Styling
        <nav className='layout-header-nav'>

            {/* NavLink zur Startseite.
                Die `className`-Funktion prüft, ob der Link aktuell aktiv ist,
                und weist dann dynamisch die CSS-Klasse `active` zu. */}
            <NavLink to='/' className={({ isActive }) => isActive ? 'active' : ''}>
                Home
            </NavLink>

            {/* NavLink zur Seite mit den aktuellen Naturereignissen */}
            <NavLink to='/live-events' className={({ isActive }) => isActive ? 'active' : ''}>
                Live Events
            </NavLink>

            {/* NavLink zur Archiv-Seite mit abgeschlossenen Ereignissen */}
            <NavLink to='/archive' className={({ isActive }) => isActive ? 'active' : ''}>
                Archive
            </NavLink>

            {/* NavLink zur Seite mit benutzerdefinierten Ereignissen */}
            <NavLink to='/manage-events' className={({ isActive }) => isActive ? 'active' : ''}>
                Manage Events
            </NavLink>
        </nav>
    )
}

// Exportiert die Navigation-Komponente für die Verwendung in anderen Teilen der App
export default Navigation;