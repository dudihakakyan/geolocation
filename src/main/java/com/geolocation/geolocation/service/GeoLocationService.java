package com.geolocation.geolocation.service;

import com.geolocation.geolocation.document.Distance;
import com.geolocation.geolocation.dto.DistanceResponseDTO;
import com.geolocation.geolocation.handler.exceptions.ApiParametersAreNotAlphaStringException;
import com.geolocation.geolocation.repository.DistanceRepository;
import com.geolocation.geolocation.util.DistanceUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class GeoLocationService
{
    @Autowired
    private DistanceRepository distanceRepository;

    public DistanceResponseDTO getDistanceBetween(String source, String destination)
    {
        if (!StringUtils.isAlpha(source) || !StringUtils.isAlpha(destination)) {
            throw new ApiParametersAreNotAlphaStringException();
        }

        final String src = source.toLowerCase();
        final String dst = destination.toLowerCase();

        if (src.equals(dst)) {
            return new DistanceResponseDTO(0);
        }

        // Sort
        final String id = src.compareTo(dst) < 0 ? DistanceUtil.getDocumentId(src, dst) : DistanceUtil.getDocumentId(dst, src);

        Optional<Distance> optDistance;
        try
        {
            optDistance = distanceRepository.findById(id);
        } catch (Exception e) {
            optDistance = Optional.empty();
        }

        return optDistance.map(DistanceResponseDTO::new).orElseGet(() -> {
            Integer distanceBetween = DistanceUtil.getDistanceBetween(src, dst);

            // create Distance document
            Distance distance = new Distance()
                    .setId(id)
                    .setDistance(distanceBetween);

            insertToDbAsync(distance);

            // return DTO
            return new DistanceResponseDTO(distanceBetween);
        });
    }

    public void insertToDbAsync(Distance distance) {
        CompletableFuture.runAsync(() -> distanceRepository.insert(distance)).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }
}
