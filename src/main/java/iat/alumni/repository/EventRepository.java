package iat.alumni.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import iat.alumni.model.Event;
import iat.alumni.model.User;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
	@Query("SELECT a FROM Event a WHERE a.user.userName LIKE %?1%")
	List <Event> findAll (String keyword);
	long count();
	
	List<Event> findByApprovedTrue();
	List<Event> findByUser(User user);
	List<Event> findByUserAndApproved(User user, boolean approved);
}