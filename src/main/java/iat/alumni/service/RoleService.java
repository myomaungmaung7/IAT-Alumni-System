package iat.alumni.service;

import java.util.List;

import iat.alumni.model.Role;

public interface RoleService {
	List<Role> getAllRoles();
	Role findRoleByName(String name);
}
