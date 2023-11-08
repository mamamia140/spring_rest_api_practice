package tr.gov.bilgem.restrpactice.model;

import java.time.Instant;

/**
 * Represents a audit log.
 *
 * @author Serdar Serpen
 * @date Nov 3, 2023
 * @since 1.0.0
 */
//@Entity
public class Audit {
	
	private Long id;
	
	//Not null
	private User user;
	
	//Not blank
	//Max 50
	private String action;	//action description
	
	//Not null
	//IPPv4
	private String clientIpAddress;
	
	//Not null
	private Instant timestamp;
	
}
