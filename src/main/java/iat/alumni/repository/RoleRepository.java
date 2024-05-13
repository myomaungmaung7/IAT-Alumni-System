package iat.alumni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iat.alumni.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	@Query("SELECT r FROM Role r WHERE r.name=:n")
	Role findByName(@Param("n")String n);
}
