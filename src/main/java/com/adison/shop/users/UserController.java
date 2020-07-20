package com.adison.shop.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//well, do not put business logic in the controller layer. controllers are just traffic cops--
@Controller
//do not do a full cycle of request handling, because we don't have a view layer here.
//instead, return an object in the body of the response
@ResponseBody
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "hello")
    public String sayHello() {
        return "Hello";
    }
}
