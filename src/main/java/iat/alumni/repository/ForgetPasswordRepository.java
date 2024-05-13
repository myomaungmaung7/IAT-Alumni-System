package iat.alumni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iat.alumni.model.ForgetPassword;

@Repository
public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword, Integer>{

}
