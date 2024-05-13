package iat.alumni.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iat.alumni.model.Event;
import iat.alumni.model.JoinEvent;
import iat.alumni.model.User;
import iat.alumni.repository.JoinEventRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JoinEventService {
	
	@Autowired
    private JoinEventRepository joinEventRepository;

	public void joinEvent(User user, Event event) {
		if (!isUserJoinedEvent(user, event)) {
            JoinEvent joinEvent = new JoinEvent();
            joinEvent.setUser(user);
            joinEvent.setEvent(event);
            joinEventRepository.save(joinEvent);
            event.setJoined(true);
        }
    }
	
	public boolean isUserJoinedEvent(User user, Event event) {
        return joinEventRepository.existsByUserAndEvent(user, event);
    }
	
	public Integer getNumberOfJoinedUsers(Event event) {
        return joinEventRepository.countByEvent(event);
    }
}
