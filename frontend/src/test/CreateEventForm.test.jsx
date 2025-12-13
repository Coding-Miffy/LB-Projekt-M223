import { describe, it, expect, vi } from 'vitest';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import CustomEventForm from '../components/create-event-form.jsx';

describe('CustomEventForm', () => {
  it('validates required fields and calls onEventSubmit with event data', async () => {
    const user = userEvent.setup();
    const onEventSubmit = vi.fn();
    render(<CustomEventForm onEventSubmit={onEventSubmit} />);

    // Submit empty form -> validation errors
    await user.click(screen.getByRole('button', { name: /create event/i }));
    expect(screen.getByText(/Enter title/i)).toBeInTheDocument();
    expect(screen.getByText(/Choose a date/i)).toBeInTheDocument();
    expect(screen.getByText(/Enter longitude/i)).toBeInTheDocument();
    expect(screen.getByText(/Enter latitude/i)).toBeInTheDocument();
    expect(onEventSubmit).not.toHaveBeenCalled();

    // Fill form and submit
    await user.type(screen.getByLabelText(/Title/i), 'Test Event');
    await user.type(screen.getByLabelText(/Date/i), '2025-12-13');
    await user.selectOptions(screen.getByLabelText(/Category/i), 'volcanoes');
    await user.type(screen.getByLabelText(/Longitude/i), '12.34');
    await user.type(screen.getByLabelText(/Latitude/i), '56.78');
    await user.selectOptions(screen.getByLabelText(/Status/i), 'open');

    await user.click(screen.getByRole('button', { name: /create event/i }));

    expect(onEventSubmit).toHaveBeenCalledTimes(1);
    expect(onEventSubmit).toHaveBeenCalledWith({
      title: 'Test Event',
      date: '2025-12-13',
      category: 'volcanoes',
      longitude: '12.34',
      latitude: '56.78',
      status: 'open',
    });
  });
});
