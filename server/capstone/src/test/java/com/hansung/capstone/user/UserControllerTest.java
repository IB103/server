package com.hansung.capstone.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 처리
                .build();
    }

    @Test
    @DisplayName("Post register test - /login/register")
    void registerTest() throws Exception{
        UserCreateForm user = UserCreateForm.builder()
                .email("hoon@hoon.com")
                .password1("1234")
                .password2("1234")
                .username("hoon")
                .build();

        mockMvc.perform(post("http://localhost:8080/login/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Post register dup test - /login/register")
    void registerDupTest() throws Exception{
        UserCreateForm user = UserCreateForm.builder()
                .email("hoon@hoon.com")
                .password1("1234")
                .password2("1234")
                .username("hoon")
                .build();

        mockMvc.perform(post("http://localhost:8080/login/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Already Exists"));
    }
    @Test
    @DisplayName("Post login check - /login/check")
    void loginCheckTest() throws Exception {
        AppUser user1 = AppUser.builder()
                .username("hoon")
                .password("1234").build();

        String content = objectMapper.writeValueAsString(user1);
        mockMvc.perform(post("/login/check")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("good"));
    }

    @Test
    @DisplayName("Post login error - /login/check")
    void loginErrorTest() throws Exception{
        AppUser user1 = AppUser.builder()
                .username("104")
                .password("1234").build();

        String cnt = objectMapper.writeValueAsString(user1);
        mockMvc.perform(post("/login/check")
                        .content(cnt)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(status().reason("AppUser Not Found"));

        AppUser user2 = AppUser.builder()
                .username("hoon")
                .password("123").build();

        cnt = objectMapper.writeValueAsString(user2);
        mockMvc.perform(post("/login/check")
                        .content(cnt)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("bad"));
    }

    @Test
    @DisplayName("Get FindID - /login/findID")
    void findIDTest() throws Exception {
        Map<String, String> emailMap = new HashMap<>();
        emailMap.put("email", "hoon@hoon.com");
        String cnt = objectMapper.writeValueAsString(emailMap);

        mockMvc.perform(get("/login/findID")
                        .content(cnt)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hoon"));
    }
}