package com.hansung.capstone.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hansung.capstone.SecurityConfig;
import com.hansung.capstone.user.AppUser;
import com.hansung.capstone.user.UserCreateForm;
import com.hansung.capstone.user.UserRepository;
import com.hansung.capstone.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultHandlers.exportTestSecurityContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class))
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;



    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get check")
    void getCheck() throws Exception {

        mockMvc.perform(get("/login/hi"))
                .andDo(print())
                .andExpect(status().isOk());
    }
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

        mockMvc.perform(post("http://localhost:8080/login/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(exportTestSecurityContext())
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

        mockMvc.perform(post("http://localhost:8080/login/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("real test")
    void testTest() throws Exception {
        UserCreateForm user = UserCreateForm.builder()
                .email("hi@hi")
                .password1("123")
                .password2("123")
                .username("hi")
                .build();

        mockMvc.perform(post("/login/hi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}