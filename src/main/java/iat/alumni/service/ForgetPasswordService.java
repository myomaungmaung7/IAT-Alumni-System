package iat.alumni.service;

import java.util.List;

import iat.alumni.model.ForgetPassword;
import iat.alumni.model.PasswordDto;

public interface ForgetPasswordService {

	void createForgetPassword (ForgetPassword forgetpassword);
	List<PasswordDto> getAllPassword();
}
