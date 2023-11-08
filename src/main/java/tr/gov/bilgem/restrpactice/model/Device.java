package tr.gov.bilgem.restrpactice.model;

/**
 * Represents a managed device.
 *
 * @author Serdar Serpen
 * @date Nov 3, 2023
 * @since 1.0.0
 */
//@Entity
public class Device {

	private Long id;
	
	//Not blank
	//Unique
	//Max 50
	private String name;
	
	//Not blank
	//Unique
	//Max 10
	private String serialNo;
	
	//Not blank
	//IPv4
	//Unique
	private String ipAddress;
	
	//Not null
	private Location location;
	
	//Not null
	private Group group;
	
	//Nullable
	private User owner;
	
}
