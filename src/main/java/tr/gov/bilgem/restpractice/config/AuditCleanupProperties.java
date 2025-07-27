package tr.gov.bilgem.restpractice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "audits")
public class AuditCleanupProperties {

    /**
     * CRON expression for audit cleanup schedule
     * Default: "0 0 2 * * ?" (Every day at 2:00 AM)
     */
    private String cleanJobSchedule = "0 0 2 * * ?";

    /**
     * Number of days to keep audit records
     * Default: 365 days
     */
    private int expireDays = 365;
}