import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api/events";

// HTTP-Client mit Timeout konfigurieren
const apiClient = axios.create({
    timeout: 10000, // 10 Sekunden Timeout
    headers: {
        "Content-Type": "application/json",
    },
});

// Events laden
export const getEvents = async (amount = 5, category = null) => {
    try {
        console.log(`Lade ${amount} Events für Kategorie:`, category);

        // Schritt 1: URL zusammenbauen
        let url = `${API_BASE_URL}/random?amount=${amount}`;
        if (category) {
            url = `${API_BASE_URL}/random?category=${category}&limit=${amount}`;
        }
        console.log("EONET URL:", url);

        // Schritt 2: API-Aufruf
        const response = await apiClient.get(url);
        console.log("EONET Response:", response);

        // Schritt 3: Events extrahieren
        const data = response.data;
        const events = data;
        console.log("Events geladen:", events.length);

        // Schritt 4: Prüfen ob Events vorhanden
        if (events.length === 0) {
            console.warn("Keine Events gefunden!");
        }

        // Schritt 5: Zurückgeben
        return events;
    } catch (error) {
        console.error("Fehler beim laden der Events:", error);
        console.error("Error Details:", error.message);
        return [];
    }
};

// Alle Events laden
export const getAllEvents = async () => {
    try {
        const url = `${API_BASE_URL}/all`;
        const response = await apiClient.get(url);
        const data = response.data;
        const events = data.results || data; // fallback if API returns array directly
        if (!events || events.length === 0) {
            console.warn("Keine Events gefunden!");
        }
        return events;
    } catch (error) {
        console.error("Fehler beim Laden aller Events:", error);
        console.error("Error Details:", error.message);
        return [];
    }
};

export const createEvent = async (eventData) => {
    try {
        const url = `${API_BASE_URL}/create`;
        const response = await apiClient.post(url, eventData);
        return response.data;
    } catch (error) {
        console.error("Fehler beim Erstellen des Events:", error);
        console.error("Error Details:", error.message);
        return null;
    }
};

export const updateEvent = async (eventId, updatedData) => {
    try {
        const url = `${API_BASE_URL}/${eventId}/update`;
        const response = await apiClient.put(url, updatedData);
        return response.data;
    } catch (error) {
        console.error("Fehler beim Aktualisieren des Events:", error);
        console.error("Error Details:", error.message);
        return null;
    }
};

export const deleteEvent = async (eventId) => {
    try {
        const url = `${API_BASE_URL}/${eventId}`;
        await apiClient.delete(url);
        console.log("Event erfolgreich gelöscht: ", eventId);
        return eventId;
    } catch (error) {
        console.error("Fehler beim Löschen des Events:", error);
        console.error("Error Details:", error.message);
        return null;
    }
};

// Für Map
export const getOpenEventsByCategory = async (category) => {
    try {
        const url = `${API_BASE_URL}/filter?status=open&category=${category}`;
        const response = await apiClient.get(url);
        return response.data;
    } catch (error) {
        console.error("Fehler beim Abrufen der Events: ", error);
        console.error("Error Details:", error.message);
        return [];
    }
}

// Für Archive
export const getClosedEventsByCategory = async (category, startDate, endDate) => {
    try {
        const params = new URLSearchParams();
        if (category) params.append('category', category);
        params.append('status', 'closed');
        if (startDate) params.append('start', startDate);
        if (endDate) params.append('end', endDate);

        const url = `${API_BASE_URL}/filter?${params.toString()}`;
        const response = await apiClient.get(url);
        return response.data;
    } catch (error) {
        console.error("Fehler beim Abrufen der Events: ", error);
        console.error("Error Details:", error.message);
        return [];
    }
}