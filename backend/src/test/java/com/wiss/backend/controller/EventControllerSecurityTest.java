package com.wiss.backend.controller;

import com.wiss.backend.entity.Event;
import com.wiss.backend.model.EventCategory;
import com.wiss.backend.model.EventStatus;
import com.wiss.backend.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <h2>
 *     Security-Tests für den EventController
 * </h2>
 *
 * <p>
 *     Diese Testklasse überprüft sämtliche sicherheitsrelevanten Zugriffsbeschränkungen
 *     des {@link com.wiss.backend.controller.EventController}.
 *     Getestet wird das Verhalten der REST-Endpunkte abhängig vom Authentifizierungs-
 *     und Berechtigungsstatus des aufrufenden Benutzers.
 * </p>
 *
 * <h3>Ziele der Testklasse:</h3>
 * <ul>
 *     <li>Sicherstellen, dass ungeauthentifizierte Anfragen korrekt blockiert werden (HTTP 403).</li>
 *     <li>Überprüfen, dass Nutzer:innen mit Rolle <code>USER</code> nur lesenden Zugriff erhalten.</li>
 *     <li>Verifizieren, dass Nutzer:innen mit Rolle <code>ADMIN</code> erweiterte Rechte wie Erstellen und Löschen besitzen.</li>
 *     <li>Absicherung der Spring-Security-Konfiguration durch MockMvc-Integrationstests.</li>
 * </ul>
 *
 * <h3>Testaufbau:</h3>
 * <p>
 *     Vor jedem Test wird ein einzelnes Test-Event in einer isolierten H2-Datenbank angelegt.
 *     Die Tests verwenden <code>@WithMockUser</code>, um unterschiedliche Rollen zu simulieren.
 * </p>
 *
 * <h3>Verwendete Technologien:</h3>
 * <ul>
 *     <li><b>SpringBootTest</b> & MockMvc für End-to-End-Test der Webschicht</li>
 *     <li><b>H2-Datenbank</b> durch <code>@ActiveProfiles("test")</code></li>
 *     <li><b>Spring Security Test</b> zur Rollen-Simulation</li>
 * </ul>
 *
 * <h3>Geprüfte Szenarien:</h3>
 * <ul>
 *     <li>Zugriff ohne Login → 403 Forbidden</li>
 *     <li>Zugriff als USER → nur lesende Endpunkte erlaubt</li>
 *     <li>Zugriff als ADMIN → CRUD vollständig erlaubt</li>
 * </ul>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.controller.EventController
 * @see org.springframework.security.test.context.support.WithMockUser
 * @see com.wiss.backend.repository.EventRepository
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EventControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        // Testdaten vorbereiten
        eventRepository.deleteAll();

        Event testEvent = new Event(
                1L,
                "Waldbrand Kalifornien",
                LocalDate.of(2022, 8, 20),
                EventCategory.wildfires,
                36.7783,
                -119.4179,
                EventStatus.open,
                null
        );
        eventRepository.save(testEvent);
    }

    // Tests ohne Login
    @Test
    void getAllEvents_withoutAuth_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isForbidden());  // 403 erwartet
    }

    @Test
    void createEvent_withoutAuth_shouldReturn403() throws Exception {
        String eventJson = """
                {
                    "title": "Water Discoloration in Lake Victoria",
                    "date": "2025-07-12",
                    "category": "waterColor",
                    "longitude": 33.00,
                    "latitude": -1.00,
                    "status": "open"
                }
                """;

        mockMvc.perform(post("/api/events/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson))
                .andExpect(status().isForbidden());  // 403 erwartet
    }

    // Tests als User
    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void getAllEvents_asUser_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())  // 200 OK erwartet
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void getEventsByStatus_asUser_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/events/status/closed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void createEvent_asUser_shouldReturn403() throws Exception {
        String eventJson = """
                {
                    "title": "Water Discoloration in Lake Victoria",
                    "date": "2025-07-12",
                    "category": "waterColor",
                    "longitude": 33.00,
                    "latitude": -1.00,
                    "status": "open"
                }
                """;

        mockMvc.perform(post("/api/events/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson))
                .andExpect(status().isForbidden());  // 403 Forbidden erwartet!
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void deleteEvent_asUser_shouldReturn403() throws Exception {
        Long eventId = eventRepository.findAll().getFirst().getId();

        mockMvc.perform(delete("/api/events/" + eventId))
                .andExpect(status().isForbidden());  // 403 Forbidden erwartet!
    }

    // Tests als Admin
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getAllEvents_asAdmin_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createEvent_asAdmin_shouldReturn201() throws Exception {
        String eventJson = """
                {
                    "title": "Water Discoloration in Lake Victoria",
                    "date": "2025-07-12",
                    "category": "waterColor",
                    "longitude": 33.00,
                    "latitude": -1.00,
                    "status": "open"
                }
                """;

        mockMvc.perform(post("/api/events/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson))
                .andExpect(status().isCreated())  // 201 Created erwartet!
                .andExpect(jsonPath("$.title")
                        .value("Water Discoloration in Lake Victoria"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteEvent_asAdmin_shouldReturn204() throws Exception {
        Long eventId = eventRepository.findAll().getFirst().getId();

        mockMvc.perform(delete("/api/events/" + eventId))
                .andExpect(status().isNoContent());  // 204 No Content erwartet!
    }


}
