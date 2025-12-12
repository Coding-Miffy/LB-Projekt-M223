import { useAuth } from '../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';
import { useState, useMemo } from 'react';
import categoryEmoji from '../utils/categoryEmoji';

const sampleFallback = [
  { id: 's1', title: 'Wildfire near California', category: 'wildfires', date: '2025-11-20', latitude: 36.7783, longitude: -119.4179, description: 'Active wildfire reported in rural area.' },
  { id: 's2', title: 'Volcanic activity - Mt. Etna', category: 'volcanoes', date: '2025-11-15', latitude: 37.7510, longitude: 14.9934, description: 'Increased seismicity and gas emissions.' },
  { id: 's3', title: 'Earthquake off Chile coast', category: 'earthquakes', date: '2025-10-05', latitude: -33.4489, longitude: -70.6693, description: 'Magnitude 5.8 earthquake recorded offshore.' },
  { id: 's4', title: 'Flooding in Rhine valley', category: 'floods', date: '2025-12-02', latitude: 50.1109, longitude: 8.6821, description: 'River levels rising, localized flooding reported.' },
  { id: 's5', title: 'Flooding in Rhine valley', category: 'floods', date: '2025-12-02', latitude: 50.1109, longitude: 8.6821, description: 'River levels rising, localized flooding reported.' },
  { id: 's6', title: 'Flooding in Rhine valley', category: 'floods', date: '2025-12-02', latitude: 50.1109, longitude: 8.6821, description: 'River levels rising, localized flooding reported.' },
  { id: 's7', title: 'Flooding in Rhine valley', category: 'floods', date: '2025-12-02', latitude: 50.1109, longitude: 8.6821, description: 'River levels rising, localized flooding reported.' },
];

const Account = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  // Keep favorites only in memory for now (no persistence)
  const [favorites, setFavorites] = useState(sampleFallback);
  const [expanded, setExpanded] = useState(false);
  const [sortBy, setSortBy] = useState('none');
  const [sortDir, setSortDir] = useState('asc');

  const sortedFavorites = useMemo(() => {
    const arr = [...favorites];
    if (sortBy === 'category') {
      arr.sort((a, b) => (a.category || '').localeCompare(b.category || '', undefined, { sensitivity: 'base' }));
    } else if (sortBy === 'date') {
      arr.sort((a, b) => new Date(a.date) - new Date(b.date));
    }
    if (sortDir === 'desc') arr.reverse();
    return arr;
  }, [favorites, sortBy, sortDir]);

  const visible = (expanded ? sortedFavorites : sortedFavorites.slice(0, 3));

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  const removeFavorite = (id) => {
    setFavorites(prev => prev.filter(f => f.id !== id));
  };

  return (
    <div className="account-page">
      <div className="account-container">
        <h2>Mein Account</h2>

        {user ? (
          <div className="account-details">
            <p><strong>Benutzername:</strong> {user.username}</p>
            <p><strong>Email:</strong> {user.email}</p>
            <p><strong>Rolle:</strong> {user.role}</p>
            <div style={{ marginTop: '18px' }}>
              <button onClick={handleLogout} className="btn-logout">Logout</button>
            </div>

            <hr style={{ margin: '20px 0' }} />

            <section>
              <h3>Favoriten</h3>

              <div style={{ display: 'flex', gap: '12px', alignItems: 'center', marginBottom: '12px' }}>
                <label style={{ fontSize: '14px' }}>Sortiere nach:</label>
                <select value={sortBy} onChange={(e) => setSortBy(e.target.value)}>
                  <option value="none">Keine</option>
                  <option value="category">Kategorie</option>
                  <option value="date">Datum</option>
                </select>

                <label style={{ fontSize: '14px' }}>Richtung:</label>
                <select value={sortDir} onChange={(e) => setSortDir(e.target.value)}>
                  <option value="asc">Aufsteigend</option>
                  <option value="desc">Absteigend</option>
                </select>
              </div>

              {visible.length === 0 ? (
                <p>Keine Favoriten vorhanden.</p>
              ) : (
                <div className="card-grid">
                  {visible.map((ev) => (
                    <div key={ev.id} className="archive-card">
                      <div>
                        <div className="archive-card-title">
                          <div className="emoji">{categoryEmoji(ev.category)}</div>
                          <div>{ev.title}</div>
                        </div>
                        <p className="archive-card-detail">{ev.category} • {ev.date}</p>
                        {ev.description && <p className="archive-card-detail">{ev.description}</p>}
                      </div>
                      <div className="card-actions">
                        <button onClick={() => removeFavorite(ev.id)} className="btn-secondary">Entfernen</button>
                      </div>
                    </div>
                  ))}
                </div>
              )}

              {sortedFavorites.length > 3 && (
                <button onClick={() => setExpanded(!expanded)} style={{ marginTop: '8px' }} className="btn-secondary">
                  {expanded ? 'Weniger anzeigen' : `Mehr anzeigen (${sortedFavorites.length - 3})`}
                </button>
              )}
            </section>

          </div>
        ) : (
          <p>Kein Benutzer gefunden.</p>
        )}
      </div>
    </div>
  );
};

export default Account;
