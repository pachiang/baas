package com.baas.demo.config;

import com.baas.demo.entity.SystemUser;
import com.baas.demo.exception.InvalidAccountOrPasswordException;
import com.baas.demo.repository.SystemUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;
@AllArgsConstructor
@Component
@Slf4j
public class CustomUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    private SystemUserRepository systemUserRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String account = authentication.getName();

        if (null == authentication.getCredentials()) {
            throw new InvalidAccountOrPasswordException("Credentials not found!");
        }

        String password = authentication.getCredentials().toString();
        SystemUser systemUser = systemUserRepository.findOneByAccount(account);

        if (null == systemUser) {
            throw new InvalidAccountOrPasswordException("No user registered with this details!");
        }

        if (!passwordEncoder.matches(password, systemUser.getPassword())) {
            throw new InvalidAccountOrPasswordException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(account, password, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}