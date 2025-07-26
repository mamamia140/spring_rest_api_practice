package tr.gov.bilgem.restpractice.device;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.gov.bilgem.restpractice.controller.AbstractController;
import tr.gov.bilgem.restpractice.model.Device;
import tr.gov.bilgem.restpractice.service.AbstractService;

import java.util.Map;

@RestController
@RequestMapping("${api.root}/devices")
class DeviceController extends AbstractController<Device, Long> {

    protected DeviceController(AbstractService<Device, Long> deviceService) {
        super(deviceService);
    }

    @PatchMapping(path = "/{id}/name")
    public ResponseEntity<String> updateName(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try{
            entityService.existsById(id);
            String name = body.get("name");
            ((DeviceService) entityService).updateNameById(id,name);
            return new ResponseEntity<>(String.format("Updated name of the device id with:%s successfully", id),HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Device couldn't be updated. Internal server error\n\nError:%s",e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
