import { useState, useContext } from "react";
import Button from "./button";
import categoryEmoji from "../utils/categoryEmoji";
import { CategoryContext } from "../contexts/CategoryContext";

// Komponente zum Anzeigen und Bearbeiten eines eigenen Events
const CustomEventCard = ({ event, onEdit, onDelete }) => {

    // Emoji für das Event basierend auf der Kategorie ermitteln
    const emoji = categoryEmoji(event.category);

    // Lokale Zustände für den Editiermodus und Formulareingaben
    const [isEditing, setIsEditing] = useState(false);
    const [editTitle, setEditTitle] = useState(event.title);
    const [editDate, setEditDate] = useState(event.date);
    const [editCategory, setEditCategory] = useState('wildfires'); // default = 'wildfires'
    const [editLongitude, setEditLongitude] = useState(event.longitude);
    const [editLatitude, setEditLatitude] = useState(event.latitude);
    const [editStatus, setEditStatus] = useState('open');

    // Zugriff auf globale Kategorien via Context
    const { categories } = useContext(CategoryContext);

    // Gibt den Titel zur Kategorie-ID zurück (oder die ID selbst als Fallback)
    const getCategoryTitle = (id) => {
        const match = categories.find(c => c.id === id);
        return match ? match.title : id;
    };

    // Aktiviert den Bearbeitungsmodus und setzt die Felder vorab mit den Eventdaten
    const startEditing = () => {
        setIsEditing(true);
        setEditTitle(event.title);
        setEditDate(event.date);
        setEditCategory(event.category);
        setEditLongitude(event.longitude);
        setEditLatitude(event.latitude);
        setEditStatus(event.status);
    };

    // Beendet den Bearbeitungsmodus ohne Änderungen zu speichern
    const cancelEditing = () => {
        setIsEditing(false);
    };

    // Speichert Änderungen – validiert Eingaben und übergibt aktualisiertes Event
    const saveChanges = () => {
        if (!editTitle.trim() || !editDate.trim()) {
            alert("Fill out Title and Date");
            return;
        }

        const updatedEvent = {
            ...event,
            title: editTitle,
            date: editDate,
            category: editCategory,
            longitude: editLongitude,
            latitude: editLatitude,
            status: editStatus
        };

        onEdit(updatedEvent);
        setIsEditing(false);
    };

    // Fragt Benutzer:in, ob das Event wirklich gelöscht werden soll
    const handleDelete = () => {
        const isConfirmed = window.confirm(
            "Are you sure you want to delete this event?\nThis process cannot be undone."
        );
        if (isConfirmed) {
            onDelete(event.id);
        }
    };

    // JSX: Darstellung der Komponente
    return (
        <div className="event-card">
            {isEditing ? (
                // Bearbeitungsansicht (Formular)
                <div className="editing-form">
                    <h3>Edit Event</h3>

                    <div className="edit-field">
                        <label>Title</label>
                        <input
                            type="text"
                            value={editTitle}
                            onChange={(e) => setEditTitle(e.target.value)}
                            className="form-input"
                        />
                    </div>

                    <div className="edit-field">
                        <label>Date</label>
                        <input
                            type="date"
                            value={editDate}
                            onChange={(e) => setEditDate(e.target.value)}
                            className="form-input"
                        />
                    </div>

                    <div className="edit-field">
                        <label>Category</label>
                        <select
                            value={editCategory}
                            onChange={(e) => setEditCategory(e.target.value)}
                            className="form-input"
                        >
                            {/* Dropdown mit Kategorien zur Auswahl */}
                            <option value="wildfires">🔥 Wildfire</option>
                            <option value="severeStorms">🌪️ Severe Storm</option>
                            <option value="volcanoes">🌋 Volcanoe</option>
                            <option value="seaLakeIce">🧊 Sea Lake Ice</option>
                            <option value="earthquakes">🌍 Earthquake</option>
                            <option value="floods">🌊 Flood</option>
                            <option value="landslides">⛰️ Landslide</option>
                            <option value="snow">❄️ Snow</option>
                            <option value="drought">☀️ Drought</option>
                            <option value="dustHaze">🌫️ Dust Haze</option>
                            <option value="manmade">🏗️ Manmade</option>
                            <option value="waterColor">💧 Water Color</option>
                        </select>
                    </div>

                    <div className="edit-field">
                        <label>Longitude</label>
                        <input
                            type="text"
                            value={editLongitude}
                            onChange={(e) => setEditLongitude(e.target.value)}
                            className="form-input"
                        />
                    </div>

                    <div className="edit-field">
                        <label>Latitude</label>
                        <input
                            type="text"
                            value={editLatitude}
                            onChange={(e) => setEditLatitude(e.target.value)}
                            className="form-input"
                        />
                    </div>

                    <div className="edit-field">
                        <label>Status</label>
                        <select
                            value={editStatus}
                            onChange={(e) => setEditStatus(e.target.value)}
                            className="form-input"
                        >
                            {/* Dropdown mit Status zur Auswahl */}
                            <option value="open">Open</option>
                            <option value="closed">Closed</option>
                        </select>
                    </div>

                    <div className="edit-actions">
                        <Button
                            text={"Save"}
                            onButtonClick={saveChanges}
                            className={"save-button"}
                        />
                        <Button
                            text={"Cancel"}
                            onButtonClick={cancelEditing}
                            className={"cancel-button"}
                        />
                    </div>
                </div>
            ) : (
                // Standardansicht (Anzeige)
                <>
                    <div className="custom-event-card" key={event.id}>
                        <h3>
                            {emoji} {event.title}
                        </h3>
                        <div className="event-meta">
                            <p className="date-badge"><strong>Date:</strong> {new Date(event.date).toLocaleDateString()}</p>
                            <p className="category-badge"><strong>Category:</strong> {getCategoryTitle(event.category)}</p>
                        </div>
                    </div>

                    <div className="custom-event-actions">
                        <Button
                            text={"Edit"}
                            onButtonClick={startEditing}
                            className={"edit-button"}
                        />
                        <Button
                            text={"Delete"}
                            onButtonClick={handleDelete}
                            className={"delete-button"}
                        />
                    </div>
                </>
            )}
        </div>
    );
};

export default CustomEventCard;