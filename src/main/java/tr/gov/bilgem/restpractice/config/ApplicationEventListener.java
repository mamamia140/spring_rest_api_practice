package tr.gov.bilgem.restpractice.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventListener {

    @EventListener
    public void handleApplicationStarted(ApplicationStartedEvent event) {
        System.out.println("Rest-Practice application is started!");
        System.out.println("Profile:dev");
    }
}
