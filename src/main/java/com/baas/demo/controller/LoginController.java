package com.baas.demo.controller;

import com.baas.demo.constant.SecurityConstant;
import com.baas.demo.dto.APIResponse;
import com.baas.demo.dto.AuthRequest;
import com.baas.demo.entity.SystemUser;
import com.baas.demo.repository.SystemUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@RestController
@Tag(name = "權限管理")
public class LoginController {
    private SystemUserRepository systemUserRepository;
    private AuthenticationProvider authenticationProvider;

    @Operation(summary = "Login 用戶登入系統並在Response Header Authorization取得token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功"),
            @ApiResponse(responseCode = "400", description = "錯誤的請求"),
            @ApiResponse(responseCode = "401", description = "帳號密碼錯誤"),
            @ApiResponse(responseCode = "500", description = "系統錯誤")
    })
    @PostMapping("/auth/login")
    public ResponseEntity<APIResponse> login (@Valid @RequestBody AuthRequest authRequest) throws ServletException, IOException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getAccount(), authRequest.getPassword());

        Authentication authenticated = authenticationProvider.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstant.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder().setIssuer("Baas").setSubject("JWT Token")
                .claim("account", authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 30000000))
                .signWith(key).compact();


        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        SystemUser systemUser = systemUserRepository.findOneByAccount(authenticated.getName());

        APIResponse<SystemUser> apiResponse = APIResponse.
                <SystemUser>builder()
                .status("SUCCESS")
                .results(systemUser)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(responseHeaders)
                .body(apiResponse);
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority: collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}