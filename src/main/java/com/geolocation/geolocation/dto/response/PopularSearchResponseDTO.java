package com.geolocation.geolocation.dto.response;

import com.geolocation.geolocation.document.Distance;

public class PopularSearchResponseDTO
{
    private String source;
    private String destination;
    private Integer hits;

    public PopularSearchResponseDTO(Distance distance)
    {
        this.source = distance.getSource();
        this.destination = distance.getDestination();
        this.hits = distance.getHits();
    }

    public PopularSearchResponseDTO()
    {
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getDestination()
    {
        return destination;
    }

    public void setTarget(String destination)
    {
        this.destination = destination;
    }

    public Integer getHits()
    {
        return hits;
    }

    public void setHits(Integer hits)
    {
        this.hits = hits;
    }
}
