// Importiert das Standard-Stylesheet von Leaflet (Pflicht für korrekte Darstellung)
import 'leaflet/dist/leaflet.css';

// Importiert benötigte Komponenten von react-leaflet zur Kartendarstellung
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';

// Funktion zur Zuordnung eines Emojis basierend auf der Kategorie
import categoryEmoji from '../utils/categoryEmoji';


// Hilfsfunktion: Erstellt ein benutzerdefiniertes Leaflet-Icon mit einem Emoji
const getEmojiIcon = (emoji) =>
    L.divIcon({
        html: `<div style="font-size: 24px;">${emoji}</div>`, // Emoji als HTML-Inhalt
        className: 'emoji-marker',                             // CSS-Klasse für Styling
        iconSize: [30, 30],                                    // Icon-Grösse
        iconAnchor: [15, 15]                                   // Ankerpunkt für zentrierte Platzierung
    });


// Hauptkomponente: Stellt eine interaktive Leaflet-Karte dar
const Map = ({ center, zoom, events }) => {
    return (
        <MapContainer center={center} zoom={zoom} style={{ height: '50vh', width: '100%' }}>
            {/* Hintergrundkarte mit OpenStreetMap-Kachelserver */}
            <TileLayer url="https://tile.openstreetmap.org/{z}/{x}/{y}.png" />

            {/* Iteriert über alle Events und erzeugt Marker mit Popups */}
            {events.map(event => {
                const lat = event.latitude;
                const lon = event.longitude;
                const category = event.category;
                const date = event.date;
                const emoji = categoryEmoji(category);    // Holt passendes Emoji

                if (lat == null || lon == null || !category) return null;    // Falls Koordinaten fehlen, Marker nicht anzeigen

                return (
                    <Marker
                        key={event.id}
                        position={[lat, lon]} // Leaflet erwartet [lat, lon] – also umdrehen
                        icon={getEmojiIcon(emoji)} // Verwendet Emoji als Marker-Icon
                    >
                        {/* Popup beim Klick auf Marker */}
                        <Popup>
                            <div className="popup-content">
                                <div className="popup-emoji">{emoji}</div>
                                <div className="popup-title">{event.title}</div>
                                <div className="popup-meta">
                                    <div><strong>Category:</strong> {category}</div>
                                    <div><strong>Date:</strong> {date}</div>
                                </div>
                            </div>
                        </Popup>
                    </Marker>
                );
            })}
        </MapContainer>
    );
};

export default Map;