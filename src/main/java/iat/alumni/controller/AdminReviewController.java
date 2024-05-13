package iat.alumni.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import iat.alumni.model.PasswordDto;
import iat.alumni.model.ReportDto;
import iat.alumni.repository.StudentRepository;
import iat.alumni.service.ForgetPasswordService;
import iat.alumni.service.ReportService;

@Controller
@RequestMapping(value = "/admin")
public class AdminReviewController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private StudentRepository stuRepo;
	
	@Autowired
	ForgetPasswordService fpService;

	@GetMapping("/reviewReport")
	public String reportReview(Model model) {
		List<ReportDto> reportDtos= reportService.getAllReport();
		
		model.addAttribute("reportDtos", reportDtos);
		return "review-report";
	}
	@GetMapping("/reviewReport/deleteReport/{id}")
	public String deleteCourse(@PathVariable(name="id")Integer id) {
		reportService.deletebyId(id);
		
		return "redirect:/admin/reviewReport";
	}
	@GetMapping("/reviewPassword")
	public String passReview(Model model) {
		List<PasswordDto> passwordDtos= fpService.getAllPassword();
		model.addAttribute("passwordDtos", passwordDtos);
		return "review-password";
	}
	
}
