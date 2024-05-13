package iat.alumni.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iat.alumni.model.Student;
import iat.alumni.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentRepository stuRepo;

	@Override
	public Student authenrticate(String iatEmail) {
		return stuRepo.findbyEmail(iatEmail);
	}
}
