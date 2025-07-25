package tr.gov.bilgem.restpractice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.gov.bilgem.restpractice.model.User;

import java.util.Optional;

@RestController
@RequestMapping("${api.root}/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> getUserById(@PathVariable long id) {
        Optional<User> user = userService.getById(id);
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get(),HttpStatus.OK);
    }
}
