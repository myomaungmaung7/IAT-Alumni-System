package iat.alumni.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDto {
	private Integer id;
	private String userName;
	private String title;
	private LocalDateTime dateTime;
	private String photo;
	private String description;
	private LocalDate date;
}
