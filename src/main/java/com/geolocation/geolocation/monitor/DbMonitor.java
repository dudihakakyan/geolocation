package com.geolocation.geolocation.monitor;

import com.mongodb.BasicDBObject;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbMonitor
{
    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean isDbUp()
    {
        Bson ping = new BasicDBObject("ping", "1");
        try
        {
            mongoTemplate.getDb().runCommand(ping);
            return true;
        } catch (Exception exp)
        {
            // Mongo DB is down..
        }

        return false;
    }
}
