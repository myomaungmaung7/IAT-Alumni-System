package iat.alumni.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iat.alumni.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("SELECT u FROM User u WHERE u.userName LIKE %?1%")
	List<User> findAll (String keyword);
	
	@Query("SELECT u FROM User u WHERE u.email=:em")
	User findByEmail(@Param("em")String em);

	@Query("SELECT u FROM User u WHERE u.userName=:username AND u.password=:pwd")
	User findUserByNamePwd(@Param("username")String username, @Param("pwd")String pwd);

	@Query("SELECT u FROM User u WHERE u.email=:email AND u.password=:pwd")
	User findUserByEmailPwd(@Param("email")String email, @Param("pwd")String pwd);
	
	User findByUserName(String userName);
}
