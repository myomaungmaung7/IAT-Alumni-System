package iat.alumni.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer eventId;
	
	@NotBlank(message = "Title is required")
	@Column(name = "title")
	private String title;
	
	@NotBlank(message = "Description is required")
	@Size(max = 10000, message = "Description must be less than or equal to 10000 characters")
	@Column(name = "description")
	private String description;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "time")
	private LocalTime time;
	
	@NotBlank(message = "Location is required")
	@Column(name = "location")
	private String location;
	
	@NotNull(message = "Date and Time is required")
	@Future(message = "Date and Time must be in the future")
	@Column(name = "date_time")
	private LocalDateTime dateTime;
	
	@Column(name = "approved")
	private boolean approved;
	
	@Column(name = "joined")
	private Boolean joined;
	
	@Column(name = "photo", nullable = true, length = 64)
	private String photo;
	
	@NotNull(message = "Please select a file.")
	@Transient
	private MultipartFile eventFile;
	
	@Transient
	public String getPhotosImagePath() {
		if (photo == null || eventId == null) return null;
		
		return "/event-photos/" + eventId + "/" + photo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

	@OneToMany(mappedBy = "event")
    private List<JoinEvent> joinedUsers;
	
	@ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(
        name = "join_event",
        joinColumns = { @JoinColumn(name = "event_id") },
        inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> participants = new HashSet<>();
	
	public boolean isJoined(User user) {
        return participants.contains(user);
    }
}