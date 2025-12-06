// Importiere das useContext Hook
import { useContext } from 'react';
// Importiere die Hilfsfunktion zur Emoji-Zuweisung anhand der Kategorie
import categoryEmoji from '../utils/categoryEmoji';
// Importiere den Context, der die Kategorie-Daten global bereitstellt
import { CategoryContext } from '../contexts/CategoryContext';

// Die Komponente erhält drei Props: title, date, category
const ArchiveEventCard = ({ title, date, category }) => {

    // Zugriff auf den Kategorien-Context (bereitgestellt vom CategoryProvider)
    const { categories } = useContext(CategoryContext);

    // Ermittle das passende Emoji zur Kategorie (z. B. '🔥' für 'wildfires')
    const emoji = categoryEmoji(category);

    // Finde in der Liste der bekannten Kategorien ein passendes Element
    // Der Vergleich erfolgt über .includes() (unscharf), um z. B. "seaLakeIce" zu "sea lake ice" zu matchen
    const match = categories.find(cat =>
        category?.toLowerCase().includes(cat.id.toLowerCase())
    );

    // Verwende den lesbaren Titel aus der Kategorie-Liste (z. B. "Sea and Lake Ice"),
    // falls kein Treffer gefunden wurde, wird die übergebene category oder "Unknown" angezeigt
    const titleText = match?.title || category || 'Unknown';

    // Die eigentliche Card-UI
    return (
        <div className="archive-card">
            {/* Emoji prominent darstellen */}
            <div className="emoji" style={{ fontSize: '2rem' }}>{emoji}</div>

            {/* Titel des Events */}
            <h3 className="archive-card-title">{title}</h3>

            {/* Datum formatieren und anzeigen */}
            <p className="archive-card-detail">
                <strong>Date:</strong> {date ? new Date(date).toLocaleDateString() : 'Unknown'}
            </p>

            {/* Kategorietitel anzeigen */}
            <p className="archive-card-detail">
                <strong>Category:</strong> {titleText}
            </p>
        </div>
    );
};

// Komponente exportieren
export default ArchiveEventCard;