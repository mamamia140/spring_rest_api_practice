package tr.gov.bilgem.restpractice.location;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.model.Location;
import tr.gov.bilgem.restpractice.service.AbstractService;

@Component
class LocationService extends AbstractService<Location, Long> {

    private static final Log logger = LogFactory.getLog(LocationService.class);

    protected LocationService(LocationRepository locationRepository) {
        super(locationRepository);
    }

    @Override
    public Log getServiceLoggerByEntity() {
        return logger;
    }
}
