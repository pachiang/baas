package com.baas.demo.audit;

import com.baas.demo.entity.SystemUser;
import com.baas.demo.repository.SystemUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AuditorProvider implements AuditorAware<SystemUser> {

    @Override
    public Optional<SystemUser> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        return Optional.ofNullable((SystemUser) authentication.getPrincipal());
    }
}