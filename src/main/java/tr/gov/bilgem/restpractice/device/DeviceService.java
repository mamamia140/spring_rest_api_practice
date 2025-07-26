package tr.gov.bilgem.restpractice.device;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.model.Device;
import tr.gov.bilgem.restpractice.service.AbstractService;

import java.io.IOException;
import java.util.List;

@Component
class DeviceService extends AbstractService<Device, Long> {

    private static final Log logger = LogFactory.getLog(DeviceService.class);

    protected DeviceService(DeviceRepository deviceRepository) {
        super(deviceRepository);
    }

    @Override
    public Log getServiceLoggerByEntity() {
        return logger;
    }

    public void updateNameById(Long id, String name) {
        Log logger = getServiceLoggerByEntity();
        ((DeviceRepository) repository).updateNameById(id, name);
        if(logger.isDebugEnabled()){
            logger.debug("Updated name of the device with id: " + id + ". New name: " + name);
        }
    }

    public String ping(String address, String ttl, String timeout, String size, String count) throws IOException {
        List<String> command = List.of(
                "ping",
                "-i", ttl,
                "-w", timeout,
                "-l", size,
                "-n", count,
                address
        );

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        String output = new String(process.getInputStream().readAllBytes());
        return output;
    }
}
