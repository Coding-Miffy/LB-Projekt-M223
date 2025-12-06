// React Hooks für Zustand und Nebeneffekte
import { useEffect, useState } from 'react';
// Importiert die Komponente zum Anzeigen eines archivierten Events
import ArchiveEventCard from '../components/archive-event-card';

import { getClosedEventsByCategory } from '../utils/java-api';

const Archive = () => {

    // Zustand für die angezeigten Events
    const [events, setEvents] = useState([]);
    // Zustand für potenzielle Fehler beim Laden
    const [error, setError] = useState(null);

    // Zustände für Filter: Kategorie, Datum, Begrenzung
    const [selectedCategory, setSelectedCategory] = useState('wildfires');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');

    // useEffect wird ausgeführt, wenn Filter verändert werden
    useEffect(() => {
        getClosedEventsByCategory(selectedCategory, startDate, endDate)
            .then(setEvents)
            .catch(err => {
                console.error(err);
                setError("Could not load events.");
            });
    }, [selectedCategory, startDate, endDate]); // Triggert den Effekt bei Änderung

    return (
        <div className="past-events-container" style={{ padding: '1rem' }}>
            {/* Filterbereich für Kategorie und Datum */}
            <div className="filters">
                <label>
                    Category:
                    <select
                        value={selectedCategory}
                        onChange={(e) => setSelectedCategory(e.target.value)}
                        className='form-input'
                    >
                        {/* Auswahlmöglichkeit für verschiedene Naturereignis-Kategorien */}
                        <option value="wildfires">🔥 Wildfire</option>
                        <option value="severeStorms">🌪️ Severe Storm</option>
                        <option value="volcanoes">🌋 Volcanoe</option>
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

                {/* Filter: Startdatum */}
                <label>
                    Start Date:
                    <input
                        type="date"
                        value={startDate}
                        onChange={(e) => setStartDate(e.target.value)}
                        className='form-input'
                    />
                </label>

                {/* Filter: Enddatum */}
                <label>
                    End Date:
                    <input
                        type="date"
                        value={endDate}
                        onChange={(e) => setEndDate(e.target.value)}
                        className='form-input'
                    />
                </label>

            </div>

            {/* Fehleranzeige */}
            {error && <p>{error}</p>}

            {/* Liste der angezeigten Event-Karten */}
            <div className="event-list">
                {events.map(event => (
                    <ArchiveEventCard
                        key={event.id}
                        title={event.title}
                        date={event.date}
                        category={event.category}
                    />
                ))}
            </div>
        </div>
    );
};

export default Archive;