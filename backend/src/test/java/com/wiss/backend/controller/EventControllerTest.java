package com.wiss.backend.controller;

import com.wiss.backend.dto.EventDTO;
import com.wiss.backend.model.EventCategory;
import com.wiss.backend.model.EventStatus;
import com.wiss.backend.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <h2>
 *     Integrationstests für {@link EventController}
 * </h2>
 * <p>
 *     Diese Tests prüfen die REST-Endpunkte des {@link EventController} mithilfe von {@link WebMvcTest}.
 *     Der {@link EventService} wird dabei als Mock eingebunden, um gezielt die Controllerlogik zu testen.
 * </p>
 *
 * <h3>
 *     Testkontext:
 * </h3>
 * <ul>
 *     <li>Verwendet {@link MockMvc} zur Simulation von HTTP-Requests</li>
 *     <li>Testet JSON-Antworten und Statuscodes</li>
 *     <li>Mocking des {@link EventService} mit {@code @MockBean}</li>
 * </ul>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-19
 * @see EventController
 * @see EventService
 */
@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    /**
     * Testet den Endpunkt {@code GET /api/events}, der alle Events als JSON zurückgeben soll.
     * <p>
     *     Erwartung: Die Rückgabe enthält zwei Events, eines davon mit dem Titel "Waldbrand Kalifornien".
     * </p>
     */
    @Test
    public void whenGetAllEvents_thenReturnJsonArray() throws Exception {
        EventDTO event1 = new EventDTO(
                1L,
                "Waldbrand Kalifornien",
                LocalDate.of(2022, 8, 20),
                EventCategory.wildfires,
                36.7783,
                -119.4179,
                EventStatus.open
        );

        EventDTO event2 = new EventDTO(
                2L,
                "Waldbrand Australien",
                LocalDate.of(2021, 12, 15),
                EventCategory.wildfires,
                -25.2744,
                133.7751,
                EventStatus.closed
        );

        List<EventDTO> events = Arrays.asList(event1, event2);

        when(eventService.getAllEventsAsDTO()).thenReturn(events);

        mockMvc.perform(get("/api/events")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Waldbrand Kalifornien")));
    }

    /**
     * Testet den Endpunkt {@code GET /api/events/status/closed}, der Events nach Status filtert.
     * <p>
     *     Erwartung: Genau ein Event mit dem Titel "Überschwemmung Italien" und Status "closed".
     * </p>
     */
    @Test
    public void whenGetEventsByStatus_thenReturnFilteredJsonArray() throws Exception {
        EventDTO event = new EventDTO(
                1L,
                "Überschwemmung Italien",
                LocalDate.of(2023, 6, 12),
                EventCategory.floods,
                41.9028,
                12.4964,
                EventStatus.closed
        );

        when(eventService.getEventsByStatusAsDTO(EventStatus.closed))
                .thenReturn(List.of(event));

        mockMvc.perform(get("/api/events/status/closed").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Überschwemmung Italien")))
                .andExpect(jsonPath("$[0].status", is("closed")));
    }
}
