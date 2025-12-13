import { createContext, useContext, useEffect, useState } from "react";
import { getCurrentUser } from "../services/auth-service";
import {
  getFavorites as fetchFavoritesFromApi,
  addFavorite as addFavoriteApi,
  removeFavorite as removeFavoriteApi,
} from "../services/favorites-service";

const FavoritesContext = createContext();

export const FavoritesProvider = ({ children }) => {
  const [favorites, setFavorites] = useState([]);

  // Load favorites on mount: prefer backend if user is authenticated, otherwise load from localStorage
  useEffect(() => {
    const load = async () => {
      const user = getCurrentUser();
      if (user && user.id) {
        const remote = await fetchFavoritesFromApi(user.id);
        // If backend returns ids only, try to preserve existing event objects in local state
        setFavorites(remote || []);
        try {
          localStorage.setItem("favorites", JSON.stringify(remote || []));
        } catch (e) {
          // ignore
        }
      } else {
        // fallback: load from localStorage for unauthenticated users
        try {
          const raw = localStorage.getItem("favorites");
          if (raw) setFavorites(JSON.parse(raw));
        } catch (e) {
          // ignore parse errors
        }
      }
    };

    load();
  }, []);

  const addFavorite = (event) => {
    // Optimistic update
    setFavorites((prev) => {
      if (prev.find((f) => f.id === event.id)) return prev;
      const next = [...prev, event];
      try {
        localStorage.setItem("favorites", JSON.stringify(next));
      } catch (e) {}
      return next;
    });

    // Persist to backend if logged in
    const user = getCurrentUser();
    if (user && user.id) {
      addFavoriteApi(user.id, event).catch((err) => {
        console.error("Failed to persist favorite to API:", err);
      });
    }
  };

  const removeFavorite = (id) => {
    // Optimistic update
    setFavorites((prev) => {
      const next = prev.filter((f) => f.id !== id);
      try {
        localStorage.setItem("favorites", JSON.stringify(next));
      } catch (e) {}
      return next;
    });

    const user = getCurrentUser();
    if (user && user.id) {
      removeFavoriteApi(user.id, id).catch((err) => {
        console.error("Failed to remove favorite from API:", err);
      });
    }
  };

  const isFavorite = (id) => {
    return favorites.some((f) => f.id === id);
  };

  const toggleFavorite = (event) => {
    if (isFavorite(event.id)) removeFavorite(event.id);
    else addFavorite(event);
  };

  return (
    <FavoritesContext.Provider
      value={{
        favorites,
        addFavorite,
        removeFavorite,
        isFavorite,
        toggleFavorite,
      }}
    >
      {children}
    </FavoritesContext.Provider>
  );
};

export const useFavorites = () => {
  const ctx = useContext(FavoritesContext);
  if (!ctx)
    throw new Error("useFavorites must be used within FavoritesProvider");
  return ctx;
};
