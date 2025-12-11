import apiClient from "./api-client";

/**
 * Login Funktion
 * Sendet Email + Passwort an Backend und speichert Token
 *
 * @param {string} usernameOrEmail - Username or Email
 * @param {string} password - User Passwort
 * @returns {Promise<Object>} User Daten + Token
 */
export const login = async (usernameOrEmail, password) => {
  // Try backend first; if it fails (no backend running), fall back to
  // a small set of local test accounts so the app works without a backend.
  try {
    console.log("📧 Login-Versuch für:", usernameOrEmail);

    // POST Request an Backend
    const response = await apiClient.post("/auth/login", {
      usernameOrEmail,
      password,
    });

    // Token aus Response extrahieren
    const { token, userId, username, email, role } = response.data;

    // Token in localStorage speichern
    localStorage.setItem("authToken", token);

    // User-Daten auch speichern (für schnellen Zugriff)
    const userData = { id: userId, username, email, role };
    localStorage.setItem("userData", JSON.stringify(userData));

    console.log("✅ Login erfolgreich - Token gespeichert (backend)");

    // Gesamte Response zurückgeben (enthält User-Daten)
    return response.data;
  } catch (error) {
    // If backend is unavailable or login failed due to network, fall back
    // to a local in-memory / localStorage-based fake authentication so
    // development without a backend still works.
    console.warn(
      '⚠️ Backend login failed or unavailable — using local fake accounts',
      error?.message || error
    );

    // Local test accounts (same credentials shown in the login page)
    const accounts = {
      admin: { id: 1, password: 'admin123', email: 'admin@eonet.com', role: 'ADMIN' },
      user: { id: 2, password: 'user123', email: 'user@eonet.com', role: 'USER' },
    };

    // Try to match by username first, then by email
    let matchedKey = null;
    if (accounts[usernameOrEmail]) {
      matchedKey = usernameOrEmail;
    } else {
      matchedKey = Object.keys(accounts).find(
        (k) => accounts[k].email === usernameOrEmail
      );
    }

    if (matchedKey && accounts[matchedKey].password === password) {
      const acc = accounts[matchedKey];
      const token = `fake-token-${matchedKey}-${Date.now()}`;

      localStorage.setItem('authToken', token);
      const userData = { id: acc.id, username: matchedKey, email: acc.email, role: acc.role };
      localStorage.setItem('userData', JSON.stringify(userData));

      console.log('✅ Login erfolgreich - Token gespeichert (local fake)');

      return { token, userId: acc.id, username: matchedKey, email: acc.email, role: acc.role };
    }

    // If credentials do not match local test accounts, throw a clear error
    throw new Error('Ungültige Zugangsdaten (kein Backend verfügbar)');
  }
};

/**
 * Logout Funktion
 * Löscht Token aus localStorage
 */
export const logout = () => {
  console.log("🚪 Logout - Token wird gelöscht");
  localStorage.removeItem("authToken");
  // Löscht cached user data auch
  localStorage.removeItem("userData");
};

/**
 * Hole User-Daten aus localStorage
 * (Brauchen keinen Backend-Call, haben alles vom Login!)
 */
export const getUserData = () => {
  const userDataString = localStorage.getItem('userData');
  if (userDataString) {
    return JSON.parse(userDataString);
  }
  return null;
};

/**
 * Prüft ob User eingeloggt ist
 * @returns {boolean} true wenn Token existiert
 */
export const isAuthenticated = () => {
  const token = localStorage.getItem("authToken");
  return !!token; // !! konvertiert zu boolean
};

/**
 * Gibt den aktuellen Token zurück
 * @returns {string|null} Token oder null
 */
export const getToken = () => {
  return localStorage.getItem("authToken");
};

/**
 * Gibt die aktuell gespeicherten User-Daten zurück (oder null)
 * @returns {Object|null}
 */
export const getCurrentUser = () => {
  const raw = localStorage.getItem('userData');
  if (!raw) return null;
  try {
    return JSON.parse(raw);
  } catch (e) {
    console.warn('Failed to parse userData from localStorage', e);
    return null;
  }
};

/**
 * Register Funktion (optional - falls dein Backend das unterstützt)
 * @param {Object} userData - User Registrierungsdaten
 * @returns {Promise<Object>} Registrierungsbestätigung
 */
export const register = async (userData) => {
  try {
    console.log("📝 Registrierung für:", userData.email);

    const response = await apiClient.post("/auth/register", userData);

    console.log("✅ Registrierung erfolgreich");
    return response.data;
  } catch (error) {
    console.error("❌ Registrierung fehlgeschlagen:", error);
    const errorMessage =
      error.response?.data?.message || "Registrierung fehlgeschlagen";
 throw new Error(errorMessage);
  }
};
