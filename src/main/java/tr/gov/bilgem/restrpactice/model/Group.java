package tr.gov.bilgem.restrpactice.model;

/**
 * Represents a device group.
 *
 * @author Serdar Serpen
 * @date Nov 3, 2023
 * @since 1.0.0
 */
//@Entity
public class Group {

	private Long id;

	//Not blank
	//Unique
	//Max 50
	private String name;
	
	
	//Max 200
	private String description;
}
