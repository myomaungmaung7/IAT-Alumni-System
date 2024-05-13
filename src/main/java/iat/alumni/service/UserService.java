package iat.alumni.service;

import java.util.List;
import org.springframework.stereotype.Service;
import iat.alumni.model.User;

@Service
public interface UserService {

	List<User> findAllUsers();
	User createUser (User user);
	User getUserById(Integer userId);
	List<User> getAllUsers();
	List <User> getAllUsers(String keyword);
	User upUser(User users);
	Long count();
	User updateUse(User user);
	User updateUser(User user);
	void deleteUserById(Integer userId);
	User findByEmailPwd(String email, String password);
	
}
