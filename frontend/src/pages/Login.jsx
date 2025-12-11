import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import LoginForm from "../components/login-form";
import { useAuth } from "../contexts/AuthContext";


const Login = () => {
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const { login } = useAuth();  // login from AuthContext

  const handleLogin = async (loginData) => {
    setError("");
    
    try {
      // Use AuthContext.login()
      await login(loginData.usernameOrEmail, loginData.password);

      console.log("✅ Login erfolgreich");
      navigate('/live-events');
    } catch (err) {
      console.error("❌ Login fehlgeschlagen:", err);
      setError(err.message || "Login fehlgeschlagen");
    }
  };
  return (
    <div className="auth-page">
      <div className="auth-container">
        {/* show error if present */}
        {error && (
          <div style={{ color: 'red', marginBottom: '12px', textAlign: 'center' }}>
            ❌ {error}
          </div>
        )}

        <LoginForm onLogin={handleLogin} />

        <div className="auth-links">
          <p>Noch kein Account?</p>
          <p>Registrierung kommt (vielleicht) später!</p>
        </div>

        {/* Test Credentials Hinweis */}
        <div style={{ 
          marginTop: '20px', 
          padding: '15px', 
          backgroundColor: '#3b5972ff',
          borderRadius: '4px',
          fontSize: '14px'
        }}>
          <strong>Test-Accounts:</strong><br />
          <br />
          <strong>Admin:</strong><br />
          Username: admin<br />
          Email: admin@eonet.com<br />
          Passwort: admin123<br />
          <br />
          <strong>Normaler User:</strong><br />
          Username: user<br />
          Email: user@eonet.com<br />
          Passwort: user123
        </div>
      </div>
    </div>
  );
}

export default Login;
