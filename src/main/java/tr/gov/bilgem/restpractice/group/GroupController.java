package tr.gov.bilgem.restpractice.group;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.gov.bilgem.restpractice.controller.BaseController;
import tr.gov.bilgem.restpractice.model.Group;
import tr.gov.bilgem.restpractice.service.AbstractService;

import java.util.List;

@RestController
@RequestMapping("${api.root}/groups")
class GroupController extends BaseController<Group, Long> {

    protected GroupController(AbstractService<Group, Long> groupService) {
        super(groupService);
    }

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Group> getGroupById(@PathVariable long id) {
        return getEntityById(id);
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String mode,
            HttpServletRequest request) {
        return getAllPagedEntity(page, size, sort, mode, request);
    }
}
