import { Routes, Route } from "react-router-dom";
import './App.css';
import 'leaflet/dist/leaflet.css';

import Home from './pages/Home';
import LiveEvents from './pages/LiveEvents';
import Archive from './pages/Archive';
import EventsManager from './pages/EventsManager';
import NotFoundPage from './pages/NotFoundPage';
import Layout from './components/layout';
import Login from './pages/Login';
import Account from './pages/Account';
import Forbidden from './pages/Forbidden';
import ProtectedRoute from './components/protected-route'; 

function App() {


  return (
    <Routes>
      <Route index element={<Home />} /> {/* Ausserhalb Layout -> Keine Nav, kein Footer */}
      <Route path='/' element={<Layout />}>
        <Route path="forbidden" element={<Forbidden />} />
        <Route path='/login' element={<Login />} />
        <Route path='/account' element={
            <ProtectedRoute>
              <Account />
            </ProtectedRoute>
        } />
        <Route path='/live-events' element={<LiveEvents />} />
        
         /* Geschützte Route für angemeldete User */
          <Route path='/archive' element={
            <ProtectedRoute>
              <Archive />
            </ProtectedRoute>} />

           /*Admin-only Route */
          <Route path='/manage-events' element={
            <ProtectedRoute requiredRole='ADMIN'>
            <EventsManager />
          </ProtectedRoute>} />

          <Route path='*' element={<NotFoundPage />} />
      </Route>
    </Routes>

  )
}

export default App;
