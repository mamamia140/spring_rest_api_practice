package tr.gov.bilgem.restpractice.device;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.gov.bilgem.restpractice.controller.AbstractController;
import tr.gov.bilgem.restpractice.model.Device;
import tr.gov.bilgem.restpractice.service.AbstractService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping(path = "/{id}/ping")
    public ResponseEntity<String> ping(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try{
            entityService.existsById(id);
            Optional<Device> device = entityService.findById(id);
            String address = device.get().getIpAddress();
            if (address == null || address.isBlank()) {
                return ResponseEntity.badRequest().body("Address is null");
            }


            String ttl = body.getOrDefault("ttl", "128");
            String timeout = body.getOrDefault("timeout", "1000");
            String size = body.getOrDefault("size", "32");
            String count = body.getOrDefault("count", "4");

            String response = ((DeviceService) entityService).ping(address, ttl, timeout, size, count);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Device not found with id " + id);
        } catch (IOException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ping failed: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> bulkDelete(@RequestBody Map<String, String> body) {
        String ids = body.get("devices");
        List<Long> idList = new ArrayList<>();
        for (String id : ids.split(",")) {
            idList.add(Long.parseLong(id));
        }
        ((DeviceService) entityService).bulkDelete(idList);
        return new ResponseEntity<>("Bulk deleted devices", HttpStatus.OK);
    }

}
