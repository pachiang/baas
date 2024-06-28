package com.baas.demo.handler;

import com.baas.demo.dto.APIResponse;
import com.baas.demo.dto.ErrorString;
import com.baas.demo.exception.GoodsNotFoundException;
import com.baas.demo.exception.InvalidAccountOrPasswordException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import io.jsonwebtoken.security.SecurityException;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidAccountOrPasswordException.class)
    public ResponseEntity<APIResponse> handleInvalidAccountOrPasswordException(InvalidAccountOrPasswordException ex) {
        log.error("Get error named " + ex.getClass().getSimpleName() + ": ", ex);
        return errorResponseBuilder(HttpStatus.FORBIDDEN, ErrorString.帳號密碼錯誤);
    }
    @ExceptionHandler(GoodsNotFoundException.class)
    public ResponseEntity<APIResponse> handleGoodsNotFoundException(GoodsNotFoundException ex) {
        log.error("Get error named " + ex.getClass().getSimpleName() + ": ", ex);
        return errorResponseBuilder(HttpStatus.BAD_REQUEST, ErrorString.錯誤的請求);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<APIResponse> handleDataAccessException(DataAccessException ex) {
        log.error("Get error named " + ex.getClass().getSimpleName() + ": ", ex);
        return errorResponseBuilder(HttpStatus.BAD_REQUEST, ErrorString.錯誤的請求);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<APIResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        log.error("Get error named " + ex.getClass().getSimpleName() + ": ", ex);
        return errorResponseBuilder(HttpStatus.UNAUTHORIZED, ErrorString.權限不足);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<APIResponse> handleSignatureException(SignatureException ex) {
        log.error("Get error named " + ex.getClass().getSimpleName() + ": ", ex);
        return errorResponseBuilder(HttpStatus.UNAUTHORIZED, ErrorString.權限不足);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<APIResponse> handleMalformedJwtException(MalformedJwtException ex) {
        log.error("Get error named " + ex.getClass().getSimpleName() + ": ", ex);
        return errorResponseBuilder(HttpStatus.UNAUTHORIZED, ErrorString.權限不足);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Get error named " + ex.getClass().getSimpleName() + ": ", ex);
        return errorResponseBuilder(HttpStatus.UNAUTHORIZED, ErrorString.權限不足);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse> handleBadCredentialsException(AccessDeniedException ex) {
        log.error("Get error named " + ex.getClass().getSimpleName() + ": ", ex);
        return errorResponseBuilder(HttpStatus.UNAUTHORIZED, ErrorString.權限不足);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIResponse> handleAuthenticationException(AuthenticationException ex) {
        log.error("Get error named " + ex.getClass().getSimpleName() + ": ", ex);
        return errorResponseBuilder(HttpStatus.UNAUTHORIZED, ErrorString.權限不足);
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<APIResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        log.error("Get error named " + ex.getClass().getSimpleName() + ": ", ex);
        return errorResponseBuilder(HttpStatus.NOT_FOUND, ErrorString.找不到資源);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<APIResponse> handleSecurityException(Exception ex) {
        log.error("Get error named " + ex.getClass().getSimpleName() + ": ", ex);
        return errorResponseBuilder(HttpStatus.UNAUTHORIZED, ErrorString.權限不足);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleGeneralException(Exception ex) {
        log.error("Get error named " + ex.getClass().getSimpleName() + ": ", ex);
        return errorResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ErrorString.系統錯誤);
    }

    private ResponseEntity<APIResponse> errorResponseBuilder (HttpStatus httpStatus, ErrorString errorString) {
        APIResponse apiResponse = APIResponse.builder()
                .status("FAILED")
                .error(errorString)
                .build();
        return ResponseEntity.status(httpStatus).body(apiResponse);
    }
}

