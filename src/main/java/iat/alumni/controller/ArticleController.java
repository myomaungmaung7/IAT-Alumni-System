package iat.alumni.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import iat.alumni.model.Article;
import iat.alumni.model.User;
import iat.alumni.model.UserSession;
import iat.alumni.service.ArticleService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping (value = "/admin")
@SessionAttributes(value = { "userSession" }, types = { UserSession.class })
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	//For Manage Article Page
	@GetMapping(value = "/manageArticle")
	public String viewArticles (Model model, UserSession userSession) {
		List <Article> listArticles = articleService.getAllArticles();
		model.addAttribute("listArticles", listArticles);
		return "manage-article";
	}

	@GetMapping(value = "/manageArticle/deleteArticle/{articleId}")
	public String deleteArticle (@PathVariable (name = "articleId") Integer articleId) {
		articleService.deleteArticle(articleId);
		return "redirect:/admin/manageArticle";
	}

}
