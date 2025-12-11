import { useAuth } from '../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';

const Account = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
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
          </div>
        ) : (
          <p>Kein Benutzer gefunden.</p>
        )}
      </div>
    </div>
  );
};

export default Account;
