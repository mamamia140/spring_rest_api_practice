package tr.gov.bilgem.restrpactice.device;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Serdar Serpen
 * @date Nov 8, 2023
 * @since 1.0.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class DeviceControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	void testGetDevice() {
		//TODO
	}
	
	@Test
	void testAddDevice() {
		//TODO
	}
	
	@Test
	void testDeleteDevice() {
		//TODO
	}
}

