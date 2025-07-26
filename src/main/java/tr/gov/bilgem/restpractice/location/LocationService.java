package tr.gov.bilgem.restpractice.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.model.Location;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.service.AbstractService;
import tr.gov.bilgem.restpractice.user.UserRepository;

import java.util.Optional;

@Component
class LocationService extends AbstractService<Location, Long> {

    protected LocationService(LocationRepository locationRepository) {
        super(locationRepository);
    }
}
