package iat.alumni.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import iat.alumni.model.Article;
import iat.alumni.model.User;
import iat.alumni.model.UserSession;
import iat.alumni.service.ArticleService;
import iat.alumni.util.FileUploadUtil;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/alumni/article")
@SessionAttributes(value = {"userSession"}, types = {UserSession.class})
public class AlumniController {

	@Autowired
	private ArticleService articleService;
  
	//For Alumni Article Posting
	@GetMapping(value = "/posting")
	public String articlePostingForm(Model model, HttpSession session) {
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		User currentUser = userSession.getUser();
		Article articles = new Article();
		model.addAttribute("articles", articles);
		model.addAttribute("currentUser", currentUser);
		return "article-posting";
	}
	
	@PostMapping(value = "/savedPost")
	public String saveArticle(@ModelAttribute("articles") @Validated Article articles, BindingResult result, Model model,
			@RequestParam("articleFile") MultipartFile articleFile, HttpSession session) throws IOException {
		
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		if (userSession == null || userSession.getUser() == null) {
			return "redirect:/login";
		}
		User currentUser = userSession.getUser();
		
		if(result.hasErrors()) {
			return "article-posting";
		}
		
		String fileName = StringUtils.cleanPath(articleFile.getOriginalFilename());
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		LocalDate today = LocalDate.now();
		articles.setDate(today);
		articles.setPhoto(fileName);
		articles.setUser(currentUser);
		Article savedArticle = articleService.createArticle(articles);
		String uploadDir = "article-photos/" + savedArticle.getArticleId();
		FileUploadUtil.saveFile(uploadDir, fileName, articleFile);
		return "redirect:/alumniHome";
	}
}
	
	
	
	