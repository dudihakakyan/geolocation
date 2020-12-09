package com.geolocation.geolocation.service;

import com.geolocation.geolocation.document.Distance;
import com.geolocation.geolocation.dto.request.AddDistanceRequestDTO;
import com.geolocation.geolocation.dto.response.AddDistanceResponseDTO;
import com.geolocation.geolocation.dto.response.DistanceResponseDTO;
import com.geolocation.geolocation.dto.response.PopularSearchResponseDTO;
import com.geolocation.geolocation.handler.exceptions.ApiParametersAreNotAlphaStringException;
import com.geolocation.geolocation.repository.DistanceRepository;
import com.geolocation.geolocation.util.DistanceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GeoLocationService
{
    @Autowired
    private DistanceRepository distanceRepository;

    public DistanceResponseDTO getDistanceBetween(String source, String destination)
    {
        if (!StringUtils.isAlpha(source) || !StringUtils.isAlpha(destination))
        {
            throw new ApiParametersAreNotAlphaStringException();
        }

        if (StringUtils.equalsIgnoreCase(source, destination))
        {
            return new DistanceResponseDTO(0);
        }

        final String id = Distance.generateId(source, destination);

        Optional<Distance> optDistance;
        try
        {
            optDistance = distanceRepository.findById(id);
        } catch (Exception e)
        {
            optDistance = Optional.empty();
        }

        return optDistance.map(distance -> {
            incrementHitsAsync(distance);
            return new DistanceResponseDTO(distance);
        }).orElseGet(() -> {
            Integer distanceBetween = DistanceUtil.getDistanceBetween(source, destination);

            // create Distance document
            Distance distance = new Distance()
                    .setId(id)
                    .setDistance(distanceBetween)
                    .setHits(1);

            insertToDbAsync(distance);

            // return DTO
            return new DistanceResponseDTO(distanceBetween);
        });
    }

    private void incrementHitsAsync(Distance distance)
    {
        CompletableFuture.runAsync(() -> {
            distance.incrementHits();
            distanceRepository.save(distance);
        }).exceptionally(e -> {
            log.error("Failed to update hits!", e);
            return null;
        });

    }

    public void insertToDbAsync(Distance distance)
    {
        CompletableFuture.runAsync(() -> distanceRepository.insert(distance)).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }

    public PopularSearchResponseDTO getPopularSearch()
    {
        Optional<Distance> optDistance = distanceRepository.findTopByOrderByHitsDesc();
        return optDistance.map(PopularSearchResponseDTO::new).orElse(null);
    }

    public AddDistanceResponseDTO addDistance(AddDistanceRequestDTO addDistanceRequestDTO)
    {
        String id = Distance.generateId(addDistanceRequestDTO.getSource(), addDistanceRequestDTO.getDestination());
        Optional<Distance> optDistance = findById(id);

        Distance distance;
        if (optDistance.isPresent())
        {
            distance = optDistance.get();
            distance.setDistance(addDistanceRequestDTO.getDistance());
        } else
        {
            distance = new Distance()
                    .setId(id)
                    .setDistance(addDistanceRequestDTO.getDistance())
                    .setHits(0);
        }

        distance = save(distance);
        return new AddDistanceResponseDTO(distance);
    }

    public Optional<Distance> findById(String id)
    {
        try
        {
            return distanceRepository.findById(id);
        } catch (DataAccessResourceFailureException e)
        {
            log.error("Failed to find Distance by id", e);
        }

        return Optional.empty();
    }

    public Distance save(Distance distance)
    {
        try
        {
            return distanceRepository.save(distance);
        } catch (DataAccessResourceFailureException e)
        {
            log.error("Failed to add distance!", e);
        }

        throw new RuntimeException();
    }
}
