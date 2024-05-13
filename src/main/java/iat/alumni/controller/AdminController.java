package iat.alumni.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import iat.alumni.model.Article;
import iat.alumni.model.Comment;
import iat.alumni.model.Event;
import iat.alumni.model.Role;
import iat.alumni.model.Student;
import iat.alumni.model.User;
import iat.alumni.model.UserRole;
import iat.alumni.model.UserSession;
import iat.alumni.service.ArticleService;
import iat.alumni.service.CommentService;
import iat.alumni.service.EventService;
import iat.alumni.service.RoleService;
import iat.alumni.service.StudentService;
import iat.alumni.service.UserRoleService;
import iat.alumni.service.UserService;
import iat.alumni.util.DummyMultipartFile;
import iat.alumni.util.FileUploadUtil;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping (value = "/admin")
@SessionAttributes(value = { "userSession" }, types = { UserSession.class })
public class AdminController {
	@Autowired
	UserService userService;
	
	@Autowired
	EventService eventService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ArticleService articleService;
	
	
	//For User List
	@GetMapping(value = "/user/list")
	public String userListPage(Model model, UserSession userSession) {
		List<User> userlist = userService.findAllUsers();
		model.addAttribute("userlist", userlist);
		return "user-list";
	}
	
	//For Create New User
	@GetMapping(value = "/user/create")
	public String showNewUserForm(Model model, UserSession userSession) {
		User user = new User();
		model.addAttribute("user", user);
		return "user-new";
	}
		
	//Also For Create New User post mapping
	@PostMapping(value = "/user/save")
	public String saveUser(Model model,@ModelAttribute @Validated User user, BindingResult result,
			@RequestParam("file") MultipartFile file)
				throws IOException {
			
			if (StringUtils.isEmpty(user.getUserName())) {
		        result.rejectValue("userName", "error.user", "User name is required");
		    }
		    if (StringUtils.isEmpty(user.getDateOfBirth())) {
		        result.rejectValue("dateOfBirth", "error.user", "Date of Birth is required");
		    }
		    if (StringUtils.isEmpty(user.getGender())) {
		        result.rejectValue("gender", "error.user", "Gender is required");
		    }
		    if (file.isEmpty()) {
		        result.rejectValue("file", "error.user", "Please select a file.");
		    }
		    
		    Student student = studentService.authenrticate(user.getEmail());
		    if (student == null) {
	            result.rejectValue("email", "error.user", "Please provide an IAT Email");
	        }
			
			if (result.hasErrors()) {
		        return "user-new";
		    }
				
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			user.setProfilePic(fileName);
			
			User savedUser = userService.createUser(user);
			
			Role alumniRole = roleService.findRoleByName("ALUMNI");
			
			UserRole userRole = new UserRole();
		    userRole.setUser(savedUser);
		    userRole.setRole(alumniRole);
		    userRoleService.saveUserRole(userRole);
			
			String uploadDir = "user-photos/" + savedUser.getUserId();
			FileUploadUtil.saveFile(uploadDir, fileName, file);
			
			return "redirect:/admin/user/list";
		}
		
		//Delete User
		@PostMapping(value = "/user/delete-user/{userId}")
		public String deleteUser(@PathVariable("userId") Integer userId) {
			userService.deleteUserById(userId);
			return "redirect:/admin/user/list";
		}
		
		//For Review Posted Events
		@GetMapping(value = "/event/list")
		public String reviewEvents(Model model) {
		    List<Event> eventList = eventService.getAllEvent();
		    model.addAttribute("eventList", eventList);
		    return "review-events";
		}
		
		//For Approve Events
		@PostMapping(value = "/event/approve/{eventId}")
		public String approveEvent(@PathVariable("eventId") Integer eventId) {
			String dummyFilename = "dummy.txt";
			String dummyContentType = "text/plain";
			String dummyContent = "Dummy file content";
			
			MultipartFile dummyFile = null;
			try {
			    dummyFile = DummyMultipartFile.create(dummyFilename, dummyContentType, dummyContent);
			} catch (IOException e) {
			    e.printStackTrace();
			}
			
		    Event event = eventService.getEventById(eventId);
		    event.setApproved(true);
		    event.setEventFile(dummyFile);
		    eventService.createEvent(event);
		    return "redirect:/admin/event/list";
		}
		
		//For Reject Events
		@PostMapping(value = "/event/reject/{eventId}")
	    public String rejectEvent(@RequestParam("eventId") Integer eventId) {
	        eventService.deleteEventById(eventId);
	        return "redirect:/admin/event/list";
	    }
		
		//For Event Detail
		@GetMapping(value = "/event/details/{eventId}")
		public String showEventDetail(@PathVariable("eventId") Integer eventId, Model model) {
		    Event event = eventService.getEventById(eventId);
		    model.addAttribute("event", event);
		    return "admin-event-detail";
		}
		
		//For Article Detail For Admin
		@GetMapping(value = "/article/detail/{articleId}")
		public ModelAndView showArticleDetailForm (@PathVariable(name = "articleId") Integer articleId, Model model, HttpSession session) {
			
			ModelAndView mav = new ModelAndView ("admin-article-details");
			
			Comment comment = new Comment();
			model.addAttribute("comment", comment);
			List<Comment> listComment=commentService.getCommentsByArticleId(articleId);
		    model.addAttribute("listComment",listComment); 
		    model.addAttribute("currentArticleId", articleId);
			List<Article> listArticle=articleService.getAllArticle();
		    model.addAttribute("listArticle",listArticle); 
			Article article = articleService.getArticleById(articleId);
			Double averageRating = article.getAverageRating();
			DecimalFormat df = new DecimalFormat("0.0");
		    String formattedRating = averageRating != null ? df.format(averageRating) : "N/A";
			model.addAttribute("article", article);
			model.addAttribute("average", formattedRating);
			return mav;
		}
}