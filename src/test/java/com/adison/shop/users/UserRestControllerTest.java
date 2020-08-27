package com.adison.shop.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserRestController.class)
@ExtendWith(SpringExtension.class)
class UserRestControllerTest {

    private static final Long USER_ID = 1L;

    private final User user = new User();
    private final UserDTO userDTO = new UserDTO();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;


    @BeforeEach
    void setUp() {
        user.setId(USER_ID);
        user.setFirstName("Charlie");
        user.setLastName("Runkle");
        user.setEmail("charlie.runkle@whatsmyname.com");

        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmailAddress(user.getEmail());

        when(userMapper.toUserDTO(user)).thenReturn(userDTO);
        when(userService.getById(USER_ID)).thenReturn(user);
    }

    @Test
    void shouldGetUserById() throws Exception {
        mockMvc.perform(get("/api/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                {
                                    "firstName": "Charlie", 
                                    "lastName": "Runkle", 
                                    "emailAddress": "charlie.runkle@whatsmyname.com"
                                }
                                """));


    }

}