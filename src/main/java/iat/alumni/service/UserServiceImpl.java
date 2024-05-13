package iat.alumni.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import iat.alumni.model.Role;
import iat.alumni.model.Student;
import iat.alumni.model.User;
import iat.alumni.repository.RoleRepository;
import iat.alumni.repository.StudentRepository;
import iat.alumni.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private StudentRepository stuRepo;
	
	@Override
	public List<User> findAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User createUser(User user) {
		Role role= roleRepo.findByName("ALUMNI");
		Student stu= stuRepo.findbyEmail(user.getEmail());
		if(stu != null) 
		user.setCourseName(stu.getCourseName());
		user.setEducation(stu.getEduBackground());
		user.setJobTitle(stu.getJobTitle());
	    Set<Role> roles = new HashSet<>();
	    roles.add(role);
	    user.setRoles(roles);
		return userRepo.save(user);
	}

	@Override
	public User findByEmailPwd(String email, String password) {
		return userRepo.findUserByEmailPwd(email, password);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public List<User> getAllUsers(String keyword) {
		if (keyword != null) {
			return userRepo.findAll(keyword);
		}
		return userRepo.findAll();
	}

	@Override
	public Long count() {
		return userRepo.count();
	}

	@Override
	public User upUser(User users) {
		User existingUser = userRepo.findById(users.getUserId()).get();
		Role role = roleRepo.findByName("ALUMNI");
		Student stu = stuRepo.findbyEmail(users.getEmail());
		if(stu != null) {
			existingUser.setEmail(users.getEmail());
			existingUser.setCourseName(users.getCourseName());
			existingUser.setDegree(stu.getEduBackground());
			existingUser.setJobTitle(stu.getJobTitle());
			
			Set <Role> roles = new HashSet <> ();
			roles.add(role);
			existingUser.setRoles(roles);
		}
		User updateUser = userRepo.save(existingUser);
		return updateUser;
	}

	@Override
	public User getUserById(Integer userId) {
		Optional <User> optionUser = userRepo.findById(userId);
		return optionUser.get();
	}
	
	@Override
	public User updateUser(User users) {
		User existingUser=userRepo.findById(users.getUserId()).get();

		existingUser.setUserName(users.getUserName());
		existingUser.setEmail(users.getEmail());
		existingUser.setDateOfBirth(users.getDateOfBirth());
		existingUser.setCourseName(users.getCourseName());
		existingUser.setEducation(users.getEducation());
		existingUser.setJobTitle(users.getJobTitle());
		existingUser.setBio(users.getBio());
		existingUser.setGender(users.getGender());
		User updateUser = userRepo.save(existingUser);
		return updateUser;
		}

	@Override
	public User updateUse(User user) {
		User existingUser=userRepo.findById(user.getUserId()).get();
		 System.out.println("userID ==== " + existingUser.getUserId());
			
			existingUser.setUserName(user.getUserName());
			String fileName = StringUtils.cleanPath(user.getFile().getOriginalFilename());
			existingUser.setProfilePic(fileName);
			existingUser.setEmail(user.getEmail());
			existingUser.setDateOfBirth(user.getDateOfBirth());
			existingUser.setCourseName(user.getCourseName());
			existingUser.setEducation(user.getEducation());
			existingUser.setJobTitle(user.getJobTitle());
			existingUser.setDegree(user.getDegree());
			existingUser.setMajor(user.getMajor());
			existingUser.setBio(user.getBio());
			User updateUser = userRepo.save(existingUser);
			return updateUser;
	}
	
	@Override
	public void deleteUserById(Integer userId) {
		userRepo.deleteById(userId);
	}

}