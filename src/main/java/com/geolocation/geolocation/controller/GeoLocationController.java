package com.geolocation.geolocation.controller;

import com.geolocation.geolocation.dto.DistanceResponseDTO;
import com.geolocation.geolocation.dto.PopularSearchResponseDTO;
import com.geolocation.geolocation.service.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class GeoLocationController
{
    @Autowired
    private GeoLocationService geoLocationService;

    @GetMapping("/distance")
    public ResponseEntity<?> getDistanceBetween(@RequestParam String source, @RequestParam String destination)
    {
        DistanceResponseDTO distanceBetween = geoLocationService.getDistanceBetween(source, destination);
        return ResponseEntity.ok(distanceBetween);
    }

    @GetMapping("/popularsearch")
    public ResponseEntity<?> getPopularSearch()
    {
        PopularSearchResponseDTO popularSearch = geoLocationService.getPopularSearch();
        return popularSearch == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(popularSearch);
    }
}
