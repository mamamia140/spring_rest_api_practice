package tr.gov.bilgem.restpractice.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.service.AbstractService;

import java.util.Optional;

@Service
public class UserService extends AbstractService<User, Long> {

    private static final Log logger = LogFactory.getLog(UserService.class);

    protected UserService(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public Log getServiceLoggerByEntity() {
        return logger;
    }
}
