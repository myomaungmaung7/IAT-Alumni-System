package iat.alumni.service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iat.alumni.model.Event;
import iat.alumni.model.EventDto;
import iat.alumni.model.User;
import iat.alumni.repository.EventRepository;
import iat.alumni.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventServiceImpl implements  EventService {
	
	@Autowired
	private EventRepository eventRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public Event createEvent(Event event) {
		return eventRepo.save(event);
	}

	@Override
	public Event getEventById(Integer eventId) {
		Optional<Event> optionEvent = eventRepo.findById(eventId);
		return optionEvent.get();
	}
	
	@Override
	public List<Event> getAllEvent() {
		return eventRepo.findAll();
	}

	@Override
	public void deleteEventById(Integer eventId) {
		eventRepo.deleteById(eventId);
	}

	@Override
	public List<Event> getApprovedEvents() {
		return eventRepo.findByApprovedTrue();
	}

	@Override
	public Event findById(Integer eventId) {
		return eventRepo.findById(eventId).orElse(null);
	}

	@Override
	public List<Event> findEventsByUser(User user) {
		return eventRepo.findByUser(user);
	}

	@Override
	public List<Event> findApprovedEventsByUser(User user) {
		return eventRepo.findByUserAndApproved(user, true);
	}

	@Override
	public List<EventDto> search(String keyword) {
		List <Event> events = eventRepo.findAll(keyword);
		List <EventDto> eventDtos = new ArrayList <> ();
		for(Event event : events) {
			Optional <User> user = userRepo.findById(event.getUser().getUserId());
			if (user.isPresent() && (keyword == null || user.get().getUserName().contains(keyword))) {
				EventDto eventDto = new EventDto();
				eventDto.setId(event.getEventId());
				eventDto.setTitle(event.getTitle());
				eventDto.setDateTime(event.getDateTime());
				eventDto.setDate(event.getDate());
				eventDto.setDescription(event.getDescription());
				eventDto.setUserName(user.get().getUserName());
				eventDto.setPhoto(user.get().getPhotosImagePath());
				eventDtos.add(eventDto);
			}
		}
		return eventDtos;
	}


	@Override
	public Long count() {
		return eventRepo.count();
	}

}