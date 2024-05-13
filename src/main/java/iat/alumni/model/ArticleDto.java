package iat.alumni.model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDto {
	private Integer id;
	private String userName;
	private String title;
	private LocalDate date;
	private String description;
	private String photo;
	private Integer articleId;
	
}