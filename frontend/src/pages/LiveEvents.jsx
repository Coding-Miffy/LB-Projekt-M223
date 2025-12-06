// Importiere React-Hooks
import { useEffect, useState } from 'react';

// Importiere Karten-Komponente für die Darstellung der Events
import Map from '../components/map';

import { getOpenEventsByCategory } from '../utils/java-api';

const LiveEvents = () => {

    // Zustand für die geladenen Events
    const [events, setEvents] = useState([]);
    // Zustand für Fehlermeldungen
    const [error, setError] = useState(null);
    // Zustand für Ladeanzeige
    const [isLoading, setIsLoading] = useState(false);

    // Ausgewählte Kategorie und Anzahl der Events
    const [selectedCategory, setSelectedCategory] = useState('wildfires'); // Default-Kategorie

    // Datenabruf bei Änderung der Kategorie oder der Anzahl
    useEffect(() => {
        setIsLoading(true);      // Ladeanzeige aktivieren
        setError(null);          // Vorherige Fehler zurücksetzen

        // Events von der API holen
        getOpenEventsByCategory(selectedCategory)
            .then(setEvents)     // Events in den State speichern
            .catch(err => {
                console.error(err);
                setError('Error loading events.'); // Fehler speichern
            })
            .finally(() => setIsLoading(false)); // Ladeanzeige deaktivieren
    }, [selectedCategory]); // Abhängigkeiten

    return (
        <div className="page-container">
            <h1 className="section-title">Live Earth Natural Events</h1>

            {/* Filter-Steuerung */}
            <div className="filters">
                <label>
                    Category:
                    <select
                        value={selectedCategory}
                        onChange={(e) => setSelectedCategory(e.target.value)}
                        className="form-input"
                    >
                        {/* Auswahlmöglichkeiten für Event-Kategorien */}
                        <option value="wildfires">🔥 Wildfire</option>
                        <option value="severeStorms">🌪️ Severe Storm</option>
                        <option value="volcanoes">🌋 Volcano</option>
                        <option value="seaLakeIce">🧊 Sea and Lake Ice</option>
                        <option value="earthquakes">🌍 Earthquake</option>
                        <option value="floods">🌊 Flood</option>
                        <option value="landslides">⛰️ Landslide</option>
                        <option value="snow">❄️ Snow</option>
                        <option value="drought">☀️ Drought</option>
                        <option value="dustHaze">🌫️ Dust Haze</option>
                        <option value="manmade">🏗️ Manmade</option>
                        <option value="waterColor">💧 Water Color</option>
                    </select>
                </label>

            </div>

            {/* Zustandsanzeigen */}
            {isLoading && <p>🔄 Loading...</p>}
            {error && <p>❌ {error}</p>}

            {/* Darstellung der Events auf der Karte */}
            <Map center={[20, 0]} zoom={2} events={events} />
        </div>
    );
};

export default LiveEvents;