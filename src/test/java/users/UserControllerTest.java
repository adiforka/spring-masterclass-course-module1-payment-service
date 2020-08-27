package users;

import com.adison.shop.users.User;
import com.adison.shop.users.UserDTO;
import com.adison.shop.users.UserMapper;
import com.adison.shop.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    private static final long USER_ID = 1;

    private final User user = new User();
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        user.setId(1L);
        user.setFirstName("Adi");
        user.setLastName("La Poope");
        user.setEmail("adi.lapoope@gmail.com");

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getFirstName());
        userDTO.setEmailAddress(user.getEmail());

        when(userMapper.toUserDTO(user)).thenReturn(userDTO);
        when(userService.getById(USER_ID)).thenReturn(user);
    }

    /*@Test
    void shouldReturnUserById() throws Exception {
        mockMvc.perform(get("/api/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }*/


}
