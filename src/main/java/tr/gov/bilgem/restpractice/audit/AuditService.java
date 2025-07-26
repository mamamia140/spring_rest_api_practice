package tr.gov.bilgem.restpractice.audit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tr.gov.bilgem.restpractice.model.Audit;
import tr.gov.bilgem.restpractice.service.AbstractService;

import java.time.Instant;

@Service
public class AuditService extends AbstractService<Audit, Long> {

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
}