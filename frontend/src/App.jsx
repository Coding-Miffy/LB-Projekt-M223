import { Routes, Route } from "react-router-dom";
import './App.css';
import 'leaflet/dist/leaflet.css';

import Home from './pages/Home';
import LiveEvents from './pages/LiveEvents';
import Archive from './pages/Archive';
import EventsManager from './pages/EventsManager';
import NotFoundPage from './pages/NotFoundPage';
import Layout from './components/layout';

function App() {


  return (
    <Routes>
      <Route index element={<Home />} /> {/* Ausserhalb Layout -> Keine Nav, kein Footer */}
      <Route path='/' element={<Layout />}>
        <Route path='/live-events' element={<LiveEvents />} />
        <Route path='/archive' element={<Archive />} />
        <Route path='/manage-events' element={<EventsManager />} />
        <Route path='*' element={<NotFoundPage />} />
      </Route>
    </Routes>

  )
}

export default App;
