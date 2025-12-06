// React Hook zum Verwalten von Zuständen importieren
import { useState } from 'react';
// Wiederverwendbare Button-Komponente importieren
import Button from './button';

// Formular-Komponente für die Erstellung von benutzerdefinierten Events
const CustomEventForm = ({ onEventSubmit }) => {

    // Formularwerte als State definieren
    const [title, setTitle] = useState('');
    const [date, setDate] = useState('');
    const [category, setCategory] = useState('wildfires');
    const [longitude, setLongitude] = useState('');
    const [latitude, setLatitude] = useState('');
    const [status, setStatus] = useState('open');

    // Fehler-States für die Validierung der Eingabefelder
    const [titleError, setTitleError] = useState('');
    const [dateError, setDateError] = useState('');
    const [categoryError, setCategoryError] = useState('');
    const [longitudeError, setLongitudeError] = useState('');
    const [latitudeError, setLatitudeError] = useState('');
    const [statusError, setStatusError] = useState('');

    // Zeigt an, ob gerade eine Speicherung im Gange ist
    const [isSubmitting, setIsSubmitting] = useState(false);

    // Aktualisiert den Titel und entfernt evtl. vorhandene Fehlermeldung
    const handleTitleChange = (e) => {
        setTitle(e.target.value);
        if (titleError) setTitleError("");
    };

    // Aktualisiert das Datum und entfernt evtl. vorhandene Fehlermeldung
    const handleDateChange = (e) => {
        setDate(e.target.value);
        if (dateError) setDateError("");
    };

    // Aktualisiert die Kategorie und entfernt evtl. vorhandene Fehlermeldung
    const handleCategoryChange = (e) => {
        setCategory(e.target.value);
        if (categoryError) setCategoryError("");
    };

    // Aktualisiert die Longitude und entfernt evtl. vorhandene Fehlermeldung
    const handleLongitudeChange = (e) => {
        setLongitude(e.target.value);
        if (longitudeError) setLongitudeError("");
    };

    // Aktualisiert die Latitude und entfernt evtl. vorhandene Fehlermeldung
    const handleLatitudeChange = (e) => {
        setLatitude(e.target.value);
        if (latitudeError) setLatitudeError("");
    };

    // Aktualisiert den Status und entfernt evtl. vorhandene Fehlermeldung
    const handleStatusChange = (e) => {
        setStatus(e.target.value);
        if (statusError) setStatusError("");
    };

    // Prüft, ob alle erforderlichen Felder ausgefüllt sind
    const validateForm = () => {
        let isValid = true;

        // Fehler zurücksetzen
        setTitleError("");
        setDateError("");
        setCategoryError("");
        setLongitudeError("");
        setLatitudeError("");
        setStatusError("");

        // Titel darf nicht leer sein
        if (!title.trim()) {
            setTitleError("Enter title");
            isValid = false;
        }

        // Datum darf nicht leer sein
        if (!date.trim()) {
            setDateError("Choose a date");
            isValid = false;
        }

        // Längengrad darf nicht leer sein
        if (!longitude.trim()) {
            setLongitudeError("Enter longitude");
            isValid = false;
        }

        // Breitengrad darf nicht leer sein
        if (!latitude.trim()) {
            setLatitudeError("Enter latitude");
            isValid = false;
        }

        return isValid;
    };

    // Handler für das Absenden des Formulars
    const handleSubmit = (e) => {
        e.preventDefault(); // verhindert Seitenreload

        // Validierung prüfen – falls ungültig, abbrechen
        if (!validateForm()) return;

        setIsSubmitting(true); // Status "wird gesendet" aktivieren

        // Neues Event zusammenstellen
        const newEvent = {
            title: title,
            date: date,
            category: category,
            longitude: longitude,
            latitude: latitude,
            status: status
        };

        // Event an die Elternkomponente übergeben
        onEventSubmit(newEvent);

        // Formular zurücksetzen
        setTitle("");
        setDate("");
        setCategory("");
        setLongitude("");
        setLatitude("");
        setStatus("");
        setIsSubmitting(false);
    };

    // JSX – Das Formular zur Event-Erstellung
    return (
        <form onSubmit={handleSubmit} className="event-form">
            <h3>Create new event: </h3>

            {/* Titel-Eingabefeld */}
            <div className="form-group">
                <label htmlFor="title">Title: </label>
                <input
                    type="text"
                    id="title"
                    value={title}
                    onChange={handleTitleChange}
                    placeholder="Title"
                    className={`form-input ${titleError ? "form-input--error" : ""}`}
                />
                {/* Fehlermeldung anzeigen, falls vorhanden */}
                {titleError && (
                    <span className='error-message'>{titleError}</span>
                )}
            </div>

            {/* Datums-Eingabefeld */}
            <div className="form-group">
                <label htmlFor="date">Date: </label>
                <input
                    type="date"
                    id="date"
                    value={date}
                    onChange={handleDateChange}
                    className={`form-input ${dateError ? "form-input--error" : ""}`}
                />
                {dateError && (
                    <span className='error-message'>{dateError}</span>
                )}
            </div>

            {/* Kategorie-Auswahl */}
            <div className="form-group">
                <label htmlFor="category">Category: </label>
                <select
                    id="category"
                    value={category}
                    onChange={handleCategoryChange}
                    className="form-input"
                >
                    {/* Liste der verfügbaren Kategorien */}
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
            </div>

            {/* Longitude-Eingabefeld */}
            <div className="form-group">
                <label htmlFor="longitude">Longitude: </label>
                <input
                    type="text"
                    id="longitude"
                    value={longitude}
                    onChange={handleLongitudeChange}
                    placeholder="Longitude"
                    className={`form-input ${longitudeError ? "form-input--error" : ""}`}
                />
                {/* Fehlermeldung anzeigen, falls vorhanden */}
                {longitudeError && (
                    <span className='error-message'>{longitudeError}</span>
                )}
            </div>

            {/* Latitude-Eingabefeld */}
            <div className="form-group">
                <label htmlFor="latitude">Latitude: </label>
                <input
                    type="text"
                    id="latitude"
                    value={latitude}
                    onChange={handleLatitudeChange}
                    placeholder="Latitude"
                    className={`form-input ${latitudeError ? "form-input--error" : ""}`}
                />
                {/* Fehlermeldung anzeigen, falls vorhanden */}
                {latitudeError && (
                    <span className='error-message'>{latitudeError}</span>
                )}
            </div>

            {/* Status-Auswahl */}
            <div className="form-group">
                <label htmlFor="status">Status: </label>
                <select
                    id="status"
                    value={status}
                    onChange={handleStatusChange}
                    className="form-input"
                >
                    {/* Verfügbare Status */}
                    <option value="open">Open</option>
                    <option value="closed">Closed</option>
                </select>
            </div>

            {/* Absende-Button */}
            <div className="form-submit">
                <Button
                    text={isSubmitting ? "Saving..." : "Create Event"}
                    onButtonClick={handleSubmit}
                    disabled={isSubmitting}
                    className="submit-button"
                />
            </div>
        </form>
    );
};

export default CustomEventForm;