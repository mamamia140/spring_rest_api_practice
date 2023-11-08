package tr.gov.bilgem.restrpactice.controller;

import lombok.Value;

/**
 * @author Serdar Serpen
 * @date Nov 6, 2023
 * @since 1.0.0
 */
@Value
class BuildInfo {

	private String time; 			// dd.MM.yyyy HH:mm
	private String branch;			//master
	private String ccommitId; 		// ID of the commit
	private String version;			// application version (pom.xml)
}


