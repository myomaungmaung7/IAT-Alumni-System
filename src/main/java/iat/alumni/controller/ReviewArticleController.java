package iat.alumni.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import iat.alumni.model.Article;
import iat.alumni.service.ArticleService;

@Controller
@RequestMapping(value="/admin")
public class ReviewArticleController {

	@Autowired
	ArticleService artService;
	
	@GetMapping(value="/reviewarticle")
	public String viewReviewArticlePage(Model model) {
	 List<Article> listArticles=artService.getAllArticles();
	 
	 model.addAttribute("listArticles",listArticles);
		return "reviewarticle";
		
		}
	

	@GetMapping(value = "/reviewarticle/deleteArticle/{articleId}")
	public String deleteArticle (@PathVariable (name = "articleId") Integer articleId) {
		artService.deleteArticle(articleId);
		return "redirect:/admin/reviewarticle";
	}

}
