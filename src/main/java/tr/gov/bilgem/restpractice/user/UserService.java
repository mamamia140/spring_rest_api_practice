package tr.gov.bilgem.restpractice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.model.User;

import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public Optional<User> getById(long id) {
        return userRepository.findById(id);
    }
}
