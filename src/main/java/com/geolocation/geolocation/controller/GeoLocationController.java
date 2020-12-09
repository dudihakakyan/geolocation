package com.geolocation.geolocation.controller;

import com.geolocation.geolocation.dto.request.AddDistanceRequestDTO;
import com.geolocation.geolocation.dto.response.AddDistanceResponseDTO;
import com.geolocation.geolocation.dto.response.DistanceResponseDTO;
import com.geolocation.geolocation.dto.response.PopularSearchResponseDTO;
import com.geolocation.geolocation.service.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;


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

    @PostMapping("/distance")
    public ResponseEntity<?> addDistance(@RequestBody @Valid AddDistanceRequestDTO addDistanceRequestDTO) {
        AddDistanceResponseDTO addDistanceResponseDTO = geoLocationService.addDistance(addDistanceRequestDTO);
        return ResponseEntity.created(URI.create("")).body(addDistanceResponseDTO);
    }
}
