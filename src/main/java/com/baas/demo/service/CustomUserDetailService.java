package com.baas.demo.service;

import com.baas.demo.entity.SystemUser;
import com.baas.demo.repository.SystemUserRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@AllArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private SystemUserRepository systemUserRepository;
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        SystemUser systemUser = systemUserRepository.findOneByAccount(account);
        if (null == systemUser) {
            throw new UsernameNotFoundException("SystemUser not found");
        }

        UserDetails userDetails = User.builder()
                .username(systemUser.getAccount())
                .password(systemUser.getPassword())
                .authorities(new ArrayList<>())
                .build();
        return userDetails;
    }
}
