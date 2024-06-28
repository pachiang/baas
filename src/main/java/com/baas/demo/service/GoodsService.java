package com.baas.demo.service;

import com.baas.demo.entity.Goods;
import com.baas.demo.exception.GoodsNotFoundException;
import com.baas.demo.repository.GoodsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Service
@AllArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;

    public Goods add(Goods goods) {
        return goodsRepository.save(goods);
    }

    public Goods findById(UUID _id) {
        return goodsRepository.findById(_id)
                .orElseThrow(() -> new GoodsNotFoundException("Goods not found"));
    }

    public List<Goods> findAll() {
        return goodsRepository.findAll();
    }

    public Goods update(UUID _id, String name) {
        Goods goods = goodsRepository.findById(_id)
                .orElseThrow(() -> new GoodsNotFoundException("Goods not found"));

        goods.setName(name);
        return goodsRepository.save(goods);
    }

    public Goods delete(UUID _id) {
        Goods goods = goodsRepository.findById(_id)
                .orElseThrow(() -> new GoodsNotFoundException("Goods not found"));

        goodsRepository.delete(goods);
        return goods;
    }
}
