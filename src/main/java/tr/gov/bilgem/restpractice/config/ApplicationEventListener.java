package tr.gov.bilgem.restpractice.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventListener {

    @Value("${spring.profiles.active:}")
    private String activeProfiles;

    @Value("${server.port}")
    private String serverPort;
    @EventListener
    public void onApplicationStartedEvent(ApplicationStartedEvent event) {
        System.out.println("Rest-Practice application is started...");

    }

    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {
        System.out.println("Profile:" + activeProfiles);
        System.out.println("Listening on HTTP port: " + serverPort);
    }
}
