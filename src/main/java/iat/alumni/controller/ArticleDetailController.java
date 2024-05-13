package iat.alumni.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import iat.alumni.dto.CommentRequest;
import iat.alumni.model.Article;
import iat.alumni.model.Comment;
import iat.alumni.model.Rating;
import iat.alumni.model.User;
import iat.alumni.model.UserSession;
import iat.alumni.service.ArticleService;
import iat.alumni.service.CommentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
 
@Controller
@RequestMapping(value="/alumni")
public class ArticleDetailController {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping(value = "/articleDetail/{articleId}")
	public ModelAndView showArticleDetailForm (@PathVariable(name = "articleId") Integer articleId, Model model, HttpSession session) {
		
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		User currentUser = userSession.getUser();
		
		ModelAndView mav = new ModelAndView ("article-details");
		
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
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("average", formattedRating);
		return mav;
	}

	
 
	 
	@PostMapping("/savedcomment/{articleId}")
	 public String saveComment(@PathVariable("articleId") Integer articleId, @RequestParam("commentText") String commentText,
			 					HttpSession session, @Valid @ModelAttribute("commentRequest") CommentRequest commentRequest, BindingResult result, RedirectAttributes redirectAttributes) {
		
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		User user = userSession.getUser();
		
		Article article = articleService.getArticleById(articleId);
		if (user == null || article == null ) {
			return "redirect:/login";
		}
		
	     if (result.hasErrors()) {
	    	 redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentRequest", result);
	         redirectAttributes.addFlashAttribute("commentRequest", commentRequest);
	    	 return "redirect:/alumni/articleDetail/{articleId}";
	     }
//	     SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//	     LocalDate today = LocalDate.now();
	     
//	     comment.setDate(today);
//	     comment.setUser(userSession.getUser());
//	     comment.setArticle(articleService.getArticleById(articleId));
//	     
//	     articleService.createComment(comment);
	     
	     commentService.saveCommentForArticle(commentText, user, article);
	     return "redirect:/alumni/articleDetail/" + articleId;
	 }
	 
	
	@GetMapping(value="/rating/{articleId}")
	public String articleRatingandReviewForm(@PathVariable(name = "articleId") Integer articleId, Model model) {
		
		Rating rating = new Rating();
		model.addAttribute("rating", rating);
		return "reviewandrating";
	}
	
	@PostMapping("/saveRating/{articleId}")
	public String saveRating( @ModelAttribute @Valid Rating rating,@PathVariable Integer articleId, RedirectAttributes redirectAttributes,BindingResult result, HttpSession session,Model model) {
		 
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		if (userSession == null || userSession.getUser() == null ) {
			return "redirect:/login";
		}
		
		if(result.hasErrors()) {
			 
		}
		rating.setUser(userSession.getUser());
		rating.setArticle(articleService.getArticleById(articleId));
		articleService.createRating(rating);
		return"redirect:/alumni/articleDetail/{articleId}";
		}	
}
