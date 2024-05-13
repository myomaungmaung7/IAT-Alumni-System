package iat.alumni.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import iat.alumni.model.Article;
import iat.alumni.model.Report;
import iat.alumni.model.UserSession;
import iat.alumni.service.ArticleService;
import iat.alumni.service.ReportService;
import jakarta.servlet.http.HttpSession;


@Controller
@SessionAttributes(value = {"userSession"}, types = {UserSession.class})
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ArticleService articleService;

	
	
	@GetMapping(value = "/reportedArticle/{articleId}")
	public String reportForm(@PathVariable Integer articleId,Model model) {
		Article article = articleService.getArticleById(articleId);
		model.addAttribute("article", article);
		Report report = new Report();
		model.addAttribute("report", report);
		return "reported-articles";
	}
	
	@PostMapping(value = "/articleReport/{articleId}")
	public String saveReport (@ModelAttribute("report") Report report, @PathVariable Integer articleId, Model model, HttpSession session) {
		
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		if (userSession == null || userSession.getUser() == null) {
			return "redirect:/login";
		}
		
		LocalDate date = LocalDate.now();
		
		report.setArticle(articleService.getArticleById(articleId));
		report.setDate(date);
		report.setUser(userSession.getUser());
		reportService.createReport(report);
		
		return "redirect:/alumniHome";
	}
	
}
