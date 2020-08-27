package com.adison.shop.users;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.PagedResultDTO;
import com.adison.shop.common.web.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//defined in yml config
@RequestMapping("${apiPrefix}/users")
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UriBuilder uriBuilder = new UriBuilder();


    @PostMapping
    public ResponseEntity<UserDTO> addUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        var user = userMapper.toUser(userDTO);
        var userId = userService.add(user).getId();
        var locationUri = uriBuilder.requestUriWithId(userId);
        return ResponseEntity.created(locationUri).build();
    }

    @GetMapping("{id}")
    //instead of returning a response entity with a status OK on it,
    //we could return a User and Spring would take that as an OK
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        var user = userService.getById(id);
        var userDTO = userMapper.toUserDTO(user);
        //generating links inside the body of the response with hateoas
        userDTO.add(linkTo(methodOn(UserRestController.class).getUser(id)).withSelfRel());
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public PagedResultDTO<UserDTO> getUsersByLastName(
            @RequestParam(defaultValue = "") String lastNameFragment,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        PagedResult<User> usersPage = userService.getByLastName(lastNameFragment, pageNumber, pageSize);
       return userMapper.toUserDTOsPage(usersPage);
    }

    //below is a local, class-level exception handler

    //intercepts exceptions from a controller class and returns them to the client (remember to annotate!)
    //uses an exception hierarchy, where this ex will be thrown if its subclass instance is thrown and there's no
    //specialized method to handle that

    //a source. we can be more informative by sending back an
    //exceptionTransferObject in the body of the response. 404 may mean the client misspelled the name of the resource
    //which nonetheless exists
    /*@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDTO> onUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDTO("User not found"));

    }*/
}
