// Importiert notwendige React-Hooks und Funktionen
import { createContext, useEffect, useState } from 'react';
// Importiert Axios für HTTP-Anfragen
import axios from 'axios';

// Erstellt ein neues Context-Objekt, das später global verfügbar gemacht wird
export const CategoryContext = createContext();

// Definiert den Provider, der den Context bereitstellt
export const CategoryProvider = ({ children }) => {
    // Initialisiert den Zustand `categories` mit einem leeren Array
    const [categories, setCategories] = useState([]);

    // useEffect wird beim ersten Rendern des Providers ausgeführt
    useEffect(() => {
        // Asynchrone Funktion zum Laden der Kategorien aus der NASA EONET API
        const fetchCategories = async () => {
            try {
                // Führt eine GET-Anfrage an die API durch
                const response = await axios.get('https://eonet.gsfc.nasa.gov/api/v3/categories');
                // Setzt den Zustand mit den geladenen Kategorien
                setCategories(response.data.categories);
            } catch (error) {
                // Gibt einen Fehler in der Konsole aus, falls die Anfrage fehlschlägt
                console.error("Failed to load categories:", error);
            }
        };

        // Führt die Funktion aus
        fetchCategories();
    }, []); // Leeres Abhängigkeitsarray = nur einmal beim Mount ausführen

    return (
        // Macht den Wert `categories` über den Context im gesamten Komponentenbaum verfügbar
        <CategoryContext.Provider value={{ categories }}>
            {children} {/* Rendert alle Kindelemente innerhalb des Providers */}
        </CategoryContext.Provider>
    );
};