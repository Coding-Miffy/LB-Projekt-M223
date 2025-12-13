import { describe, it, expect, vi } from 'vitest';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { MemoryRouter } from 'react-router-dom';
import Navigation from '../components/navigation.jsx';
import { AuthContext } from '../contexts/AuthContext.jsx';

describe('Navigation', () => {
  it('shows Login link when not authenticated', () => {
    render(
      <AuthContext.Provider value={{ isAuthenticated: false, user: null }}>
        <MemoryRouter>
          <Navigation />
        </MemoryRouter>
      </AuthContext.Provider>
    );

    expect(screen.getByText(/Login/i)).toBeInTheDocument();
  });

  it('shows user info and logout when authenticated and calls logout', async () => {
    const user = userEvent.setup();
    const fakeUser = { username: 'alice', role: 'ADMIN' };
    const logout = vi.fn();

    render(
      <AuthContext.Provider value={{ isAuthenticated: true, user: fakeUser, logout }}>
        <MemoryRouter>
          <Navigation />
        </MemoryRouter>
      </AuthContext.Provider>
    );

    expect(screen.getByText(/alice/i)).toBeInTheDocument();
    expect(screen.getByText(/admin/i)).toBeInTheDocument();

    await user.click(screen.getByRole('button', { name: /logout/i }));
    expect(logout).toHaveBeenCalledTimes(1);
  });
});
