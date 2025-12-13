import { describe, it, expect, vi } from "vitest";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import LoginForm from "../components/login-form.jsx";
import { AuthContext } from "../contexts/AuthContext.jsx";
import { MemoryRouter } from "react-router-dom";

describe("LoginForm", () => {
  it("shows validation errors and calls onLogin when form valid", async () => {
    const user = userEvent.setup();
    const onLogin = vi.fn();
    render(
      <AuthContext.Provider
        value={{ login: vi.fn(), isLoading: false, isAuthenticated: false }}
      >
        <MemoryRouter>
          <LoginForm onLogin={onLogin} />
        </MemoryRouter>
      </AuthContext.Provider>
    );

    // Submit empty form -> validation errors
    await user.click(screen.getByRole("button", { name: /einloggen/i }));
    expect(
      screen.getByText(/Benutzername oder Email ist erforderlich/i)
    ).toBeInTheDocument();
    expect(screen.getByText(/Passwort ist erforderlich/i)).toBeInTheDocument();
    expect(onLogin).not.toHaveBeenCalled();

    // Fill form and submit -> onLogin called with values
    await user.type(screen.getByLabelText(/Benutzername oder Email/i), "foo");
    await user.type(screen.getByLabelText(/Passwort/i), "secret123");
    await user.click(screen.getByRole("button", { name: /einloggen/i }));

    expect(onLogin).toHaveBeenCalledTimes(1);
    expect(onLogin).toHaveBeenCalledWith({
      usernameOrEmail: "foo",
      password: "secret123",
    });
  });
});
