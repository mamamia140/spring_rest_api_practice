package tr.gov.bilgem.restrpactice.controller;

import org.springframework.stereotype.Service;

/**
 * @author Serdar Serpen
 * @date Nov 7, 2023
 * @since 1.0.0
 */
@Service
class BuildInfoService {

	BuildInfo get() {
		/*
		 * TODO: Fetch build info content from build-info.properties, git.properties and
		 * pom.xml.
		 */
		return new BuildInfo("07.11.2023 14:37", "master", "87494a7825295f36200a02e", "1.0.0");
	}
}
