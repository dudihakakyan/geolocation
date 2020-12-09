package com.geolocation.geolocation.repository;

import com.geolocation.geolocation.document.Distance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DistanceRepository extends MongoRepository<Distance, String>
{
    Optional<Distance> findTopByOrderByHitsDesc();

    //     FIXME: Why it is not working?
//    @Query(value = "{_id:?0}, {distance:1}")
//    Optional<Distance> getDistanceById(String id);

//    @Query(value = "{_id:?0}, $inc: {hits:1}")
//    Optional<Distance> incrementHits(String id);
}
