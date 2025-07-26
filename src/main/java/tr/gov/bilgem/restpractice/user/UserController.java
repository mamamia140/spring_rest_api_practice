package tr.gov.bilgem.restpractice.user;

import org.springframework.web.bind.annotation.*;
import tr.gov.bilgem.restpractice.controller.AbstractController;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.service.AbstractService;

@RestController
@RequestMapping("${api.root}/users")
public class UserController extends AbstractController<User, Long> {

    protected UserController(AbstractService<User, Long> userService) {
        super(userService);
    }

}
