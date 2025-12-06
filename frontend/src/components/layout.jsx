// Importiert den <Outlet> für das geschachtelte Routing und die Navigations-Komponente
import { Outlet } from "react-router-dom";
import Navigation from './navigation';

// Layout-Komponente definiert das generelle Seitenlayout mit Header, Main und Footer
const Layout = () => {
    // Ermittelt das aktuelle Jahr dynamisch für die Anzeige im Footer
    const currentYear = new Date().getFullYear();

    return (
        <div className='App'>
            {/* Header-Bereich enthält die Navigationsleiste */}
            <header className='App-header'>
                <Navigation />
            </header>

            {/* Hauptbereich – hier werden die Seiteninhalte je nach Route gerendert */}
            <main className='layout-main-content'>
                <Outlet /> {/* Platzhalter für die jeweils aktive Route */}
            </main>

            {/* Footer mit dynamisch aktualisiertem Copyright-Hinweis */}
            <footer className='layout-footer'>
                <p>© {currentYear} Natascha Blumer</p>
            </footer>
        </div>
    );
};

export default Layout;