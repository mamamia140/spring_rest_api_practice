package tr.gov.bilgem.restpractice.model;

/**
 * Represents device location.
 *
 * @author Serdar Serpen
 * @date Nov 3, 2023
 * @since 1.0.0
 */
//@Entity
public class Location {

	private Long id;
	
	//Not blank
	//Max 50
	private String city;
	
	//Not blank
	//Max 50
	private String county;
	
	//Not blank
	//Max 50
	private String country;
	
	private Double longitude;
	
	private Double altitude;
}
