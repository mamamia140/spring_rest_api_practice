package tr.gov.bilgem.restpractice.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import tr.gov.bilgem.restpractice.controller.BaseController;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.service.AbstractService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.root}/users")
public class UserController extends BaseController<User, Long> {

    protected UserController(AbstractService<User, Long> userService) {
        super(userService);
    }

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> getUserById(@PathVariable long id) {
        return getEntityById(id);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String mode,
            HttpServletRequest request) {
        return getAllPagedEntity(page, size, sort, mode, request);
    }

}
