package iat.alumni.service;

import java.util.List;
import java.util.Optional;

import iat.alumni.model.Article;
import iat.alumni.model.Comment;
import iat.alumni.model.Event;
import iat.alumni.model.User;

public interface CommentService {
	Optional <Comment> findById(Integer commentId);
	void saveComment(String text, User user, Event event);
	void saveCommentForArticle(String text, User user, Article article);
	List<Comment> getCommentsByEventId(Integer eventId);
	List<Comment> getCommentsByArticleId(Integer articleId);
}