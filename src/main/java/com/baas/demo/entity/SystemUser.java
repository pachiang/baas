package com.baas.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "system_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "_id", updatable = false, nullable = false)
    @JsonIgnore
    private UUID id;

    @Column(name = "account", nullable = false, unique = true, length = 128)
    private String account;

    @Column(name = "password", nullable = false, length = 128)
    @JsonIgnore
    private String password;

    @Column(name = "name", length = 128)
    private String name;
}