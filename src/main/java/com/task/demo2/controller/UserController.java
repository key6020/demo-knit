package com.task.demo2.controller;

import com.task.demo2.dto.req.LoginReqDto;
import com.task.demo2.dto.req.SignupReqDto;
import com.task.demo2.dto.res.CommonResponse;
import com.task.demo2.dto.res.TokenResponse;
import com.task.demo2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Validated
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<?>> signup(@RequestBody @Valid SignupReqDto reqDto) {
        return new ResponseEntity<>(userService.signup(reqDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<TokenResponse>> login(@RequestBody @Valid LoginReqDto reqDto) {
        return new ResponseEntity<>(userService.login(reqDto), HttpStatus.OK);
    }

    // logout
    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<?>> logout(HttpServletRequest request) {
        return new ResponseEntity<>(userService.logout(request), HttpStatus.OK);
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<CommonResponse<TokenResponse>> refreshToken(HttpServletRequest request) {
        return new ResponseEntity<>(userService.refreshToken(request), HttpStatus.OK);
    }
}
