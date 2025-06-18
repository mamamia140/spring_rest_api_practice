package tr.gov.bilgem.restpractice.config.analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.diagnostics.FailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.stereotype.Component;

import java.net.BindException;

@Component
public class PortInUseFailureAnalyzer implements FailureAnalyzer {

    private static final Logger logger = LoggerFactory.getLogger(PortInUseFailureAnalyzer.class);

    @Override
    public FailureAnalysis analyze(Throwable failure) {
        Throwable cause = findCause(failure, BindException.class);
        if (cause != null && cause.getMessage() != null && cause.getMessage().contains("Address already in use")) {
            System.out.println("HTTP port 8080 is in use!");
            logger.error("Startup failure: Port is already in use. You may need to stop another service or change the port.");
            return new FailureAnalysis(
                    "Port appears to be already in use.",
                    "Please make sure the port is not occupied by another application or change the port in application.properties.",
                    failure
            );
        }
        return null;
    }

    private Throwable findCause(Throwable throwable, Class<? extends Throwable> causeClass) {
        while (throwable != null) {
            if (causeClass.isInstance(throwable)) {
                return throwable;
            }
            throwable = throwable.getCause();
        }
        return null;
    }
}
