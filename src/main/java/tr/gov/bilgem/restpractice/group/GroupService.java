package tr.gov.bilgem.restpractice.group;

import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.model.Group;
import tr.gov.bilgem.restpractice.service.AbstractService;

@Component
class GroupService extends AbstractService<Group, Long> {

    protected GroupService(GroupRepository groupRepository) {
        super(groupRepository);
    }
}
