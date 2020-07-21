package com.adison.shop.users;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.PagedResultTransferObject;
import com.adison.shop.common.web.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RequestMapping("api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UriBuilder uriBuilder;


    @PostMapping
    public ResponseEntity<UserTransferObject> addUser(
            @Valid @RequestBody UserTransferObject userTransferObject,
            BindingResult bindingResult
    ) {
        //validation with @Valid and a BindingResult instance
        //possible to see what fields failed to pass validation
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
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

    //below is a local, class-level exception handler

    //intercepts exceptions from a controller class and returns them to the client (remember to annotate!)
    //uses an exception hierarchy, where this ex will be thrown if its subclass instance is thrown and there's no
    //specialize method to handle that

    //a 404 may mean the client misspelled the name of the resource. we can be more informative by sending back an
    //exceptionTransferObject in the body of the response.
    /*@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionTransferObject> onUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionTransferObject("User not found"));

    }*/
}
