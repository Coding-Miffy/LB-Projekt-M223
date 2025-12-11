import apiClient from '../services/api-client';

// Base path for events endpoints (relative to api-client baseURL)
const API_EVENTS_BASE = '/events';

// Events laden
export const getEvents = async (amount = 5, category = null) => {
    try {
        if (process.env.NODE_ENV !== 'production') console.log(`Lade ${amount} Events für Kategorie:`, category);

        // Schritt 1: URL zusammenbauen
        let path = `${API_EVENTS_BASE}/random?amount=${amount}`;
        if (category) {
            path = `${API_EVENTS_BASE}/random?category=${encodeURIComponent(category)}&limit=${amount}`;
        }
        if (process.env.NODE_ENV !== 'production') console.log("Events API path:", path);

        // Schritt 2: API-Aufruf via shared apiClient
        const response = await apiClient.get(path);
        if (process.env.NODE_ENV !== 'production') console.log("Events Response:", response);

        // Schritt 3: Events extrahieren
        const data = response.data;
        const events = data;
        if (process.env.NODE_ENV !== 'production') console.log("Events geladen:", events.length);

        // Schritt 4: Prüfen ob Events vorhanden
        if (events.length === 0) {
            if (process.env.NODE_ENV !== 'production') console.warn("Keine Events gefunden!");
        }

        // Schritt 5: Zurückgeben
        return events;
    } catch (error) {
        if (process.env.NODE_ENV !== 'production') {
            console.error("Fehler beim laden der Events:", error);
            console.error("Error Details:", error.message);
        }
        return [];
    }
};

// Alle Events laden
export const getAllEvents = async () => {
    try {
        const path = `${API_EVENTS_BASE}/all`;
        const response = await apiClient.get(path);
        const data = response.data;
        const events = data.results || data; // fallback if API returns array directly
        if (!events || events.length === 0) {
            if (process.env.NODE_ENV !== 'production') console.warn("Keine Events gefunden!");
        }
        return events;
    } catch (error) {
        if (process.env.NODE_ENV !== 'production') {
            console.error("Fehler beim Laden aller Events:", error);
            console.error("Error Details:", error.message);
        }
        return [];
    }
};

export const createEvent = async (eventData) => {
    try {
        const path = `${API_EVENTS_BASE}/create`;
        const response = await apiClient.post(path, eventData);
        return response.data;
    } catch (error) {
        if (process.env.NODE_ENV !== 'production') {
            console.error("Fehler beim Erstellen des Events:", error);
            console.error("Error Details:", error.message);
        }
        return null;
    }
};

export const updateEvent = async (eventId, updatedData) => {
    try {
        const path = `${API_EVENTS_BASE}/${eventId}/update`;
        const response = await apiClient.put(path, updatedData);
        return response.data;
    } catch (error) {
        if (process.env.NODE_ENV !== 'production') {
            console.error("Fehler beim Aktualisieren des Events:", error);
            console.error("Error Details:", error.message);
        }
        return null;
    }
};

export const deleteEvent = async (eventId) => {
    try {
        const path = `${API_EVENTS_BASE}/${eventId}`;
        await apiClient.delete(path);
        if (process.env.NODE_ENV !== 'production') console.log("Event erfolgreich gelöscht: ", eventId);
        return eventId;
    } catch (error) {
        if (process.env.NODE_ENV !== 'production') {
            console.error("Fehler beim Löschen des Events:", error);
            console.error("Error Details:", error.message);
        }
        return null;
    }
};

// Für Map
export const getOpenEventsByCategory = async (category) => {
    try {
        const path = `${API_EVENTS_BASE}/filter?status=open&category=${encodeURIComponent(category)}`;
        const response = await apiClient.get(path);
        return response.data;
    } catch (error) {
        if (process.env.NODE_ENV !== 'production') {
            console.error("Fehler beim Abrufen der Events: ", error);
            console.error("Error Details:", error.message);
        }
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

        const path = `${API_EVENTS_BASE}/filter?${params.toString()}`;
        const response = await apiClient.get(path);
        return response.data;
    } catch (error) {
        if (process.env.NODE_ENV !== 'production') {
            console.error("Fehler beim Abrufen der Events: ", error);
            console.error("Error Details:", error.message);
        }
        return [];
    }
}
