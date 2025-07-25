package tr.gov.bilgem.restpractice.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.gov.bilgem.restpractice.model.Device;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.user.UserService;

import java.util.Optional;

@RestController
@RequestMapping("${api.root}/devices")
class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Device> getDeviceById(@PathVariable long id) {
        Optional<Device> device = deviceService.getById(id);
        if(device.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(device.get(),HttpStatus.OK);
    }
}
