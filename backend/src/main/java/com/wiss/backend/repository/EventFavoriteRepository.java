package com.wiss.backend.repository;

import com.wiss.backend.entity.EventFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventFavoriteRepository extends JpaRepository<EventFavorite, Long> {

    /**
     * Findet alle UserSessions eines bestimmten Users
     *
     * @param userId Die ID des Users
     * @return Liste aller GameSessions dieses Users
     */
    List<EventFavorite> findByUserId(Long userId);

    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    void deleteByUserIdAndEventId(Long userId, Long eventId);

    long countByEventId(Long eventId);

}
