package com.hansung.capstone.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepository userRepository;

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

        // 회원가입
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

        // 중복된 회원가입 오류
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

        // 로그인
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

        // 잘못된 이메일

        mockMvc.perform(post("/api/users/signin")
                        .content(cnt)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(status().reason("AppUser Not Found"));

        UserDTO.SignInRequestDTO user2 = UserDTO.SignInRequestDTO.builder()
                .email("hoon@test.com")
                .password("12345").build();

        // 잘못된 비밀번호

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
    @DisplayName("Get email & nick dup test - /api/users/email/duplicate-check")
    void emailAndNickTest() throws Exception {
        String dupEmail = "hoon@test.com";
        String dupNick = "훈";

        // 중복된 이메일
        mockMvc.perform(get("/api/users/email/duplicate-check")
                        .param("email", dupEmail))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("BAD"));

        // 중복된 닉네임
        mockMvc.perform(get("/api/users/nickname/duplicate-check")
                        .param("nickname", dupNick))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("BAD"));
    }

    @Test
    @Order(600)
    @DisplayName("Put modify password test - /api/users/modifyPW")
    void ModifyPWTest() throws Exception{
        UserDTO.ModifyPWRequestDTO req = UserDTO.ModifyPWRequestDTO.builder()
                .email("hoon@test.com")
                .password("131313").build();
        // 잘못된 로그인
        mockMvc.perform(post("/api/users/signin")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // 비밀번호 변경
        mockMvc.perform(put("/api/users/modifyPW")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        // 변경된 비밀번호로 로그인
        mockMvc.perform(post("/api/users/signin")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(700)
    @DisplayName("Put modify nickname test - /api/users/modifyNick")
    void ModifyNickTest() throws Exception {
        UserDTO.ModifyNickRequestDTO req = UserDTO.ModifyNickRequestDTO.builder()
                .email("hoon@test.com")
                .nickname("창").build();

        // 변경

        mockMvc.perform(put("/api/users/modifyNick")
                .content(objectMapper.writeValueAsString(req))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(800)
    @DisplayName("Get find email test - /api/users/findID")
    void findIDTest() throws Exception {
        // email 찾기

        MvcResult result = mockMvc.perform(get("/api/users/findID")
                        .param("username", "훈")
                        .param("birthday", "12345678"))
                .andDo(print())
                .andReturn();

        List<String> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>() {});

        List<String> expectation = new ArrayList<>();
        expectation.add("ho**@test.com");
        assertTrue(actual.equals(expectation));

        // 이름과 생년월일이 같은 새로운 사람
        UserDTO.SignUpRequestDTO req = UserDTO.SignUpRequestDTO.builder()
                .email("103103103@test.com")
                .password("1234")
                .nickname("test")
                .username("훈")
                .birthday("12345678").build();

        mockMvc.perform(post("/api/users/signup")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result2 = mockMvc.perform(get("/api/users/findID")
                        .param("username", "훈")
                        .param("birthday", "12345678"))
                .andDo(print())
                .andReturn();

        List<String> actual2 = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>() {});
        expectation.add("10*******@test.com");
        System.out.println(actual2);
    }
}