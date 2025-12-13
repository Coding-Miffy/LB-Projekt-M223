import apiClient from "./api-client";

const API_BASE = "/users";

export const getFavorites = async (userId) => {
  try {
    const path = `${API_BASE}/${userId}/favorites`;
    const response = await apiClient.get(path);
    return response.data || [];
  } catch (error) {
    if (process.env.NODE_ENV !== "production")
      console.error("Fehler beim Laden der Favoriten:", error.message || error);
    return [];
  }
};

export const addFavorite = async (userId, event) => {
  try {
    const path = `${API_BASE}/${userId}/favorites`;
    // Send minimal payload; backend may accept eventId or full event
    const payload = { eventId: event.id };
    const response = await apiClient.post(path, payload);
    return response.data;
  } catch (error) {
    if (process.env.NODE_ENV !== "production")
      console.error(
        "Fehler beim Hinzufügen eines Favoriten:",
        error.message || error
      );
    throw error;
  }
};

export const removeFavorite = async (userId, eventId) => {
  try {
    const path = `${API_BASE}/${userId}/favorites/${eventId}`;
    const response = await apiClient.delete(path);
    return response.data;
  } catch (error) {
    if (process.env.NODE_ENV !== "production")
      console.error(
        "Fehler beim Entfernen eines Favoriten:",
        error.message || error
      );
    throw error;
  }
};
