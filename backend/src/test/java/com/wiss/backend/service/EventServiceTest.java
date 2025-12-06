package com.wiss.backend.service;

import com.wiss.backend.dto.EventDTO;
import com.wiss.backend.entity.Event;
import com.wiss.backend.model.EventCategory;
import com.wiss.backend.model.EventStatus;
import com.wiss.backend.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * <h2>
 *     Unit-Tests für den {@link EventService}
 * </h2>
 *
 * <p>
 *     Diese Testklasse prüft die Kernfunktionalität des {@link EventService} mithilfe von Mockito.
 *     Der {@link EventRepository} wird dabei als Mock verwendet, um den Service isoliert zu testen.
 * </p>
 *
 * <h3>
 *     Getestete Methoden:
 * </h3>
 * <ul>
 *     <li>{@link EventService#getAllEvents()}</li>
 *     <li>{@link EventService#getAllEventsAsDTO()}</li>
 * </ul>
 *
 * <h3>
 *     Verwendete Testwerkzeuge:
 * </h3>
 * <ul>
 *     <li>{@code @ExtendWith(MockitoExtension.class)} für Mockito-Integration</li>
 *     <li>{@code @Mock} und {@code @InjectMocks} zur Simulation abhängiger Komponenten</li>
 * </ul>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-19
 * @see EventService
 * @see EventRepository
 */
@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    /**
     * Testet, ob {@link EventService#getAllEvents()} eine korrekte Liste von {@link Event}-Objekten zurückgibt.
     * Die Rückgabegrösse wird überprüft und sichergestellt, dass {@link EventRepository#findAll()} aufgerufen wurde.
     */
    @Test
    public void whenGetAllEvents_thenReturnListOfEvents() {
        List<Event> mockEvents = List.of(new Event(), new Event());
        when(eventRepository.findAll()).thenReturn(mockEvents);

        List<Event> result = eventService.getAllEvents();

        assertEquals(2, result.size());
        verify(eventRepository).findAll();
    }

    /**
     * Testet, ob {@link EventService#getAllEventsAsDTO()} korrekt aus einer {@link Event}-Liste
     * eine entsprechende {@link EventDTO}-Liste erstellt und alle Felder korrekt abbildet.
     */
    @Test
    void whenGetAllEventsAsDTO_thenFieldsAreMappedCorrectly() {
        Event event = new Event();
        event.setTitle("Vulkanausbruch Island");
        event.setDate(LocalDate.of(2023, 5, 12));
        event.setCategory(EventCategory.volcanoes);
        event.setLatitude(64.9631);
        event.setLongitude(-19.0208);
        event.setStatus(EventStatus.open);

        when(eventRepository.findAll()).thenReturn(List.of(event));

        List<EventDTO> result = eventService.getAllEventsAsDTO();

        assertEquals(1, result.size());
        EventDTO dto = result.getFirst();
        assertEquals("Vulkanausbruch Island", dto.getTitle());
        assertEquals(LocalDate.of(2023, 5, 12), dto.getDate());
        assertEquals(EventCategory.volcanoes, dto.getCategory());
        assertEquals(64.9631, dto.getLatitude());
        assertEquals(-19.0208, dto.getLongitude());
        assertEquals(EventStatus.open, dto.getStatus());
    }
}