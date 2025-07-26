package tr.gov.bilgem.restpractice.device;

import org.springframework.web.bind.annotation.*;
import tr.gov.bilgem.restpractice.controller.AbstractController;
import tr.gov.bilgem.restpractice.model.Device;
import tr.gov.bilgem.restpractice.service.AbstractService;

@RestController
@RequestMapping("${api.root}/devices")
class DeviceController extends AbstractController<Device, Long> {

    protected DeviceController(AbstractService<Device, Long> deviceService) {
        super(deviceService);
    }
}
