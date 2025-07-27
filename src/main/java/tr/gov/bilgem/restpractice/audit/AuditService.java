package tr.gov.bilgem.restpractice.audit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tr.gov.bilgem.restpractice.model.Audit;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.service.AbstractService;

import java.time.Instant;

@Service
public class AuditService extends AbstractService<Audit, Long> {

    private static final Log logger = LogFactory.getLog(AuditService.class);

    protected AuditService(AuditRepository auditRepository) {
        super(auditRepository);
    }

    public Page<Audit> search(String query, int page, int size, String sortBy, String direction, Instant start, Instant end) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ((AuditRepository) repository).search(query, pageable, start, end);
    }

    @Override
    public Log getServiceLoggerByEntity() {
        return logger;
    }

    public void delete(long time) {
        Instant instant = Instant.ofEpochSecond(time);
        ((AuditRepository) repository).deleteByDate(instant);
        if(logger.isDebugEnabled()){
            logger.debug("Deleted audit records before the date: " + instant);
        }
    }

    public void logAudit(User user, String action, String ipAddress, Instant timeStamp) {
        try {
            Audit audit = new Audit(user, action, ipAddress, timeStamp);
            repository.save(audit);

            if (logger.isDebugEnabled()) {
                logger.debug("Audit log created: " + action);
            }
        } catch (Exception e) {
            logger.error("Failed to create audit log: " + e.getMessage(), e);
        }
    }
}