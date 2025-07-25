package tr.gov.bilgem.restpractice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents application (API) user account.
 *
 * @author Serdar Serpen
 * @date Nov 3, 2023
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

	public User(String username, String email, String password, Role role) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public enum Role {
		ADMIN, OPERATOR, OBSERVER
	}
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;


	@NotBlank
	@Size(max=50)
	@Column(nullable = false, unique = true, length = 50)
	private String username;


	@NotBlank
	@Size(max=50)
	@Email
	@Column(nullable = false, unique = true, length = 50)
	private String email;


	@NotBlank
	@Size(min = 6, max = 50)
	@Column(nullable = false, length = 50)
	private String password;


	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Version
	private int version;

}
