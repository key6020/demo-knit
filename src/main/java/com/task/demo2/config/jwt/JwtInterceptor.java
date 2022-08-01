package com.task.demo2.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }

        String token = jwtTokenProvider.getTokenFromHeader(request);
        if (token == null) {
            return false;
        }
        String refreshToken = request.getHeader("Refresh");
        if (refreshToken != null) {
            jwtTokenProvider.validateRefreshToken(refreshToken);
        }
        request.setAttribute("email", jwtTokenProvider.parseToken(token).get("email"));
        return !jwtTokenProvider.validateAccessToken(token);
    }
}
