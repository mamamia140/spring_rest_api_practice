package tr.gov.bilgem.restpractice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents device location.
 *
 * @author Serdar Serpen
 * @date Nov 3, 2023
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "locations")
public class Location extends BaseEntity{

	@Size(max = 50)
	@NotBlank
	@Column
	private String city;

	@NotBlank
	@Size(max = 50)
	@Column
	private String county;

	@Size(max = 50)
	@NotBlank
	@Column
	private String country;

	@Column
	private Double longitude;

	@Column
	private Double altitude;

}
