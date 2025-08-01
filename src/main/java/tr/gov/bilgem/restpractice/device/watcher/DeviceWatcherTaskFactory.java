package tr.gov.bilgem.restpractice.device.watcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.device.DeviceService;
import tr.gov.bilgem.restpractice.model.Device;

/**
 * Factory class to create DeviceWatcherTask instances
 */
@Component
public class DeviceWatcherTaskFactory {

    @Autowired
    @Lazy
    private DeviceService deviceService;

    @Value("${device.watcher.ping.timeout:5000}")
    private int pingTimeoutMs;

    /**
     * Create a new DeviceWatcherTask for the given device
     * @param device The device to monitor
     * @return A new DeviceWatcherTask instance
     */
    public DeviceWatcherTask createTask(Device device) {
        return new DeviceWatcherTask(device, deviceService, pingTimeoutMs);
    }
}