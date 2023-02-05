package com.hansung.capstone.community;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Slf4j
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PostService postService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 처리
                .build();
    }

    @Test
    @Order(100)
    @DisplayName("Post create post test - /api/community/post/create")
    void createPostTest() throws Exception {
        PostDTO.CreateRequestDTO req = PostDTO.CreateRequestDTO.builder()
                .title("test-title")
                .content("테스트 콘텐트 입니다").build();

        String cnt = objectMapper.writeValueAsString(req);

        MvcResult result = mockMvc.perform(post("/api/community/post/create")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String exp = "\"id\":1,\"title\":\"test-title\"";
        assertTrue(result.getResponse().getContentAsString().contains(exp));
    }

    @Test
    @Order(200)
    @DisplayName("Put modify post test - /api/community/post/modify")
    void modifyPostTest() throws Exception {
        PostDTO.ModifyRequestDTO req = PostDTO.ModifyRequestDTO.builder()
                .id(1L)
                .title("제목 변경 테스트")
                .content("내용 변경 테스트").build();

        String cnt = objectMapper.writeValueAsString(req);

        MvcResult result = mockMvc.perform(put("/api/community/post/modify")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String exp = "\"id\":1,\"title\":\"제목 변경 테스트\"";
        assertTrue(result.getResponse().getContentAsString().contains(exp));
    }

    @Test
    @Order(300)
    @DisplayName("Get all post test - /api/community/post/list")
    void getAllPostTest() throws Exception {
        for (int i = 1; i <= 300; i++ ) {
            PostDTO.CreateRequestDTO req = PostDTO.CreateRequestDTO.builder()
                    .title("test-title - [%03d]".formatted(i))
                    .content("테스트 콘텐트 입니다").build();
            this.postService.createPost(req);
        }
        MvcResult result = mockMvc.perform(get("/api/community/post/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }
}