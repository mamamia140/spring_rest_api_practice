package tr.gov.bilgem.restrpactice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides the "/api/hello" end-point.
 *
 * @author Serdar Serpen
 * @date Nov 3, 2023s
 * @since 1.0.0
 */
@RestController
@RequestMapping("${api.root}/hello")
@Slf4j
class HelloController {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	String hello() {
		LOGGER.debug("Hello request is received...");
		return "Hello...";
	}

}
