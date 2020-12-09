package com.geolocation.geolocation.repository;

import com.geolocation.geolocation.document.Distance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DistanceRepository extends MongoRepository<Distance, String>
{
}
