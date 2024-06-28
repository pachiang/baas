package com.baas.demo.controller;

import com.baas.demo.dto.APIResponse;
import com.baas.demo.entity.Goods;
import com.baas.demo.exception.GoodsNotFoundException;
import com.baas.demo.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "商品管理")
public class GoodsController {

    private final GoodsService goodsService;

    @Operation(summary = "新增商品")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功"),
            @ApiResponse(responseCode = "400", description = "錯誤的請求"),
            @ApiResponse(responseCode = "403", description = "權限不足"),
            @ApiResponse(responseCode = "500", description = "系統錯誤")
    })
    @PostMapping("/goods/add")
    public ResponseEntity<APIResponse> addGoods(@Valid @RequestBody Goods goods) {
        Goods addedGoods = goodsService.add(goods);
        APIResponse<Goods> apiResponse = APIResponse.<Goods>builder()
                .status("SUCCESS")
                .results(addedGoods)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "取得所有商品")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功"),
            @ApiResponse(responseCode = "400", description = "錯誤的請求"),
            @ApiResponse(responseCode = "403", description = "權限不足"),
            @ApiResponse(responseCode = "500", description = "系統錯誤")
    })
    @GetMapping("/goods")
    public ResponseEntity<APIResponse> getAllGoods() {
        List<Goods> goodsList = goodsService.findAll();
        APIResponse<List<Goods>> apiResponse = APIResponse.<List<Goods>>builder()
                .status("SUCCESS")
                .results(goodsList)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "取得指定商品")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功"),
            @ApiResponse(responseCode = "400", description = "錯誤的請求"),
            @ApiResponse(responseCode = "403", description = "權限不足"),
            @ApiResponse(responseCode = "500", description = "系統錯誤")
    })
    @GetMapping("/goods/{id}")
    public ResponseEntity<APIResponse> getGoodsById(@PathVariable("id") UUID id) {
        Goods goods = goodsService.findById(id);
        APIResponse<Goods> apiResponse = APIResponse.<Goods>builder()
                .status("SUCCESS")
                .results(goods)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "更新商品")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功"),
            @ApiResponse(responseCode = "400", description = "錯誤的請求"),
            @ApiResponse(responseCode = "403", description = "權限不足"),
            @ApiResponse(responseCode = "500", description = "系統錯誤")
    })
    @PutMapping("/goods/{id}")
    public ResponseEntity<APIResponse> updateGoods(@PathVariable("id") UUID id, @Valid @RequestBody Goods goods) {
        Goods updatedGoods = goodsService.update(id, goods.getName());
        APIResponse<Goods> apiResponse = APIResponse.<Goods>builder()
                .status("SUCCESS")
                .results(updatedGoods)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "刪除商品")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功"),
            @ApiResponse(responseCode = "400", description = "錯誤的請求"),
            @ApiResponse(responseCode = "403", description = "權限不足"),
            @ApiResponse(responseCode = "500", description = "系統錯誤")
    })
    @DeleteMapping("/goods/{id}")
    public ResponseEntity<APIResponse> deleteGoods(@PathVariable("id") UUID id) {
        Goods deletedGoods = goodsService.delete(id);
        APIResponse<Goods> apiResponse = APIResponse.<Goods>builder()
                .status("SUCCESS")
                .results(deletedGoods)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
