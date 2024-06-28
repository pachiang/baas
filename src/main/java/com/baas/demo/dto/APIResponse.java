package com.baas.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class APIResponse<T> {
    private String status;
    private ErrorString error;
    private T results;
}
