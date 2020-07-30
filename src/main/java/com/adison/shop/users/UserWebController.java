package com.adison.shop.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserWebController {

    private final UserService userService;
    private final UserMapper userMapper;

    //pattern with forms: get (gets form), post (posts form data), redirect (gets users, e.g., with the new data included)

    //this is to return a form view with a blank user object to populate through the sf form
    @GetMapping("add-user.html")
    public ModelAndView addUser() {
        var modelAndView = new ModelAndView("add-user");
        //Spring will map the form data in the view to the User object and inject it into the method
        //if I don't add a key, the object will be identified by type, lower-case ("user"). add for binding
        modelAndView.addObject(new User());
        return modelAndView;
    }

    //this is to add the user (Injects result of binding data in the form to the provided user instance)
    @PostMapping("add-user.html")
    public String saveUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //an error redirects user to same view
            return "add-user";
        }
        userService.add(user);
        return "redirect:show-users.html";
    }

    @GetMapping("show-users.html")
    public ModelAndView showUsers(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "5") int pageSize
    ) {
        var usersPage = userService.getAll(pageNumber, pageSize);
        var modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", usersPage);
        return modelAndView;
    }
}
