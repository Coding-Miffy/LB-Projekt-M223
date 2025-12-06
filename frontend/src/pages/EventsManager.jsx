import { useState, useEffect } from 'react';

import CustomEventForm from '../components/create-event-form';

import CustomEventCard from '../components/create-event-card';

import { getAllEvents, createEvent, updateEvent, deleteEvent } from '../utils/java-api';

const CustomEvents = () => {
    // Zustand für alle gespeicherten Events
    const [savedEvents, setSavedEvents] = useState([]);

    // Events beim Start laden
    useEffect(() => {
        const loadEvents = async () => {
            const loadedEvents = await getAllEvents();
            setSavedEvents(loadedEvents);
        };
        loadEvents();
    }, []);

    // Neues Event hinzufügen (von Form aufgerufen)
    const handleEventSubmit = async (newEventData) => {
        try {
            const createdEvent = await createEvent(newEventData);
            if (createdEvent) {
                setSavedEvents((prevEvents) => [
                    ...prevEvents,
                    createdEvent,
                ]);
            }
        } catch (error) {
            console.error("Fehler beim Erstellen des Events: ", error);
        }
    };

    // Event bearbeiten (von Card aufgerufen)
    const handleEventEdit = async (updatedEvent) => {
        try {
            const response = await updateEvent(
                updatedEvent.id,
                updatedEvent
            );
            if (response) {
                const updatedEvents = savedEvents.map((e) => e.id === updatedEvent.id ? updatedEvent : e);

                setSavedEvents(updatedEvents);
                console.log("Event erfolgreich aktualisiert: ", updatedEvent);
            }
        } catch (error) {
            console.error("Fehler beim Aktualisieren des Events: ", error);
        }
    };

    // Event löschen (von Card aufgerufen)
    const handleEventDelete = async (eventId) => {
        console.log("handleEventDelete aufgerufen mit ID: ", eventId);

        try {
            const deletedId = await deleteEvent(eventId);
            console.log("Antwort vom Backend: ", deletedId);

            if (deletedId) {
                const updatedEvents = savedEvents.filter(
                    (e) => e.id !== eventId
                );
                setSavedEvents(updatedEvents);
            }
        } catch (error) {
            console.error("Fehler beim Löschen des Events: ", error);
        }
    };

    return (
        <div className="page-container">
            <h1 className="section-title">Manage Events</h1>

            {/* Formular zum Erstellen neuer Events */}
            <CustomEventForm onEventSubmit={handleEventSubmit} />

            <div className='saved-events-section'>
                <h2>Saved events: ({savedEvents.length})</h2>

                {/* Wenn keine Events gespeichert sind, wird ein Hinweis angezeigt */}
                {savedEvents.length === 0 ? (
                    <div className='no-events'>
                        <p>No events created yet.</p>
                        <p>Use the form above to create your first event!</p>
                    </div>
                ) : (
                    // Darstellung der Liste aller gespeicherten Events
                    <div className='custom-events-list'>
                        {savedEvents.map((event) => (
                            <CustomEventCard
                                key={event.id}
                                event={event}
                                onEdit={handleEventEdit}
                                onDelete={handleEventDelete}
                            />
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
};

export default CustomEvents;