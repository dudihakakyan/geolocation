package com.geolocation.geolocation.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class AddDistanceRequestDTO
{
    @NotBlank
    private String source;

    @NotBlank
    private String destination;

    @Min(0)
    private Integer distance;

    public AddDistanceRequestDTO()
    {
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source.toLowerCase();
    }

    public String getDestination()
    {
        return destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination.toLowerCase();
    }

    public Integer getDistance()
    {
        return distance;
    }

    public void setDistance(Integer distance)
    {
        this.distance = distance;
    }
}
