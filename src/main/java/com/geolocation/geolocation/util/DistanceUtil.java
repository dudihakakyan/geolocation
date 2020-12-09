package com.geolocation.geolocation.util;

import com.geolocation.geolocation.handler.exceptions.ExternalServiceException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class DistanceUtil
{
    public static Integer getDistanceBetween(String source, String destination)
    {
        if (StringUtils.isAnyBlank(source, destination)) {
            log.error("source & destination must not be blank. source: " + source + "destination: " + destination);
            return 0;
        }

        final String url = "https://www.merchak.org/route.json?stops=";
        final String param = source.toLowerCase() + "|" + destination.toLowerCase();

        RestTemplate restTemplate = new RestTemplate();

        try
        {
            ResponseEntity<String> response = restTemplate.getForEntity(url + param, String.class);

            if (HttpStatus.OK == response.getStatusCode())
            {
                JsonObject convertedObject = new Gson().fromJson(response.getBody(), JsonObject.class);
                return (int) convertedObject.get("distance").getAsDouble();
            }
        } catch (Exception ignored)
        {
        }

        throw new ExternalServiceException();
    }

}
