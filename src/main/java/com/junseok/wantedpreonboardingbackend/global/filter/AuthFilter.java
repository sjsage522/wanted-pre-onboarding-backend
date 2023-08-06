package com.junseok.wantedpreonboardingbackend.global.filter;

import com.junseok.wantedpreonboardingbackend.global.util.JwtProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
public class AuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private static final List<Pattern> EXCLUDE_URL = List.of(
            Pattern.compile("^/api/v1/users*")
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return EXCLUDE_URL.stream()
                .anyMatch(excludeUrl -> excludeUrl.matcher(request.getServletPath()).matches());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader("Authorization");
        final String jwt = authorization.split("Bearer")[1];

        final Map<String, Object> map = jwtProvider.parseJwt(jwt);
        request.setAttribute("userId", map.get("sub"));

        filterChain.doFilter(request, response);
    }
}
