package com.adison.shop.users;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.PagedResultDTO;
import com.adison.shop.common.web.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UriBuilder uriBuilder;


    @PostMapping
    public ResponseEntity<UserDTO> addUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult
    ) {
        //validation with @Valid and a BindingResult instance
        //possible to see what fields failed to pass validation
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        User user = userMapper.toUser(userDTO);
        Long userId = userService.add(user).getId();
        URI locationUri = uriBuilder.requestUriWithId(userId);
        return ResponseEntity.created(locationUri).build();
    }

    @GetMapping("{id}")
    //instead of returning a response entity with a status OK on it,
    //we could return a User and Spring would take that as an OK
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        UserDTO userDTO = userMapper.toUserTransferObject(user);
        //generating links inside the body of the response with hateoas
        userDTO.add(linkTo(methodOn(UserController.class).getUser(id)).withSelfRel());
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public PagedResultDTO<UserDTO> getUsersByLastName(
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

    //a 4ource. we can be more informative by sending back an
    //exceptionTransferObject in the body of the response.04 may mean the client misspelled the name of the res
    /*@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionTransferObject> onUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionTransferObject("User not found"));

    }*/
}
