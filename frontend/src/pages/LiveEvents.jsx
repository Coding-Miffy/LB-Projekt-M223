// Importiere React-Hooks
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

// Importiere Karten-Komponente für die Darstellung der Events
import Map from '../components/map';

import { getOpenEventsByCategory } from '../services/events-service';


const LiveEvents = () => {

    // Zustand für die geladenen Events
    const [events, setEvents] = useState([]);
    // Zustand für Fehlermeldungen
    const [error, setError] = useState(null);
    // Zustand für Ladeanzeige
    const [isLoading, setIsLoading] = useState(false);

    // Ausgewählte Kategorie und Anzahl der Events
    const [selectedCategory, setSelectedCategory] = useState('wildfires'); // Default-Kategorie

    const location = useLocation();
    const focusEvent = location.state?.focusEvent;

    // Datenabruf bei Änderung der Kategorie oder der Anzahl
    useEffect(() => {
        setIsLoading(true);      // Ladeanzeige aktivieren
        setError(null);          // Vorherige Fehler zurücksetzen

        // Events von der API holen
        getOpenEventsByCategory(selectedCategory)
            .then((res) => {
                // respect API response; if empty or falsy, leave events empty
                if (!res || (Array.isArray(res) && res.length === 0)) {
                    setEvents([]);
                } else {
                    setEvents(res);
                }
            })
            .catch(err => {
                console.error(err);
                // On error, set no events and surface an error to the user
                setEvents([]);
                setError('Error loading events.'); // Fehler speichern
            })
            .finally(() => setIsLoading(false)); // Ladeanzeige deaktivieren
    }, [selectedCategory]); // Abhängigkeiten

    // If navigated here with a focusEvent, ensure it's visible on the map and center on it
    useEffect(() => {
        if (focusEvent) {
            // if focusEvent not present in events, add it temporarily
            setEvents((prev) => {
                if (!prev.find((e) => e.id === focusEvent.id)) {
                    return [...prev, focusEvent];
                }
                return prev;
            });
        }
    }, [focusEvent]);

    const mapCenter = focusEvent && focusEvent.latitude && focusEvent.longitude ? [focusEvent.latitude, focusEvent.longitude] : [20, 0];

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
            <Map center={mapCenter} zoom={focusEvent ? 7 : 2} events={events} />
        </div>
    );
};

export default LiveEvents;