package tr.gov.bilgem.restpractice.controller;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Serdar Serpen
 * @date Nov 7, 2023
 * @since 1.0.0
 */
@Service
class BuildInfoService {

	BuildInfo get() throws IOException {
		/*
		 * TODO: Fetch build info content from build-info.properties, git.properties and
		 * pom.xml.
		 */
		// Load build-info.properties
		Properties buildInfoProps = PropertiesLoaderUtils.loadAllProperties("META-INF/build-info.properties");

		// Load git.properties
		Properties gitProps = PropertiesLoaderUtils.loadAllProperties("git.properties");

		return new BuildInfo(buildInfoProps.getProperty("build.time", "N/A"),
				gitProps.getProperty("git.branch", "N/A"),
				gitProps.getProperty("git.commit.id.abbrev", "N/A"),
				buildInfoProps.getProperty("build.version", "N/A"));
	}
}
