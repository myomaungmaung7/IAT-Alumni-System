package iat.alumni.service;

import java.util.List;

import iat.alumni.model.Event;
import iat.alumni.model.EventDto;
import iat.alumni.model.User;

public interface EventService {
	Event createEvent(Event event);
	Event getEventById(Integer eventId);
	List<Event> getAllEvent();
	void deleteEventById(Integer eventId);
	List<Event> getApprovedEvents();
	Event findById(Integer eventId);
	List<Event> findEventsByUser(User user);
	List<Event> findApprovedEventsByUser(User user);
	List <EventDto> search(String keyword);
	Long count();
}