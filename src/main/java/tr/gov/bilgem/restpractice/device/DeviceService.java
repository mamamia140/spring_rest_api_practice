package tr.gov.bilgem.restpractice.device;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.audit.AuditService;
import tr.gov.bilgem.restpractice.device.watcher.DeviceWatcherTask;
import tr.gov.bilgem.restpractice.device.watcher.DeviceWatcherTaskFactory;
import tr.gov.bilgem.restpractice.model.Device;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.service.AbstractService;
import tr.gov.bilgem.restpractice.user.UserService;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
public class DeviceService extends AbstractService<Device, Long> {

    private static final Log logger = LogFactory.getLog(DeviceService.class);

    @Autowired
    AuditService auditService;

    @Autowired
    UserService userService;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private DeviceWatcherTaskFactory deviceWatcherTaskFactory;

    @Value("${device.watcher.period:60000}")
    private long watcherPeriodMillis;

    // Map to store scheduled tasks for each device
    private final ConcurrentHashMap<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

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

    /**
     * Schedule a device watcher task for a specific device
     * @param device The device to watch
     */
    public void scheduleDeviceWatcherTask(Device device) {
        if (device == null || device.getId() == null) {
            logger.warn("Cannot schedule task for null device or device with null ID");
            return;
        }

        Long deviceId = device.getId();

        // Cancel existing task if present
        cancelDeviceWatcherTask(deviceId);

        try {
            DeviceWatcherTask task = deviceWatcherTaskFactory.createTask(device);
            ScheduledFuture<?> scheduledFuture = taskScheduler.scheduleAtFixedRate(
                    task,
                    Instant.now(),
                    Duration.ofMillis(watcherPeriodMillis)
            );

            scheduledTasks.put(deviceId, scheduledFuture);

            if (logger.isInfoEnabled()) {
                logger.info("Scheduled device watcher task for device: " + device.getName() +
                        " (ID: " + deviceId + ") with period: " + watcherPeriodMillis + "ms");
            }
        } catch (Exception e) {
            logger.error("Failed to schedule device watcher task for device ID: " + deviceId, e);
        }
    }

    /**
     * Cancel the scheduled task for a specific device
     * @param deviceId The ID of the device
     */
    public void cancelDeviceWatcherTask(Long deviceId) {
        if (deviceId == null) {
            return;
        }

        ScheduledFuture<?> scheduledFuture = scheduledTasks.remove(deviceId);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            if (logger.isInfoEnabled()) {
                logger.info("Cancelled device watcher task for device ID: " + deviceId);
            }
        }
    }
}
