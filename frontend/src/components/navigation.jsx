import { NavLink } from "react-router-dom";
import { useContext } from 'react';
import { AuthContext } from '../contexts/AuthContext';

// Navigation component
const Navigation = () => {
    const { user, isAuthenticated, logout } = useContext(AuthContext);

    return (
        <nav className='layout-header-nav'>

            <NavLink to='/' className={({ isActive }) => isActive ? 'active' : ''}>
                Home
            </NavLink>

            <NavLink to='/live-events' className={({ isActive }) => isActive ? 'active' : ''}>
                Live Events
            </NavLink>

            <NavLink to='/archive' className={({ isActive }) => isActive ? 'active' : ''}>
                Archive
            </NavLink>

            <NavLink to='/manage-events' className={({ isActive }) => isActive ? 'active' : ''}>
                Manage Events
            </NavLink>

            {/* Authentication area */}
            {!isAuthenticated ? (
                <NavLink to='/login' className={({ isActive }) => isActive ? 'active' : ''}>
                    Login
                </NavLink>
            ) : (
                <>
                    <NavLink to='/account' className={({ isActive }) => `nav-user ${user?.role === 'ADMIN' ? 'admin' : 'user'} ${isActive ? 'active' : ''}`}>
                        <span className="nav-avatar">👤</span>
                        <span className="nav-username">{user?.username ?? 'User'}</span>
                        <span className="nav-role">({user?.role ?? 'USER'})</span>
                    </NavLink>

                    <button onClick={logout} className="nav-logout">Logout</button>
                </>
            )}

        </nav>
    );
}

export default Navigation;