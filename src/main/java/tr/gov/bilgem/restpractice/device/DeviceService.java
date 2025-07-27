package tr.gov.bilgem.restpractice.device;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.audit.AuditService;
import tr.gov.bilgem.restpractice.model.Device;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.service.AbstractService;
import tr.gov.bilgem.restpractice.user.UserService;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
class DeviceService extends AbstractService<Device, Long> {

    @Autowired
    AuditService auditService;

    @Autowired
    UserService userService;

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

    public void bulkDelete(List<Long> ids) {
        Log logger = getServiceLoggerByEntity();
        ((DeviceRepository) repository).deleteAllById(ids);
        if(logger.isDebugEnabled()){
            logger.debug("Deleted " + ids.size() + " device(s)");
        }
    }


    @Override
    public Device create(Device device) {
        Device createdDevice = super.create(device);
        Optional<User> currentUser = userService.getCurrentUser();

        String deviceName = device.getName();
        auditService.logAudit(currentUser.get(), String.format("%s adlı cihaz sisteme eklendi",deviceName), createdDevice.getIpAddress(), Instant.now());

        return createdDevice;
    }

    @Override
    public void deleteById(Long id) {
        // Get device name before deletion for audit log
        Optional<Device> deviceOpt = findById(id);
        String deviceName = deviceOpt.get().getName();
        String ipAddress = deviceOpt.get().getIpAddress();
        Optional<User> currentUser = userService.getCurrentUser();

        super.deleteEntity(id);

        auditService.logAudit(currentUser.get(), String.format("%s adlı cihaz sistemden silindi",deviceName), ipAddress, Instant.now());
    }
}
