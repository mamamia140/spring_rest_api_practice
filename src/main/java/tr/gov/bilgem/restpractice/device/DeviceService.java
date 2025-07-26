package tr.gov.bilgem.restpractice.device;

import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.model.Device;
import tr.gov.bilgem.restpractice.service.AbstractService;

@Component
class DeviceService extends AbstractService<Device, Long> {

    protected DeviceService(DeviceRepository deviceRepository) {
        super(deviceRepository);
    }
}
