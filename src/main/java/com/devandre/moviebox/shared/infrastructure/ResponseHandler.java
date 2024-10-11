package com.devandre.moviebox.shared.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(HttpStatus status, Object data, boolean result) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date());
        map.put("status", status.value());
        map.put("result", result);
        map.put("data", data instanceof List<?> ? data : List.of(data));

        try {
            return new ResponseEntity<>(map, status);
        } catch (Exception exception) {
            log.error("Error while building response", exception);
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("result", false);
            map.put("message", exception.getMessage());
            map.put("data", null);
            return new ResponseEntity<>(map, status);
        }
    }
}
