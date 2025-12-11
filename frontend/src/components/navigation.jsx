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
                    <NavLink to='/account' className={({ isActive }) => isActive ? 'active' : ''} style={{
                        marginLeft: '20px',
                        padding: '5px 10px',
                        background: (user?.role === 'ADMIN') ? '#dc3545' : '#007bff',
                        color: 'white',
                        borderRadius: '4px',
                        fontSize: '14px',
                        textDecoration: 'none'
                    }}>
                        👤 {user?.username ?? 'User'} ({user?.role ?? 'USER'})
                    </NavLink>

                    <button
                        onClick={logout}
                        style={{
                            marginLeft: '12px',
                            padding: '5px 10px',
                            background: '#6c757d',
                            color: 'white',
                            border: 'none',
                            borderRadius: '4px',
                            cursor: 'pointer',
                            fontSize: '14px'
                        }}
                    >
                        Logout
                    </button>
                </>
            )}

        </nav>
    );
}

export default Navigation;