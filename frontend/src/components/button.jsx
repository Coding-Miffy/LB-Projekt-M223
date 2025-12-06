// Definiere eine funktionale Komponente namens Button
// Sie akzeptiert vier Props:
// - text: Beschriftung des Buttons
// - onButtonClick: Callback-Funktion, die bei Klick aufgerufen wird
// - disabled: Optional, deaktiviert den Button bei true (Standard: false)
// - className: Optional, zusätzliche CSS-Klassen für Styling
const Button = ({ text, onButtonClick, disabled = false, className }) => {
    return (
        // Button-Element mit folgenden Attributen:
        <button
            onClick={onButtonClick} // Klick-Ereignis
            disabled={disabled}     // Deaktiviert den Button, wenn true
            className={`button ${className || ""}`} // Standardklasse + optionale Zusatzklasse
        >
            {/* Beschriftung des Buttons */}
            {text}
        </button>
    );
};

// Exportiere die Komponente, damit sie in anderen Dateien verwendet werden kann
export default Button;