package tr.gov.bilgem.restpractice.location;

import org.springframework.web.bind.annotation.*;
import tr.gov.bilgem.restpractice.controller.AbstractController;
import tr.gov.bilgem.restpractice.model.Location;
import tr.gov.bilgem.restpractice.service.AbstractService;

@RestController
@RequestMapping("${api.root}/locations")
class LocationController extends AbstractController<Location, Long> {

    protected LocationController(AbstractService<Location, Long> locationService) {
        super(locationService);
    }

}
