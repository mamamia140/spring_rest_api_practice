package tr.gov.bilgem.restpractice.group;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.model.Group;
import tr.gov.bilgem.restpractice.service.AbstractService;

@Component
class GroupService extends AbstractService<Group, Long> {

    private static final Log logger = LogFactory.getLog(GroupService.class);

    protected GroupService(GroupRepository groupRepository) {
        super(groupRepository);
    }

    @Override
    public Log getServiceLoggerByEntity() {
        return logger;
    }
}
