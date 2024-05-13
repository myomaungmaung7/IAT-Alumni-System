package iat.alumni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import iat.alumni.model.User;
import iat.alumni.service.UserService;

@Controller
public class RoleController {
	
	@Autowired
	private UserService userService;

	@GetMapping(value = "/editEmail/{id}")
	public ModelAndView showUserUpdateForm(@PathVariable("id") Integer userId, Model model) {
		ModelAndView mav = new ModelAndView("request");
		User user = userService.getUserById(userId);
		mav.addObject(user);
		return mav;
	}

	@PostMapping(value = "/updateEmail/{id}")
	public String updateUser (@PathVariable("id")Integer userId, @Validated User users, BindingResult result,
			Model model) {
		if(result.hasErrors()) {
			users.setUserId(userId);
		}
		userService.upUser(users);
		return "redirect:/editEmail/{id}";
	}

}
