package iat.alumni.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import iat.alumni.model.ForgetPassword;
import iat.alumni.model.Student;
import iat.alumni.model.User;
import iat.alumni.model.UserSession;
import iat.alumni.repository.StudentRepository;
import iat.alumni.service.ForgetPasswordService;
import iat.alumni.service.UserService;
import iat.alumni.util.FileUploadUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ForgetPasswordService fpService;
	
	@Autowired
	StudentRepository stuRepo;
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/login/authenticate")
	public String authenticate(@ModelAttribute("user") User user, BindingResult bindingResult, Model model, HttpSession session) {
		
	        String retStr = null;
	        if(bindingResult.hasErrors()) {
	            return "login";
	        }
		
		User u = userService.findByEmailPwd(user.getEmail(), user.getPassword());

		if(u == null) {
			model.addAttribute("loginMessage", "Incorrect email/password");
			return "login";
		}
		
		UserSession uSession = new UserSession();
		uSession.setUser(u);
		session.setAttribute("userSession", uSession);
		
		List<String> roles = u.getRoles();
		
		if (roles.contains("ADMIN")) {
			session.setAttribute("role", "ADMIN");
			retStr = "redirect:/admin/user/list";
		}
		
		if (roles.contains("ALUMNI")) {
			session.setAttribute("role", "ALUMNI");
			retStr = "redirect:/alumniHome";
		}
		System.out.println("authenticated user url===" + retStr);

		return retStr;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@GetMapping(value="/login/forgetpassword")
	public String forgetPasswordForm(Model model) {
		System.out.println("I forgot my password" );
		ForgetPassword forgetpassword = new ForgetPassword();
		model.addAttribute("forgetpassword", forgetpassword);
		return "forgetpassword";
	}
	
	@PostMapping("/emailsent")
	public String saveForget_Password(@ModelAttribute @Valid ForgetPassword forgetpassword,
			BindingResult result,Model model) {

		if(result.hasErrors()) {
			return "forgetpassword";
		}
		
		new SimpleDateFormat("MM/dd/yyyy");
		LocalDate.now();
		
		forgetpassword.setDate(LocalDate.now());
		System.out.println("Email ==== " + forgetpassword.getEmail());
		 
		fpService.createForgetPassword(forgetpassword);
		return"redirect:/login";
		
	}
	
	@GetMapping(value = "/request")
	public String showNewUserForm (Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "new-user";
	}
	
	@PostMapping(value = "/save/request")
	public String saveUser (Model model, @ModelAttribute @Validated User user, BindingResult result,
			@RequestParam("file") MultipartFile file) throws Exception {
		
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
		
		Student student=stuRepo.findbyEmail(user.getEmail());
		 if(student == null) {
			 result.rejectValue("email", "error.user","Please provide an IAT Email");
		 }
		if(result.hasErrors()) {
			return "new-user";
		}
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		user.setProfilePic(fileName);
		User savedUser = userService.createUser(user);
		String uploadDir = "user-photos/" + savedUser.getUserId();
		FileUploadUtil.saveFile(uploadDir, fileName, file);
		return "redirect:/login";
	}

}