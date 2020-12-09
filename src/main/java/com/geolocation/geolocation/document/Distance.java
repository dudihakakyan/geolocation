package com.geolocation.geolocation.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Distances")
public class Distance
{
    @Id
    private String id; // source_dest
    private Integer distance;

    public String getId()
    {
        return id;
    }

    public Distance setId(String id)
    {
        this.id = id;
        return this;
    }

    public Integer getDistance()
    {
        return distance;
    }

    public Distance setDistance(Integer distance)
    {
        this.distance = distance;
        return this;
    }
}
