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

    // Navigiert zur Login-Seite
    const handleLoginClick = () => {
        navigate('/login');
    }

    return (
        <div className='home-container'>
            {/* Titel der Startseite */}
            <h1 className='home-heading'>Earth Natural Events Tracker</h1>

            {/* Button-Gruppe */}
            <div className="home-actions">
                <Button
                    text={'Proceed without login'} // Text auf dem Button
                    onButtonClick={handleClick} // Klick-Handler
                    className={'home-button'} // CSS-Klasse für Styling
                />
                <Button
                    text={'Login to account'}
                    onButtonClick={handleLoginClick}
                    className={'home-button'}
                />
            </div>
        </div>
    );
};

export default Home;