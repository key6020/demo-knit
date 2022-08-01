package com.task.demo2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.demo2.dto.req.LoginReqDto;
import com.task.demo2.dto.req.SignupReqDto;
import com.task.demo2.dto.req.ThreadCreateReqDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class Demo2ApplicationTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Rollback(value = false)
    @Transactional
    @Test
    @DisplayName("회원가입 성공")
    void signupSuccess() throws Exception {
        SignupReqDto reqDto = new SignupReqDto();
//        reqDto.setEmail("aaa@aaa.com");
//        reqDto.setPassword("aaaaa1");
//        reqDto.setName("해리");

        reqDto.setEmail("bbb@bbb.com");
        reqDto.setPassword("bbbbb1");
        reqDto.setName("론");

        mvc.perform(post("/v1/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(reqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Matchers.is("회원가입 성공")))
                .andDo(print());
    }

    @Rollback(value = false)
    @Transactional
    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        LoginReqDto reqDto = new LoginReqDto();
        reqDto.setEmail("bbb@bbb.com");
        reqDto.setPassword("bbbbb1");

        mvc.perform(post("/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(reqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Matchers.is(200)))
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
                .andDo(print());
    }

    @Rollback(value = false)
    @Transactional
    @Test
    @DisplayName("Thread 신청")
    void getUserInfo() throws Exception {
        ThreadCreateReqDto reqDto = new ThreadCreateReqDto();
        reqDto.setTitle("title1");
        reqDto.setContent("this is content");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImJiYkBiYmIuY29tIiwiaXNzIjoiZGVtbyIsImlhdCI6MTY1OTM4MjExMSwiZXhwIjoxNjU5MzgzOTExfQ.BPMRc_YlCtk_4LdjU-2QUtpwebNddcUaVVnh7tXTpu4");
        headers.add("Refresh", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImJiYkBiYmIuY29tIiwiaXNzIjoiZGVtbyIsImlhdCI6MTY1OTM4MjExMSwiZXhwIjoxNjU5OTg2OTExfQ.3s2x4693ZmPXBxJCY-N6eSm3rIX0Z91xLynQnOPxhUs");
        mvc.perform(post("/v1/threads")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(reqDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Rollback(value = false)
    @Transactional(readOnly = true)
    @Test
    @DisplayName("Thread 조회")
    void getUserRefundInfo() throws Exception {
        mvc.perform(get("/v1/thread/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(5))
                .andDo(print());
    }

    @Rollback(value = false)
    @Transactional
    @Test
    @DisplayName("로그아웃")
    void logout() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImJiYkBiYmIuY29tIiwiaXNzIjoiZGVtbyIsImlhdCI6MTY1OTM4MjExMSwiZXhwIjoxNjU5MzgzOTExfQ.BPMRc_YlCtk_4LdjU-2QUtpwebNddcUaVVnh7tXTpu4");
        headers.add("Refresh", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImJiYkBiYmIuY29tIiwiaXNzIjoiZGVtbyIsImlhdCI6MTY1OTM4MjExMSwiZXhwIjoxNjU5OTg2OTExfQ.3s2x4693ZmPXBxJCY-N6eSm3rIX0Z91xLynQnOPxhUs");

        mvc.perform(post("/v1/user/logout")
                        .headers(headers))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Matchers.is("로그아웃 성공")))
                .andDo(print());
    }
}
