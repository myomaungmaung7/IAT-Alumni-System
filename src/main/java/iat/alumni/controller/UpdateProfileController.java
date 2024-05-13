package iat.alumni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import iat.alumni.model.User;
import iat.alumni.service.UserService;
 

@Controller
@RequestMapping (value = "/admin")
public class UpdateProfileController {
	
	@Autowired
	  UserService userService;

	@GetMapping("/editUser/{Id}")
	  public ModelAndView showUserUpdateForm(@PathVariable(name="Id")int Id,Model model) {
	  ModelAndView mav=new ModelAndView("updateprofile");
	  User user=userService.getUserById(Id);
	  mav.addObject(user);
	  	 
	  return mav;
	  }
	
	@PostMapping("/updateUser/{Id}")
	  public String updateUser(@PathVariable(name="Id")int Id,@Validated User users,BindingResult result, Model model) {
	  if(result.hasErrors()) {
	  	users.setUserId(Id);
	  	 
	  }
	  userService.updateUser(users);
	  return "redirect:/admin/user/list";	 
	  }
}
