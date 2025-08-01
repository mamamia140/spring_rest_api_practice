package tr.gov.bilgem.restpractice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.device.DeviceService;
import tr.gov.bilgem.restpractice.model.Device;

import java.util.List;

@Component
public class ApplicationEventListener {

    @Value("${spring.profiles.active:}")
    private String activeProfiles;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DeviceService deviceService;
    @EventListener
    public void onApplicationStartedEvent(ApplicationStartedEvent event) {
        System.out.println("Rest-Practice application is started...");

    }

    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {
        System.out.println("Profile:" + activeProfiles);
        System.out.println("Listening on HTTP port: " + serverPort);

        initializeDeviceWatchers();
    }

    public void initializeDeviceWatchers() {
        try {
            //logger.info("Initializing device watcher tasks...");

            List<Device> allDevices = deviceService.findAll();

            for (Device device : allDevices) {
                deviceService.scheduleDeviceWatcherTask(device);
            }

            //logger.info("Successfully initialized " + allDevices.size() + " device watcher tasks");

        } catch (Exception e) {
            //logger.error("Failed to initialize device watcher tasks", e);
        }
    }
}
