package com.baas.demo.repository;

import com.baas.demo.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GoodsRepository extends JpaRepository<Goods, UUID> {
}
