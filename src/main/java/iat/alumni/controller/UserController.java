package iat.alumni.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import iat.alumni.model.Article;
import iat.alumni.model.Event;
import iat.alumni.model.User;
import iat.alumni.model.UserSession;
import iat.alumni.repository.ArticleRepository;
import iat.alumni.repository.EventRepository;
import iat.alumni.repository.UserRepository;
import iat.alumni.service.ArticleService;
import iat.alumni.service.EventService;
import iat.alumni.service.UserService;
import iat.alumni.util.FileUploadUtil;
import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes(value = { "userSession" }, types = { UserSession.class })
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	EventService eventService;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ArticleRepository artRepo;
	
	@Autowired
	EventRepository eveRepo;
	
	//For Alumni Home Page
	@GetMapping(value = "/alumniHome")
	public String alumniHome (Model model, UserSession userSession) {
		List <Article> listArticle = articleService.getAllArticlesSortedByDateDesc();
		List <Event> listEvent = eventService.getApprovedEvents();
		long userCount = userService.count();
		long eventCount = eventService.count();
		long articleCount = articleService.count();
		model.addAttribute("articleCount", articleCount);
		model.addAttribute("eventCount", eventCount);
		model.addAttribute("userCount", userCount);
		model.addAttribute("listEvent", listEvent);
		model.addAttribute("listArticle", listArticle);
		return "alumni-home";
	}
	
	//For User Home Page
	@GetMapping({"/", "/userHome", "/home"})
	public String userHome (Model model) {
		List <Article> listArticle = articleService.getAllArticlesSortedByDateDesc();
		List <Event> listEvent = eventService.getApprovedEvents();
		long userCount = userService.count();
		long articleCount = articleService.count();
		long eventCount = eventService.count();
		model.addAttribute("userCount", userCount);
		model.addAttribute("articleCount", articleCount);
		model.addAttribute("eventCount", eventCount);
		model.addAttribute("listEvent", listEvent);
		model.addAttribute("listArticle", listArticle);
		return "user-home";
	}
	
	//For Search Result Display Page
	@GetMapping(value = "/search")
	public String searchPage (Model model, @Param("keyword") String keyword) {
		List <User> listUser = userRepo.findAll(keyword);
		List <Article> listArticle = artRepo.findAll(keyword);
		List <Event> listEvent = eveRepo.findAll(keyword);
		model.addAttribute("listEvent", listEvent);
		model.addAttribute("listArticle", listArticle);
		model.addAttribute("listUser", listUser);
		return "search";
	}
	
	//For Alumni Home 
//	@GetMapping("/homeAlumni")
//	public String alumniHome(Model model, UserSession userSession, @RequestParam(value = "eventId", required = false) Integer eventId) {
//		List <Article> listArticle = artService.getAllArticles();
//		model.addAttribute("listArticle", listArticle);
//		
//		List<Event> eventList = eveService.getApprovedEvents();
//	    model.addAttribute("eventList", eventList);
//		
//		if (eventId != null) {
//	        Event event = eveService.getEventById(eventId);
//	        model.addAttribute("event", event);
//	    }
//		return "alumni-home";
//	}

	
	@GetMapping(value = "/user/profile/{id}")
	public String userPage(@PathVariable("id") Integer userId,Model model, UserSession userSession) {
		
		User user= userRepo.findById(userId).orElse(null);
		if (user != null) {
            model.addAttribute("user", user);
            return "profile";
        } 
				
		return "profile";
	}
	
	@GetMapping("/editProfile/{id}")
	public ModelAndView showUserUpdateForm(@PathVariable("id")Integer userId,Model model) {
		ModelAndView mav=new ModelAndView("user-update");
		User user=userService.getUserById(userId);
		mav.addObject(user); 
		return mav;
	}
	
	@PostMapping("/updateProfile/{id}")
	public String updateUser(@PathVariable("id")Integer userId,@Validated User user,BindingResult result, Model model, 
			  @RequestParam ("file") MultipartFile file) throws IOException {
		if(result.hasErrors()) {
	  	user.setUserId(userId);
	  	}
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		User createUser=  userService.updateUse(user);
		String uploadDir = "user-photos/" + createUser.getUserId();
		System.out.println(uploadDir);
		FileUploadUtil.saveFile(uploadDir, fileName, file);
		return "redirect:/user/profile/{id}";	 
	}
	
	@GetMapping(value = "/profile/{id}")
	public String userListPage(@PathVariable("id") Integer userId,Model model, UserSession userSession) {
		
		User user= userRepo.findById(userId).orElse(null);
		if (user != null) {
            model.addAttribute("user", user);
            return "view-profile";
        } 	
		return "view-profile";
	}
	
	@GetMapping(value = "/articleHistory")
	public String getArticles (Model model,HttpSession session) {
		 UserSession userSession = (UserSession) session.getAttribute("userSession");
		    if (userSession == null || userSession.getUser() == null) {
		        return "redirect:/login";
		    }
		
	    User currentUser = userSession.getUser();
		List <Article> articles = articleService.findArticlesByUser(currentUser);
		
		model.addAttribute("articles", articles);
		model.addAttribute("currentUser", currentUser);
		return "article-history";
	}
}
