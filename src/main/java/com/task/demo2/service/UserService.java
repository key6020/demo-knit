package com.task.demo2.service;

import com.task.demo2.config.jwt.JwtTokenProvider;
import com.task.demo2.domain.ExpiredToken;
import com.task.demo2.domain.User;
import com.task.demo2.domain.UserToken;
import com.task.demo2.dto.req.LoginReqDto;
import com.task.demo2.dto.req.SignupReqDto;
import com.task.demo2.dto.res.CommonResponse;
import com.task.demo2.dto.res.TokenResponse;
import com.task.demo2.repository.ExpiredTokenRepository;
import com.task.demo2.repository.UserRepository;
import com.task.demo2.repository.UserTokenRepository;
import com.task.demo2.utils.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserTokenRepository userTokenRepository;
    private final ExpiredTokenRepository expiredTokenRepository;

    @Transactional
    public CommonResponse<?> signup(SignupReqDto reqDto) {
        // signup available check
        if (userRepository.existsByEmail(reqDto.getEmail())) {
            return CommonResponse.response(HttpStatus.CONFLICT.value(), "이미 가입하였습니다.");
        }
        User user = User.builder()
                .email(reqDto.getEmail())
                .name(reqDto.getName())
                .password(passwordEncoder.encode(reqDto.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return CommonResponse.response(HttpStatus.CREATED.value(), "회원가입 성공");
    }

    @Transactional
    public CommonResponse<TokenResponse> login(LoginReqDto reqDto) {
        User user = userRepository.findByEmail(reqDto.getEmail());
        if (user == null || !passwordEncoder.matches(reqDto.getPassword(), user.getPassword())) {
            return CommonResponse.response(HttpStatus.BAD_REQUEST.value(), "로그인 정보가 올바르지 않습니다.");
        }

        TokenResponse resDto = new TokenResponse();
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());
        resDto.setAccessToken(jwtTokenProvider.createAccessToken(user.getEmail()));
        resDto.setRefreshToken(refreshToken);

        UserToken userToken = UserToken.builder().userId(user.getId()).refreshToken(refreshToken).build();
        userTokenRepository.save(userToken);
        return CommonResponse.response(HttpStatus.OK.value(), "로그인 성공", resDto);
    }

    @Transactional
    public CommonResponse<?> logout(HttpServletRequest request) {
        String userEmailFromToken = request.getAttribute("email").toString();
        User user = userRepository.findByEmail(userEmailFromToken);
        if (user == null) {
            return CommonResponse.response(HttpStatus.NOT_FOUND.value(), "사용자 정보를 찾을 수 없습니다.");
        }
        List<UserToken> userTokenList = userTokenRepository.findAllByUserId(user.getId());
        if (!userTokenList.isEmpty()) {
            userTokenList.forEach(ut -> {
                var expiredToken = ExpiredToken.builder()
                        .accessToken(jwtTokenProvider.getTokenFromHeader(request))
                        .refreshToken(ut.getRefreshToken())
                        .build();

                expiredTokenRepository.save(expiredToken);
            });
        }
        userTokenRepository.deleteAll(userTokenList);

        return CommonResponse.response(HttpStatus.OK.value(), "로그아웃 성공");
    }

    @Transactional
    public CommonResponse<TokenResponse> refreshToken(HttpServletRequest request) {
        String userEmailFromToken = request.getAttribute("email").toString();
        User user = userRepository.findByEmail(userEmailFromToken);
        if (user == null) {
            return CommonResponse.response(HttpStatus.NOT_FOUND.value(), "사용자가 존재하지 않습니다.");
        }

        String refreshToken = request.getHeader("Refresh");
        userTokenRepository.deleteAllByRefreshToken(refreshToken);
        expiredTokenRepository.deleteAllByRefreshToken(refreshToken);

        TokenResponse resDto = new TokenResponse();
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());
        resDto.setAccessToken(jwtTokenProvider.createAccessToken(user.getEmail()));
        resDto.setRefreshToken(newRefreshToken);

        userTokenRepository.save(UserToken.builder().userId(user.getId()).refreshToken(newRefreshToken).build());

        return CommonResponse.response(HttpStatus.OK.value(), "토큰 재발급 성공", resDto);
    }
}
