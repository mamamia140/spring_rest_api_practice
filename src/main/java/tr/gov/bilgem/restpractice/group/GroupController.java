package tr.gov.bilgem.restpractice.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.gov.bilgem.restpractice.model.Group;
import tr.gov.bilgem.restpractice.model.User;

import java.util.Optional;

@RestController
@RequestMapping("${api.root}/groups")
class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Group> getGroupById(@PathVariable long id) {
        Optional<Group> group = groupService.getById(id);
        if(group.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(group.get(),HttpStatus.OK);
    }
}
