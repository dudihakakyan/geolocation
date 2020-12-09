package com.geolocation.geolocation.repository;

import com.geolocation.geolocation.document.Distance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DistanceRepository extends MongoRepository<Distance, String>
{
    Optional<Distance> findTopByOrderByHitsDesc();

}
