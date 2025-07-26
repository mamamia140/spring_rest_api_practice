package tr.gov.bilgem.restpractice.location;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.gov.bilgem.restpractice.controller.BaseController;
import tr.gov.bilgem.restpractice.model.Location;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.service.AbstractService;
import tr.gov.bilgem.restpractice.user.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.root}/locations")
class LocationController extends BaseController<Location, Long> {

    protected LocationController(AbstractService<Location, Long> locationService) {
        super(locationService);
    }

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Location> getLocationById(@PathVariable long id) {
        return getEntityById(id);
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String mode,
            HttpServletRequest request) {
        return getAllPagedEntity(page, size, sort, mode, request);
    }
}
