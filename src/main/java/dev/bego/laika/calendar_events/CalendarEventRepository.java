package dev.bego.laika.calendar_events;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    @Query(value = "SELECT * FROM events WHERE user_id = :userId AND (MONTH(start_date) = MONTH(:date) AND YEAR(start_date) = YEAR(:date))", nativeQuery = true)
    public List<CalendarEvent> getEventsInMonthByUserId(@Param("date") LocalDate date, @Param("userId") Long userId);

    @Query(value = "SELECT * FROM events WHERE user_id = :userId AND start_date > :date AND start_date <= DATE_ADD(:date, INTERVAL 7 DAY) ORDER BY start_date ASC LIMIT 7", nativeQuery = true)
    public List<CalendarEvent> getUpcomingEventsByUser(@Param("date") LocalDate date, @Param("userId") Long userId);
}
