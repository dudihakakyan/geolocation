package com.geolocation.geolocation.dto;

import com.geolocation.geolocation.document.Distance;

public class PopularSearchResponseDTO
{
    private String source;
    private String target;
    private Integer hits;

    public PopularSearchResponseDTO(Distance distance)
    {
        this.source = distance.getSource();
        this.target = distance.getDestination();
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

    public String getTarget()
    {
        return target;
    }

    public void setTarget(String target)
    {
        this.target = target;
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
