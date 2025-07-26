package tr.gov.bilgem.restpractice.device;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.gov.bilgem.restpractice.controller.BaseController;
import tr.gov.bilgem.restpractice.model.Device;
import tr.gov.bilgem.restpractice.service.AbstractService;

import java.util.List;

@RestController
@RequestMapping("${api.root}/devices")
class DeviceController extends BaseController<Device, Long> {

    protected DeviceController(AbstractService<Device, Long> deviceService) {
        super(deviceService);
    }

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Device> getDeviceById(@PathVariable long id) {
        return getEntityById(id);
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String mode,
            HttpServletRequest request) {
        return getAllPagedEntity(page, size, sort, mode, request);
    }
}
