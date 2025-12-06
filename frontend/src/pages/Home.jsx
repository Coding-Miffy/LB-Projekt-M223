// Importiere den useNavigate-Hook aus react-router-dom für Navigation zwischen Seiten
import { useNavigate } from 'react-router-dom';
// Importiere die wiederverwendbare Button-Komponente
import Button from '../components/button';

const Home = () => {
    // Initialisiere den Hook zum Navigieren innerhalb der App
    const navigate = useNavigate();

    // Funktion, die beim Klick auf den Button ausgeführt wird
    // Navigiert zur Seite "/live-events"
    const handleClick = () => {
        navigate('/live-events');
    }

    return (
        <div className='home-container'>
            {/* Titel der Startseite */}
            <h1 className='home-heading'>Earth Natural Events Tracker</h1>

            {/* Button zum Wechseln zur Seite mit den Live Events */}
            <Button
                text={'Check it out'} // Text auf dem Button
                onButtonClick={handleClick} // Klick-Handler
                className={'home-button'} // CSS-Klasse für Styling
            />
        </div>
    );
};

export default Home;