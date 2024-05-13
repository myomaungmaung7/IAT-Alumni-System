package iat.alumni.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

 
import iat.alumni.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	@Query("SELECT c FROM Comment c WHERE c.event.eventId = :eventId")
	List<Comment> findByEventId(@Param("eventId") Integer eventId);
	
	@Query("SELECT c FROM Comment c WHERE c.article.articleId = :articleId")
	List<Comment> findByArticleId(@Param("articleId") Integer articleId);
	
	@Query("SELECT c FROM Comment c WHERE c.article.articleId = :articleId")
	List <Comment> findByCommentByArticleId(Integer articleId);
}