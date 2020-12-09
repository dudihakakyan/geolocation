package com.geolocation.geolocation.document;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Distances")
public class Distance
{
    private static String SOURCE_DEST_SEPARATOR = "_";

    @Id
    private String id; // source_dest
    private Integer distance;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Integer hits;

    public Distance()
    {
        this.hits = 1;
    }

    public String getId()
    {
        return id;
    }

    public Distance setId(String id) {
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

    public Integer getHits()
    {
        return hits;
    }

    public Distance setHits(Integer hits)
    {
        this.hits = hits;
        return this;
    }

    @Transient
    public Distance incrementHits() {
        setHits(hits == null ? 1 : hits + 1);
        return this;
    }

    @Transient
    public String getSource() {
       return id == null ? null : id.split(SOURCE_DEST_SEPARATOR)[0];

    }

    @Transient
    public String getDestination() {
       return id == null ? null : id.split(SOURCE_DEST_SEPARATOR)[1];
    }

    @Transient
    public static String generateId(String source, String destination) {
        if (StringUtils.isAnyBlank(source, destination)) {
           return null;
        }

        final String src = source.toLowerCase();
        final String dst = destination.toLowerCase();

        return src.compareTo(dst) < 0 ? src + SOURCE_DEST_SEPARATOR + destination : destination + SOURCE_DEST_SEPARATOR + src;
    }
}
