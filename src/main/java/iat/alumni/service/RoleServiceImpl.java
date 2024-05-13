package iat.alumni.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iat.alumni.model.Role;
import iat.alumni.repository.RoleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleRepository roleRepo;
	
	@Override
	public List<Role> getAllRoles() {
		return roleRepo.findAll();
	}
	@Override
	public Role findRoleByName(String name) {
		return roleRepo.findByName(name);
	}

}
