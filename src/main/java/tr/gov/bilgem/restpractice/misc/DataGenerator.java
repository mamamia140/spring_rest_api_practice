package tr.gov.bilgem.restpractice.misc;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.device.DeviceRepository;
import tr.gov.bilgem.restpractice.group.GroupRepository;
import tr.gov.bilgem.restpractice.location.LocationRepository;
import tr.gov.bilgem.restpractice.model.Device;
import tr.gov.bilgem.restpractice.model.Group;
import tr.gov.bilgem.restpractice.model.Location;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.user.UserRepository;

import java.util.Random;

@Component
@Profile({"dev", "test"})
@RequiredArgsConstructor
public class DataGenerator {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final LocationRepository locationRepository;
    private final DeviceRepository deviceRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    @PostConstruct
    public void seed() {

        if (userRepository.count() > 0 &&
                groupRepository.count() > 0 &&
                locationRepository.count() > 0 &&
                deviceRepository.count() > 0) {
            System.out.println("Database already populated. Skipping data generation.");
            return;
        }

        seedUsers();
        seedGroups();
        seedLocations();
        seedDevices();

        System.out.println("Example data generated for profiles: dev/test");
    }

    private void seedUsers() {
        userRepository.save(new User("admin", faker.internet().emailAddress(), faker.internet().password(), User.Role.ADMIN));
        userRepository.save(new User("operator", faker.internet().emailAddress(), faker.internet().password(),User.Role.OPERATOR));
        userRepository.save(new User("observer", faker.internet().emailAddress(), faker.internet().password(),User.Role.OBSERVER));
    }

    private void seedGroups() {
        for (int i = 0; i < 5; i++) {
            Group group = new Group();
            group.setName("group-" + i);
            group.setDescription(faker.lorem().sentence());
            groupRepository.save(group);
        }
    }

    private void seedLocations() {
        for (int i = 0; i < 5; i++) {
            Location location = new Location();
            location.setCity(faker.address().city());
            location.setCounty("Yenisehir");
            location.setCountry(faker.address().country());
            location.setLongitude(random.nextDouble() * 360 - 180);
            location.setAltitude(random.nextDouble() * 1000);
            locationRepository.save(location);
        }
    }

    private void seedDevices() {
        var locations = locationRepository.findAll();
        var groups = groupRepository.findAll();
        var users = userRepository.findAll();

        for (int i = 0; i < 10; i++) {
            Device device = new Device();
            device.setName("dev-" + i);
            device.setSerialNo(String.valueOf(random.nextDouble() * 10000));
            device.setIpAddress(faker.internet().ipV4Address());
            device.setAccessible(random.nextBoolean());

            // Set random Location, Group, and Owner (User)
            device.setLocation(locations.get(random.nextInt(locations.size())));
            device.setGroup(groups.get(random.nextInt(groups.size())));
            device.setOwner(users.get(random.nextInt(users.size())));

            deviceRepository.save(device);
        }
    }
}
