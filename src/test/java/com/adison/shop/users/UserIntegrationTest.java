package com.adison.shop.users;

import com.adison.shop.ShopApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ShopApplication.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserIntegrationTest {

    private static final long USER_ID = 1;

    private final User user = new User();

    @Autowired
    private MockMvc mockMvc;
    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        user.setId(USER_ID);
        user.setFirstName("Adi");
        user.setLastName("La Poope");
        user.setEmail("adi.lapoope@gmail.com");
    }

    @Test
    void shouldReturnUserById() throws Exception {
        //really, really saving user in db
        entityManager.persist(user);
        mockMvc.perform(get("/api/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.emailAddress", is(user.getEmail())));
    }
}
