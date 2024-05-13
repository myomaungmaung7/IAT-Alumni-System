package iat.alumni.service;

import java.util.List;
import iat.alumni.model.Article;
import iat.alumni.model.Comment;
import iat.alumni.model.Rating;
import iat.alumni.model.User;
import iat.alumni.model.ArticleDto;

public interface ArticleService {

	List<Article> getAllArticles();
	List<Article>sea(String keyword);
	Article createArticle(Article article);
	void deleteArticle(Integer artId);
	Article getArticleById(Integer articleId);
	List <ArticleDto> search(String keyword);
	Long count();
	List<Article> getAllArticlesSortedByDateDesc();
	List<Article> findArticlesByUser(User user);
	Comment createComment(Comment comment);
	List<Comment> getAllComment();
	List<Article> getAllArticle();
	Rating createRating(Rating rating);
}
