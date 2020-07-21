package com.adison.shop.users;

import com.adison.shop.common.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("api/users")
@Controller
@ResponseBody
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UriBuilder uriBuilder;


    @PostMapping
    //bad practice to pass User directly to the controller (client should not know)--being iterative here
    //return the created location's URI to the client. use helper class to add ID to the URI location.
    //@RequestBody lets us get the represented object's state into our object to be handled by the controller method
    public ResponseEntity<User> addUser(@RequestBody User user) {
        Long userId = userService.add(user).getId();
        URI locationUri = uriBuilder.requestUriWithId(userId);
        return ResponseEntity.created(locationUri).build();
    }

    @GetMapping("{id}")
    //instead of returning a response entity with a status OK on it, we could return a User and Spring would take that as
    //an OK
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }
}
