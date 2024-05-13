package iat.alumni.service;

import org.springframework.stereotype.Service;

import iat.alumni.model.UserRole;
import iat.alumni.repository.UserRoleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
	
	private UserRoleRepository userRoleRepository;

	@Override
	public void saveUserRole(UserRole userRole) {
		userRoleRepository.save(userRole);
	}
}
