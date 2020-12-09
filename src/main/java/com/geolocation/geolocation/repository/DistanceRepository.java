package com.geolocation.geolocation.repository;

import com.geolocation.geolocation.document.Distance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface DistanceRepository extends MongoRepository<Distance, String>
{
    Optional<Distance> findTopByOrderByHitsDesc();

    @Query(value = "{_id: ?0}, {distance: 1}")
    Optional<Distance> getDistanceById(String id);

    @Query(value = "{_id: ?0}, $inc: {hits: 1}")
    Optional<Distance> incrementHits(String id);
}
