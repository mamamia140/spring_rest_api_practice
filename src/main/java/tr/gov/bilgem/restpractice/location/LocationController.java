package tr.gov.bilgem.restpractice.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.gov.bilgem.restpractice.model.Location;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.user.UserService;

import java.util.Optional;

@RestController
@RequestMapping("${api.root}/locations")
class LocationController {

    @Autowired
    private LocationService locationService;
    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Location> getLocationById(@PathVariable long id) {
        Optional<Location> location = locationService.getById(id);
        if(location.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location.get(),HttpStatus.OK);
    }
}
