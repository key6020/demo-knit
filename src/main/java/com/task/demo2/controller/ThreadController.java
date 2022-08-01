package com.task.demo2.controller;

import com.task.demo2.dto.req.ThreadCreateReqDto;
import com.task.demo2.dto.req.ThreadUpdateReqDto;
import com.task.demo2.dto.res.CommonResponse;
import com.task.demo2.dto.res.ThreadResDto;
import com.task.demo2.service.ThreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
public class ThreadController {
    private final ThreadService threadService;
//    private final S3Service s3Service;

//    @PostMapping("/upload")
//    public ResponseEntity<S3ImageResDto> upload(@RequestPart(value = "file") MultipartFile multipartFile, @RequestPart(value = "type") String type) throws IOException {
//        return new ResponseEntity<>(s3Service.upload(multipartFile, type), HttpStatus.OK);
//    }

    @GetMapping("/v1/threads/list")
    public ResponseEntity<CommonResponse<List<ThreadResDto>>> getThreadList(@RequestParam(defaultValue = "0") Integer page) {
        return new ResponseEntity<>(threadService.getThreadList(page), HttpStatus.OK);
    }

    @GetMapping("/v1/thread/{threadId}")
    public ResponseEntity<CommonResponse<ThreadResDto>> getThreadInfo(@PathVariable Long threadId) {
        return new ResponseEntity<>(threadService.getThreadInfo(threadId), HttpStatus.OK);
    }

    @PostMapping("/v1/threads")
    public ResponseEntity<CommonResponse<?>> registerThread(@Valid @RequestBody ThreadCreateReqDto threadCreateReqDto, HttpServletRequest request) {
        return new ResponseEntity<>(threadService.registerThread(threadCreateReqDto, request), HttpStatus.OK);
    }

    @PostMapping("/v1/threads/{threadId}/like")
    public ResponseEntity<CommonResponse<?>> likeThread(@PathVariable Long threadId, HttpServletRequest request) {
        return new ResponseEntity<>(threadService.likeThread(threadId, request), HttpStatus.OK);
    }

    @PutMapping("/v1/thread/{threadId}")
    public ResponseEntity<CommonResponse<?>> updateThread(@PathVariable Long threadId, @Valid @RequestBody ThreadUpdateReqDto reqDto, HttpServletRequest request) {
        return new ResponseEntity<>(threadService.updateThread(threadId, reqDto, request), HttpStatus.OK);
    }
}
