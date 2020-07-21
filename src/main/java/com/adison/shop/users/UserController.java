package com.adison.shop.users;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.PagedResultTransferObject;
import com.adison.shop.common.web.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UriBuilder uriBuilder;


    @PostMapping
    //bad practice to pass User directly to the controller (client should not know)--being iterative here
    //return the created location's URI to the client. use helper class to add ID to the URI location.
    //@RequestBody lets us get the represented object's state into our object to be handled by the controller method
    public ResponseEntity<User> addUser(@RequestBody UserTransferObject userTransferObject) {
        User user = userMapper.toUser(userTransferObject);
        Long userId = userService.add(user).getId();
        URI locationUri = uriBuilder.requestUriWithId(userId);
        return ResponseEntity.created(locationUri).build();
    }

    @GetMapping("{id}")
    //instead of returning a response entity with a status OK on it,
    //we could return a User and Spring would take that as an OK
    public ResponseEntity<UserTransferObject> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        UserTransferObject userTransferObject = userMapper.toUserTransferObject(user);
        return ResponseEntity.ok(userTransferObject);
    }

    @GetMapping
    public PagedResultTransferObject<UserTransferObject> getUsersByLastName(
            @RequestParam String lastNameFragment,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        PagedResult<User> usersPage = userService.getByLastName(lastNameFragment, pageNumber, pageSize);
       return userMapper.toUserTransferObjectsPage(usersPage);
    }
}
