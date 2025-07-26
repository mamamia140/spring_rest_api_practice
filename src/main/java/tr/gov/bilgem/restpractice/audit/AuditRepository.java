package tr.gov.bilgem.restpractice.audit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tr.gov.bilgem.restpractice.model.Audit;
import tr.gov.bilgem.restpractice.repository.AbstractRepository;

import java.time.Instant;

public interface AuditRepository extends AbstractRepository<Audit, Long> {
    @Query("SELECT a FROM audits a WHERE " +
            "LOWER(a.action) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(a.clientIpAddress) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page<Audit> search(@Param("q") String keyword, Pageable pageable);

    @Query("""
    SELECT a FROM audits a 
        WHERE (LOWER(a.action) LIKE LOWER(CONCAT('%', :q, '%')) OR 
        LOWER(a.clientIpAddress) LIKE LOWER(CONCAT('%', :q, '%'))) AND 
        a.timestamp >= :start AND a.timestamp <= :end
    """)
    Page<Audit> search(@Param("q") String keyword, Pageable pageable, @Param("start") Instant start, @Param("end") Instant end);
}
