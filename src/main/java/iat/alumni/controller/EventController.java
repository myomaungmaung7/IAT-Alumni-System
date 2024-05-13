package iat.alumni.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import iat.alumni.dto.CommentRequest;
import iat.alumni.dto.JoinEventRequest;
import iat.alumni.model.Comment;
import iat.alumni.model.Event;
import iat.alumni.model.User;
import iat.alumni.model.UserSession;
import iat.alumni.service.CommentService;
import iat.alumni.service.EventService;
import iat.alumni.service.JoinEventService;
import iat.alumni.util.FileUploadUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping (value = "/alumni/event")
@SessionAttributes(value = { "userSession" }, types = { UserSession.class })
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private JoinEventService joinEventService;
	
	@Autowired
    private CommentService commentService;
	
	//For Event Posting
	@GetMapping("/create")
	public String ShowEventForm(Model model, HttpSession session) {
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		if (userSession == null || userSession.getUser() == null) {
            return "redirect:/login";
        }
		
		User currentUser = userSession.getUser();
		
		Event event = new Event();
		model.addAttribute("event", event);
		
		model.addAttribute("currentUser", currentUser);
		return "event-new";
	}
	
	//Also For Event Posting post mapping
	@PostMapping("/post")
	public String saveEvent(@ModelAttribute("event") @Valid Event event, BindingResult result, Model model, 
			@RequestParam("eventFile") MultipartFile eventFile, HttpSession session) 
				throws IOException {
			
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		 if (userSession == null || userSession.getUser() == null) {
	            return "redirect:/login";
	     }
		 
		 User currentUser = userSession.getUser();

		 if (eventFile.isEmpty()) {
		        result.rejectValue("eventFile", "error.event", "Please select a file.");
		 }
		 
		if (result.hasErrors()) {
			model.addAttribute("currentUser", currentUser);
			return "event-new";
		}
		
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
			
		String fileName = StringUtils.cleanPath(eventFile.getOriginalFilename());
		event.setPhoto(fileName);
		event.setDate(date);
		event.setTime(time);
		event.setUser(currentUser);
		
		Event savedEvent = eventService.createEvent(event);
		
		String uploadDir = "event-photos/" + savedEvent.getEventId();
		
		FileUploadUtil.saveFile(uploadDir, fileName, eventFile);
		
		return "redirect:/alumniHome";	
	}
	
	//Event Detail Page for Alumni
	@GetMapping("/eventDetail/{eventId}")
	public String viewEventDetails(@PathVariable("eventId") Integer eventId, Model model, HttpSession session) {
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		if (userSession == null || userSession.getUser() == null) {
            return "redirect:/login";
        }
		
		Event event = eventService.getEventById(eventId);
		User currentUser = userSession.getUser();
		
		boolean userJoinedEvent = joinEventService.isUserJoinedEvent(currentUser, event);
		event.setJoined(userJoinedEvent);
		
		int numberOfJoinedUsers = joinEventService.getNumberOfJoinedUsers(event);
		
		List<Comment> comments = commentService.getCommentsByEventId(eventId);
		
	    model.addAttribute("event", event);
	    model.addAttribute("currentUser", currentUser);
	    model.addAttribute("numberOfJoinedUsers", numberOfJoinedUsers);
	    model.addAttribute("comments", comments);
	    return "alumni-event-detail";
	}
	
	//Join Events
	@PostMapping("/join/{eventId}")
	public String joinEvent(@PathVariable("eventId") Integer eventId,HttpSession session,@ModelAttribute("joinEventRequest") JoinEventRequest joinEventRequest) {
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		Event event = eventService.getEventById(eventId);
		User user = userSession.getUser();
		
        if (user == null || event == null) {
            return "redirect:/login";
        }

        boolean isUserJoined = joinEventService.isUserJoinedEvent(user, event);
        if (!isUserJoined) {
            joinEventService.joinEvent(user, event);
        }
        return "redirect:/alumni/event/eventDetail/" + eventId;
    }
	
	//for Comment Under Event
	@PostMapping("/comment/{eventId}")
    public String addComment(@PathVariable("eventId") Integer eventId, @RequestParam("commentText") String commentText,
                             HttpSession session, @Valid @ModelAttribute("commentRequest") CommentRequest commentRequest, BindingResult result, RedirectAttributes redirectAttributes) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        User user = userSession.getUser();
        Event event = eventService.getEventById(eventId);

        if (user == null || event == null) {
            return "redirect:/login";
        }
        
        if (result.hasErrors()) {
        	redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentRequest", result);
            redirectAttributes.addFlashAttribute("commentRequest", commentRequest);
            return "redirect:/alumni/event/detail/{eventId}";
        }

        commentService.saveComment(commentText, user, event);

        return "redirect:/alumni/event/eventDetail/" + eventId;
    }
	
	@GetMapping("/postedEvents")
	public String getPostedEvents(Model model, HttpSession session) {
	    UserSession userSession = (UserSession) session.getAttribute("userSession");
	    if (userSession == null || userSession.getUser() == null) {
	        return "redirect:/login";
	    }

	    User currentUser = userSession.getUser();
	    List<Event> myEvents = eventService.findApprovedEventsByUser(currentUser);

	    model.addAttribute("myEvents", myEvents);
	    model.addAttribute("currentUser", currentUser);
	    return "posted-events";
	}

}
