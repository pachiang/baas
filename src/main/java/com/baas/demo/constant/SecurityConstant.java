package com.baas.demo.constant;

public class SecurityConstant {
    public static final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
    public static final String JWT_HEADER = "Authorization";
    public static final String[] WHITELIST_URLS = {
            "/auth/**",
            "/context-path/**",
            "/swagger/**",
            "/swagger-ui/**",
            "/v3/**"
    };
}