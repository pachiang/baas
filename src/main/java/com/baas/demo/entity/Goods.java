package com.baas.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "goods")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 128)
    @Size(max = 128, message = "Name length must be less than or equal to 128 characters")
    private String name;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "cr_user", nullable = false)
    @JsonIgnore
    private SystemUser crUser;

    @CreatedDate
    @Column(name = "cr_datetime", nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime crDatetime;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "up_user", nullable = false)
    @JsonIgnore
    private SystemUser upUser;

    @LastModifiedDate
    @Column(name = "up_datetime", nullable = false)
    @JsonIgnore
    private LocalDateTime upDatetime;
}
