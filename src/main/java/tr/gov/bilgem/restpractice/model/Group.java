package tr.gov.bilgem.restpractice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents a device group.
 *
 * @author Serdar Serpen
 * @date Nov 3, 2023
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "groups")
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;


	@NotBlank
	@Size( max = 50)
	@Column(unique = true)
	private String name;


	@Size(max = 200)
	@Column
	private String description;

	@Version
	private int version;
}
