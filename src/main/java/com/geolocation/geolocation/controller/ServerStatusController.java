package com.geolocation.geolocation.controller;

import com.geolocation.geolocation.monitor.DbMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ServerStatusController
{
    @Autowired
    private DbMonitor dbMonitor;

    @GetMapping("/hello")
    public ResponseEntity<?> isAvailable()
    {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/health")
    public ResponseEntity<?> isDbConnectinActive()
    {
        return dbMonitor.isDbUp() ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("DB is not available");
    }
}
