package com.wiss.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "event_favorites")
public class EventFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long eventId;

    protected EventFavorite() {}

    public EventFavorite(Long userId, Long eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    // Getter und Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
}
