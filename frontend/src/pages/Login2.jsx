import { useState } from "react";
import { useNavigate } from "react-router-dom";
import LoginForm from "../components/login-form";
import { login } from "../services/auth-service";  // ← NEU!

const Login = () => {
  const navigate = useNavigate();
  const [error, setError] = useState("");

  /**
   * Handler für Login (wird von LoginForm aufgerufen)
   * @param {Object} loginData - { usernameOrEmail, password }
   */
  const handleLogin = async (loginData) => {
    setError("");  // Reset error
    
      try {
      console.log("🔄 Login wird gestartet...");

      // API Call zum Backend
      const response = await login(loginData.usernameOrEmail, loginData.password);

      console.log("✅ Login erfolgreich:", response);

      // Erfolg! Redirect to Live Events
      navigate("/live-events");

    } catch (err) {
      console.error("❌ Login fehlgeschlagen:", err);
      setError(err.message || "Login fehlgeschlagen. Bitte prüfe deine Eingaben.");
    }
  };
  return (
    <div className="auth-page">
      <div className="auth-container">
        {/* Error Message (wenn vorhanden) */}
        {error && (
          <div
            className="error-message"
            style={{
              color: "red",
              padding: "10px",
              backgroundColor: "#ffe6e6",
              borderRadius: "4px",
              marginBottom: "15px",
              textAlign: "center",
            }}
          >
            ❌ {error}
          </div>
        )}

        {/* Bestehende LoginForm Component */}
        <LoginForm onLogin={handleLogin} />

        <div className="auth-links">
          <p>Noch kein Account?</p>
          <p>Registrierung kommt später!</p>
        </div>

        {/* Test Credentials Hinweis */}
        <div
          style={{
            marginTop: "20px",
            padding: "15px",
            backgroundColor: "#e7f3ff",
            borderRadius: "4px",
            fontSize: "14px",
          }}
        >
          <strong>🧪 Test-Accounts:</strong>
          <br />
          <br />
          <strong>Admin:</strong>
          <br />
          Username: admin
          <br />
          Email: admin@eonet.com
          <br />
                    Passwort: admin123
          <br />
          <br />
          <strong>Normaler User:</strong>
          <br />
          Username: user
          <br />
          Email: user@eonet.com
          <br />
          Passwort: user123
        </div>
      </div>
    </div>
  );
};

export default Login;
