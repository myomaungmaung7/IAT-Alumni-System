package iat.alumni.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iat.alumni.model.User;
import iat.alumni.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{
	List<UserRole> findByUser(User user);
}
