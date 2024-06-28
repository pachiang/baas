package com.baas.demo.repository;

import com.baas.demo.entity.SystemUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SystemUserRepository extends JpaRepository<SystemUser, UUID> {
    SystemUser findOneByAccount(String account);

    Page<SystemUser> findAll(Pageable pageable);
}
