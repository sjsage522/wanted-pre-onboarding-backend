package com.junseok.wantedpreonboardingbackend.global.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junseok.wantedpreonboardingbackend.global.dto.ApiResult;
import com.junseok.wantedpreonboardingbackend.global.exception.dto.ErrorCode;
import com.junseok.wantedpreonboardingbackend.global.exception.dto.ErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 필터에서 발생한 exception 처리
 */
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_TOKEN);
            sendErrorResponse(response, errorResponse, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE);
            sendErrorResponse(response, errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    public void sendErrorResponse(HttpServletResponse httpResponse, ErrorResponse errorResponse, HttpStatus status) throws IOException {
        httpResponse.setStatus(status.value());
        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpResponse.setCharacterEncoding("utf-8");
        httpResponse.getWriter().print(objectMapper.writeValueAsString(ApiResult.failed(errorResponse)));
    }
}
