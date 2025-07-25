package tr.gov.bilgem.restpractice.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.model.Device;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public Optional<Device> getById(long id){
        return deviceRepository.findById(id);
    }
}
