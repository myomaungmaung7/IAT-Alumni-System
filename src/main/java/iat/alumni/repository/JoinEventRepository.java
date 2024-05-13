package iat.alumni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iat.alumni.model.Event;
import iat.alumni.model.JoinEvent;
import iat.alumni.model.User;

@Repository
public interface JoinEventRepository extends JpaRepository<JoinEvent, Integer> {
	boolean existsByUserAndEvent(User user, Event event);

	@Query("SELECT COUNT(je) FROM JoinEvent je WHERE je.event = :event")
	Integer countByEvent(@Param("event") Event event);
}
