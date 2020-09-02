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

    // should be called from a frontend page, and should use PATCH or POST. or PUT. but we're using a non-canonical
    // GET to handle a request for a view rendered that the user sends by clicking on the link supplied in the registration
    // email to them.

    // instructor tip: send the GET from the registration link to frontend, provide the view there and send a request
    // from there with AJAX to the back end using a POST, PATCH, or PUT.
    @RequestMapping(value = "/not-active/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Void> activateUser(@PathVariable Long userId, @RequestParam("token") String tokenValue) {
        userService.activateUser(userId, tokenValue);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    // instead of returning a response entity with a status OK on it,
    // we could return a User and Spring would take that as an OK
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        var user = userService.getById(id);
        var userDTO = userMapper.toUserDTO(user);
        // generating links inside the body of the response with hateoas
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
}
