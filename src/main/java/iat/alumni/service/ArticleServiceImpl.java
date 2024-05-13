package iat.alumni.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import iat.alumni.model.Article;
import iat.alumni.model.Comment;
import iat.alumni.model.Rating;
import iat.alumni.model.ArticleDto;
import iat.alumni.model.User;
import iat.alumni.repository.ArticleRepository;
import iat.alumni.repository.CommentRepository;
import iat.alumni.repository.RatingRepository;
import iat.alumni.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CommentRepository ComRepo;
	  
	@Autowired
	private RatingRepository RatRepo;

	@Override
	public List<Article> getAllArticles() {
		return articleRepo.findAll();
	}

	@Override
	public Article createArticle(Article article) {
		return articleRepo.save(article);
	}

	@Override
	public void deleteArticle(Integer artId) {
		List <Comment> commentDelete = ComRepo.findByCommentByArticleId(artId);
		ComRepo.deleteAll( commentDelete );
		articleRepo.deleteById(artId);
		
	}

	@Override
	public Article getArticleById(Integer articleId) {
		Optional <Article> optionArticle = articleRepo.findById(articleId);
		return optionArticle.get();
	}

	@Override
	public List<ArticleDto> search(String keyword) {
		List <Article> articles = articleRepo.findAll(keyword);
		List <ArticleDto> articleDtos = new ArrayList <> ();
		for (Article article : articles) {
			Optional <User> user = userRepo.findById(article.getUser().getUserId());
			if (user.isPresent() && (keyword == null || user.get().getUserName().contains(keyword))) {
				ArticleDto articleDto = new ArticleDto();
				articleDto.setId(article.getArticleId());
				articleDto.setTitle(article.getTitle());
				articleDto.setDate(article.getDate());
				articleDto.setDescription(article.getDescription());
				articleDto.setUserName(user.get().getUserName());
				articleDto.setPhoto(user.get().getPhotosImagePath());
				articleDto.setArticleId(article.getArticleId());
				articleDtos.add(articleDto);
			}
		}
		return articleDtos;
	}

	@Override
	public Long count() {
		return articleRepo.count();
	}

	@Override
	public List<Article> getAllArticlesSortedByDateDesc() {
		return articleRepo.findAllArticlesSortedByDateDesc();
	}

	@Override
	public List<Article> findArticlesByUser(User user) {
		return articleRepo.findByUser(user);
	}

	@Override
	public List<Article> sea(String keyword) {
		// TODO Auto-generated method stub
		if (keyword != null) {
			return articleRepo.findAll(keyword);
		}
		return articleRepo.findAll();
	}

	@Override
	public Comment createComment(Comment comment) {
		return ComRepo.save(comment);
	}

	@Override
	public List<Comment> getAllComment() {
		return ComRepo.findAll();
	}
	
	@Override
	public Rating createRating(Rating rating) {
		return RatRepo.save(rating);
	}
 
	@Override
	public List<Article> getAllArticle() {
		return articleRepo.findAll();
	}
}