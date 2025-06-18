package tr.gov.bilgem.restpractice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Provides the "/api/build-info" end-point.
 *
 * @author Serdar Serpen
 * @date Nov 3, 2023
 * @since 1.0.0
 */
@RestController
@RequestMapping("${api.root}/build-info")
@RequiredArgsConstructor(access=AccessLevel.PACKAGE)
@Slf4j
class BuildInfoController {

	private final BuildInfoService buildInfoService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	BuildInfo get() {
		LOGGER.debug("Getting build-info...");
		return buildInfoService.get();
	}

}
