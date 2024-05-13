package iat.alumni.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {
	
	private Integer id;
	private String email;
	private LocalDate date;
}
