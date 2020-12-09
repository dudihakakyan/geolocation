package com.geolocation.geolocation.dto.response;


import com.geolocation.geolocation.document.Distance;

public class DistanceResponseDTO
{
    private Integer distance;

    public DistanceResponseDTO(Integer distance)
    {
        this.distance = distance;
    }

    public DistanceResponseDTO(Distance distance)
    {
        this.distance = distance.getDistance();
    }

    public Integer getDistance()
    {
        return distance;
    }

    public DistanceResponseDTO setDistance(Integer distance)
    {
        this.distance = distance;
        return this;
    }
}
