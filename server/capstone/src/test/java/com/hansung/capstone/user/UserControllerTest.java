package com.hansung.capstone.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(100)
    @DisplayName("Post signup test - /api/users/singup")
    void signupTest() throws Exception {
        UserDTO.SignUpRequestDTO req = UserDTO.SignUpRequestDTO.builder()
                .email("hoon@test.com")
                .password("1234")
                .nickname("hoon")
                .username("훈")
                .birthday("12345678").build();

        mockMvc.perform(post("/api/users/signup")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nickname").value("hoon"));
    }

    @Test
    @Order(200)
    @DisplayName("Post signup dup test - /api/users/signup")
    void signupDupTest() throws Exception{
        UserDTO.SignUpRequestDTO req = UserDTO.SignUpRequestDTO.builder()
                .email("hoon@test.com")
                .password("1234")
                .nickname("hoon")
                .username("훈")
                .birthday("12345678").build();

        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Already Exists"));
    }

//    @Test
//    @DisplayName("Post login check - /login/check")
//    void loginCheckTest() throws Exception {
//        User user1 = User.builder()
//                .username("hoon")
//                .password("1234").build();
//
//        String content = objectMapper.writeValueAsString(user1);
//        mockMvc.perform(post("/login/check")
//                        .content(content)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string("good"));
//    }
//
//    @Test
//    @DisplayName("Post login error - /login/check")
//    void loginErrorTest() throws Exception{
//        User user1 = User.builder()
//                .username("104")
//                .password("1234").build();
//
//        String cnt = objectMapper.writeValueAsString(user1);
//        mockMvc.perform(post("/login/check")
//                        .content(cnt)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(status().reason("AppUser Not Found"));
//
//        User user2 = User.builder()
//                .username("hoon")
//                .password("123").build();
//
//        cnt = objectMapper.writeValueAsString(user2);
//        mockMvc.perform(post("/login/check")
//                        .content(cnt)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("bad"));
//    }
//
//    @Test
//    @DisplayName("Get FindID - /login/findID")
//    void findIDTest() throws Exception {
//        Map<String, String> emailMap = new HashMap<>();
//        emailMap.put("email", "hoon@hoon.com");
//        String cnt = objectMapper.writeValueAsString(emailMap);
//
//        mockMvc.perform(get("/login/findID")
//                        .content(cnt)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string("hoon"));
//    }
//
//    @Test
//    @DisplayName("Get FindId error - /login/findID")
//    void findIDErrorTest() throws Exception {
//        Map<String, String> emailMap = new HashMap<>();
//        emailMap.put("email", "error@error.com");
//        String cnt = objectMapper.writeValueAsString(emailMap);
//        mockMvc.perform(get("/login/findID")
//                        .content(cnt)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(status().reason("AppUser Not Found"));
//    }
}