package com.wiss.backend.controller;

import com.wiss.backend.entity.Event;
import com.wiss.backend.model.EventCategory;
import com.wiss.backend.model.EventStatus;
import com.wiss.backend.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet
        .AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void getAllEvents_withoutAuth_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isForbidden());  // 403 erwartet
    }

    @Test
    void createEvent_withoutAuth_shouldReturn401() throws Exception {
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
