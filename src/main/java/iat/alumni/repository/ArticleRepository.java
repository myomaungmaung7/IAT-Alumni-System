package iat.alumni.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import iat.alumni.model.Article;
import iat.alumni.model.User;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
	@Query("SELECT a FROM Article a WHERE a.user.userName LIKE %?1%")
	List<Article> findAll (String keyword);
	
	long count();
	
	@Query("SELECT a FROM Article a ORDER BY a.date DESC")
	List <Article> findAllArticlesSortedByDateDesc();
	
	List <Article> findByUser(User user);
}
