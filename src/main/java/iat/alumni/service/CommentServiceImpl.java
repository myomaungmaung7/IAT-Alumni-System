package iat.alumni.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iat.alumni.model.Article;
import iat.alumni.model.Comment;
import iat.alumni.model.Event;
import iat.alumni.model.User;
import iat.alumni.repository.CommentRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Override
	public Optional<Comment> findById(Integer commentId) {
		return commentRepo.findById(commentId);
	}

	@Override
	public void saveComment(String text, User user, Event event) {
		Comment comment = new Comment();
        comment.setComment(text);
        comment.setUser(user);
        comment.setEvent(event);
        comment.setDateTime(LocalDateTime.now());

        commentRepo.save(comment);
	}

	@Override
	public List<Comment> getCommentsByEventId(Integer eventId) {
		return commentRepo.findByEventId(eventId);
	}

	@Override
	public void saveCommentForArticle(String text, User user, Article article) {
		Comment comment = new Comment();
        comment.setComment(text);
        comment.setUser(user);
        comment.setArticle(article);
        comment.setDateTime(LocalDateTime.now());
        
        commentRepo.save(comment);
	}

	@Override
	public List<Comment> getCommentsByArticleId(Integer articleId) {
		return commentRepo.findByArticleId(articleId);
	}
	
}