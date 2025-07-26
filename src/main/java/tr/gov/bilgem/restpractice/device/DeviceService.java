package tr.gov.bilgem.restpractice.device;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.model.Device;
import tr.gov.bilgem.restpractice.service.AbstractService;

@Component
class DeviceService extends AbstractService<Device, Long> {

    private static final Log logger = LogFactory.getLog(DeviceService.class);

    protected DeviceService(DeviceRepository deviceRepository) {
        super(deviceRepository);
    }

    @Override
    public Log getServiceLoggerByEntity() {
        return logger;
    }
}
