package com.geolocation.geolocation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ServerStatusController
{
    @GetMapping("/hello")
    public ResponseEntity<?> isAvailable() {
        return ResponseEntity.ok().build();
    }

}
