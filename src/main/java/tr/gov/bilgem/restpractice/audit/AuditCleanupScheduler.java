package tr.gov.bilgem.restpractice.audit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.config.AuditCleanupProperties;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class AuditCleanupScheduler {

    private static final Log logger = LogFactory.getLog(AuditCleanupScheduler.class);

    @Autowired
    private AuditService auditService;

    @Autowired
    private AuditCleanupProperties auditCleanupProperties;

    /**
     * Scheduled task to clean up old audit records
     * Uses configurable CRON expression from properties
     */
    @Scheduled(cron = "#{@auditCleanupProperties.cleanJobSchedule}")
    public void cleanupOldAuditRecords() {
        try {
            logger.info("Starting scheduled audit cleanup task...");

            // Calculate cutoff date based on configured expire days
            Instant cutoffDate = Instant.now().minus(auditCleanupProperties.getExpireDays(), ChronoUnit.DAYS);
            long cutoffEpochSecond = cutoffDate.getEpochSecond();

            logger.info(String.format("Cleaning up audit records older than %d days (before: %s)",
                    auditCleanupProperties.getExpireDays(), cutoffDate));


            // Perform cleanup
            auditService.delete(cutoffEpochSecond);

            logger.info(String.format("Audit cleanup completed successfully. Deleted records older than %s", cutoffDate));

        } catch (Exception e) {
            logger.error("Error during scheduled audit cleanup: " + e.getMessage(), e);
        }
    }
}