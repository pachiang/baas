package com.baas.demo.filter;

import com.baas.demo.constant.SecurityConstant;
import com.baas.demo.entity.SystemUser;
import com.baas.demo.repository.SystemUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.util.AntPathMatcher;

@Slf4j
@Component
public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    private final SystemUserRepository systemUserRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final HandlerExceptionResolver exceptionResolver;
    public JWTTokenValidatorFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver, SystemUserRepository systemUserRepository) {
        this.exceptionResolver = exceptionResolver;
        this.systemUserRepository = systemUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwt(request);
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstant.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            String account = String.valueOf(claims.get("account"));
            SystemUser systemUser = systemUserRepository.findOneByAccount(account);
            Authentication auth = new UsernamePasswordAuthenticationToken(systemUser, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            exceptionResolver.resolveException(request, response, null, ex);
        }
    }

    private static String getJwt(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(SecurityConstant.JWT_HEADER);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new BadCredentialsException("Invalid Token received!");
        }
        return authorizationHeader.substring(7);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return Arrays.stream(SecurityConstant.WHITELIST_URLS)
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}
