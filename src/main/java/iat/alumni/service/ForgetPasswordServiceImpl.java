package iat.alumni.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import iat.alumni.model.ForgetPassword;
import iat.alumni.model.PasswordDto;
import iat.alumni.repository.ForgetPasswordRepository;
import lombok.AllArgsConstructor;
 
@Service
@AllArgsConstructor
public class ForgetPasswordServiceImpl implements ForgetPasswordService{

	@Autowired
	private ForgetPasswordRepository fprepository;
	
	@Override
	public void createForgetPassword(ForgetPassword forgetpassword) {
		fprepository.save(forgetpassword);
	}

	@Override
	public List<PasswordDto> getAllPassword() {
		List<ForgetPassword> passwords=fprepository.findAll();
		List<PasswordDto> passwordDtos=new ArrayList<>();
		for(ForgetPassword password : passwords) {
			PasswordDto passwordDto=new PasswordDto();
			passwordDto.setId(password.getFpid());
			passwordDto.setEmail(password.getEmail());
			passwordDto.setDate(password.getDate());
			passwordDtos.add(passwordDto);
		}
		return passwordDtos;
	}
}