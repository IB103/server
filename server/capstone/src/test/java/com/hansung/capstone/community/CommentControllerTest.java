package com.hansung.capstone.community;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.MimeMessage;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 처리
                .build();
    }

    @Test
    @DisplayName("Post create comment test - /api/community/comment/create")
    @Order(100)
    void createCommentTest() throws Exception{
        // POST 생성
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

        CommentDTO.CreateRequestDTO comment = CommentDTO.CreateRequestDTO.builder()
                .postId(1L)
                .content("1번 댓글")
                .build();

        MvcResult result1 = mockMvc.perform(post("/api/community/comment/create")
                .content(objectMapper.writeValueAsBytes(comment))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result2 = mockMvc.perform(get("/api/community/post/detail")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }

}
