package com.adison.shop.users;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.PagedResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserWebController {

    private final UserService userService;
    private final UserMapper userMapper;

    //pattern with forms: get (gets form), post (posts form data), redirect (gets users, e.g., with the new data included)

    //this is to return a form view with a blank user object to populate through the sf form
    @GetMapping("add-user.html")
    public ModelAndView addUser() {
        ModelAndView mv = new ModelAndView("add-user");
        //if I don't add a key, the object will be identified by type, lower-case ("user"). add for binding
        mv.addObject(new User());
        return mv;
    }

    //this is to add the user (Spring will map the form data in the view to the User object and inject it into the method
    @PostMapping("add-user.html")
    public String saveUser(User user) {
        userService.add(user);
        return "redirect:show-users.html";
    }

    @GetMapping("show-users.html")
    public ModelAndView showUsers(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "5") int pageSize
    ) {
        PagedResult<User> userPage = userService.getAll(pageNumber, pageSize);
        ModelAndView mv = new ModelAndView("users");
        mv.addObject("users", userPage);
        return mv;
    }
}
