package com.adison.shop.users;

import com.adison.shop.common.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("api/users")
@Controller
@ResponseBody
public class UserController {

    private final UserService userService;
    private UriBuilder uriBuilder;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    //bad practice to pass User directly to the controller (client should not know)--being iterative here
    //return the created location's URI to the client. use helper class to add ID to the URI location.
    //@RequestBody lets us get the represented object's state into our object to be handled by the controller method
    public ResponseEntity<User> addUser(@RequestBody User user) {
        Long userId = userService.add(user).getId();
        URI locationUri = uriBuilder.requestUriWithId(userId);
        return ResponseEntity.created(locationUri).build();
    }
}
