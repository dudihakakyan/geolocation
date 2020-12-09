package com.geolocation.geolocation.util;

import com.geolocation.geolocation.handler.exceptions.ExternalServiceException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class DistanceUtil
{
    public static String getDocumentId(String source, String destination)
    {
        return source + "_" + destination;
    }

    public static Integer getDistanceBetween(String source, String destination)
    {
        final String url = "https://www.merchak.org/route.json?stops=";
        final String param = source + "|" + destination;

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
