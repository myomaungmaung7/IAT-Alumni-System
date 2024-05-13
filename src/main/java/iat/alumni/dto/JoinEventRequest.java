package iat.alumni.dto;

import iat.alumni.model.Event;
import iat.alumni.model.User;

public class JoinEventRequest {
	private User user;
    private Event event;
    
    public JoinEventRequest() {
    }
    
    public JoinEventRequest(User user, Event event) {
        this.user = user;
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
