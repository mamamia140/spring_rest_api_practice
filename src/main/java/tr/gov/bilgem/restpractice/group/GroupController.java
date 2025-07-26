package tr.gov.bilgem.restpractice.group;

import org.springframework.web.bind.annotation.*;
import tr.gov.bilgem.restpractice.controller.AbstractController;
import tr.gov.bilgem.restpractice.model.Group;
import tr.gov.bilgem.restpractice.service.AbstractService;

@RestController
@RequestMapping("${api.root}/groups")
class GroupController extends AbstractController<Group, Long> {

    protected GroupController(AbstractService<Group, Long> groupService) {
        super(groupService);
    }
}
