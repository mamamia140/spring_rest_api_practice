package tr.gov.bilgem.restpractice.device;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.gov.bilgem.restpractice.model.Device;
import tr.gov.bilgem.restpractice.repository.AbstractRepository;

@Repository
public interface DeviceRepository extends AbstractRepository<Device, Long> {
    @Override
    @Query("SELECT d FROM devices d WHERE " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(d.serialNo) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(d.ipAddress) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page<Device> search(@Param("q") String keyword, Pageable pageable);

}
