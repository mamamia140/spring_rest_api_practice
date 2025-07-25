package tr.gov.bilgem.restpractice.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.model.Location;

import java.util.Optional;

@Component
class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Optional<Location> getById(long id){
        return locationRepository.findById(id);
    }
}
