//package iat.alumni.controller;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import iat.alumni.model.ForgetPassword;
//import iat.alumni.model.PasswordDto;
//import iat.alumni.model.ReportDto;
//import iat.alumni.service.ForgetPasswordService;
// 
//import jakarta.validation.Valid;
//
//@Controller
//@RequestMapping(value="/user")
//public class ForgetPasswordController {
//@Autowired
//ForgetPasswordService fpService;
//
//	@GetMapping(value="/forgetpassword")
//	public String forgetPasswordForm(Model model) {
//		ForgetPassword forgetpassword = new ForgetPassword();
//		model.addAttribute("forgetpassword", forgetpassword);
//		return "forgetpassword";
//	}
//	
//	@PostMapping("/sent")
//	public String saveForget_Password(@ModelAttribute @Valid ForgetPassword forgetpassword,BindingResult result,Model model) {
//
//		if(result.hasErrors()) {
//			return "forgetpassword";
//		}
//		new SimpleDateFormat("MM/dd/yyyy");
//		LocalDate.now();
//		
//		forgetpassword.setDate(LocalDate.now());
//		 
//		
//		 
//		fpService.createForgetPassword(forgetpassword);
//		return"redirect:/user/forgetpassword";
//		
//		}
//	
//	
//	
//		
//	}
//
