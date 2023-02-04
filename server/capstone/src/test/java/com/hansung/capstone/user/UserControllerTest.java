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

import javax.print.attribute.standard.Media;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .nickname("훈")
                .username("훈")
                .birthday("12345678").build();

        mockMvc.perform(post("/api/users/signup")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nickname").value("훈"));
    }

    @Test
    @Order(200)
    @DisplayName("Post signup dup test - /api/users/signup")
    void signupDupTest() throws Exception {
        UserDTO.SignUpRequestDTO req = UserDTO.SignUpRequestDTO.builder()
                .email("hoon@test.com")
                .password("1234")
                .nickname("훈")
                .username("훈")
                .birthday("12345678").build();

        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Already Exists"));
    }

    @Test
    @Order(300)
    @DisplayName("Post signin test - /api/users/signin")
    void signInTest() throws Exception {
        UserDTO.SignInRequestDTO req = UserDTO.SignInRequestDTO.builder()
                .email("hoon@test.com")
                .password("1234").build();

        String content = objectMapper.writeValueAsString(req);
        mockMvc.perform(post("/api/users/signin")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("훈"));
    }

    @Test
    @Order(400)
    @DisplayName("Post signin error test - /api/users/signin")
    void signinErrorTest() throws Exception {
        UserDTO.SignInRequestDTO req = UserDTO.SignInRequestDTO.builder()
                .email("test@test.com")
                .password("1234").build();

        String cnt = objectMapper.writeValueAsString(req);
        mockMvc.perform(post("/api/users/signin")
                        .content(cnt)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(status().reason("AppUser Not Found"));

        UserDTO.SignInRequestDTO user2 = UserDTO.SignInRequestDTO.builder()
                .email("hoon@test.com")
                .password("12345").build();

        cnt = objectMapper.writeValueAsString(user2);
        mockMvc.perform(post("/api/users/signin")
                        .content(cnt)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("BAD"));
    }

    @Test
    @Order(500)
    @DisplayName("Get email&nick dup test - /api/users/duplicate-check")
    void emailAndNickTest() throws Exception {
        String dupEmail = "hoon@test.com";
        String dupNick = "훈";

        mockMvc.perform(get("/api/users/duplicate-check")
                        .param("mailOrNick", dupEmail))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("BAD"));

        mockMvc.perform(get("/api/users/duplicate-check")
                        .param("mailOrNick", dupNick))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("BAD"));
    }

    @Test
    @Order(600)
    @DisplayName("Put modify password test - /api/users/updatePW")
    void ModifyPWTest() throws Exception{
        UserDTO.UpdatePWRequestDTO req = UserDTO.UpdatePWRequestDTO.builder()
                .email("hoon@test.com")
                .password("131313").build();
        mockMvc.perform(put("/api/users/updatePW")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
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