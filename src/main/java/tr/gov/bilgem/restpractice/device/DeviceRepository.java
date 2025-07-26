package tr.gov.bilgem.restpractice.device;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.gov.bilgem.restpractice.model.Device;
import tr.gov.bilgem.restpractice.repository.AbstractRepository;

import java.util.List;

@Repository
public interface DeviceRepository extends AbstractRepository<Device, Long> {
    @Override
    @Query("SELECT d FROM devices d WHERE " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(d.serialNo) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(d.ipAddress) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page<Device> search(@Param("q") String keyword, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE devices d SET d.name = :name WHERE d.id = :id")
    void updateNameById(@Param("id") Long id, @Param("name") String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM devices d WHERE d.id IN :ids")
    void deleteAllById(List<Long> ids);

}
