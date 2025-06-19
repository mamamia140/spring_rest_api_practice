package tr.gov.bilgem.restpractice.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.gov.bilgem.restpractice.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

}
