package iat.alumni.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private Integer studentId;
	
	@Column(name = "name")
	private String name;

	@Column(name = "iat_email")
	private String iatEmail;
	
	@Column(name = "course_name")
	private String courseName;
	
	@Column(name = "education_background")
	private String eduBackground;
	
	@Column(name = "job_title")
	private String jobTitle;
	
	@Column(name = "dob")
	private LocalDate dob;
 
}
