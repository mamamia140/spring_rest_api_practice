package tr.gov.bilgem.restpractice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a managed device.
 *
 * @author Serdar Serpen
 * @date Nov 3, 2023
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "devices")
public class Device extends BaseEntity {

	@NotBlank
	@Size(max = 50)
	@Column(unique = true, length = 50)
	private String name;

	@NotBlank
	@Size(max = 10)
	@Column(unique = true)
	private String serialNo;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$")
	private String ipAddress;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id",nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Location location;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Group group;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User owner;


	/**
	 * Device responds to PING (ICMP echo) requests or not.
	 */
	@Column
	private Boolean accessible;

}