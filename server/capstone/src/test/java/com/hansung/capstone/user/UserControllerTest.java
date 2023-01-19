package com.hansung.capstone.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerTest.class)
@ContextConfiguration
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAdd() {
        assertEquals(42, Integer.sum(19, 23));
    }

    @Test
    @DisplayName("Post login check - /login/check")
    void loginCheckTest() throws Exception {
        AppUser user = AppUser.builder()
                .password("1234")
                .username("hoon")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/login/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Post register test - /login/register")
    void registerTest() throws Exception{
        UserCreateForm user = UserCreateForm.builder()
                .email("test")
                .password1("1234")
                .password2("1234")
                .username("ho3on")
                .build();

        mockMvc.perform(post("/login/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}