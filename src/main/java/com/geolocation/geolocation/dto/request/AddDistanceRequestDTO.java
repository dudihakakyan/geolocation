package com.geolocation.geolocation.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class AddDistanceRequestDTO
{
    @Pattern(regexp="^[A-Za-z]+$",message = "must contains only letters")
    private String source;

    @Pattern(regexp="^[A-Za-z]+$",message = "must contains only letters")
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
        this.source = source.toLowerCase().trim();
    }

    public String getDestination()
    {
        return destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination.toLowerCase().trim();
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
