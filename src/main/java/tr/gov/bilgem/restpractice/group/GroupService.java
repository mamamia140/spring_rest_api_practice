package tr.gov.bilgem.restpractice.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tr.gov.bilgem.restpractice.model.Group;

import java.util.Optional;

@Component
class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Optional<Group> getById(long id){
        return groupRepository.findById(id);
    }
}
