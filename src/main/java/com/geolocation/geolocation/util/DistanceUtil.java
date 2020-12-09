package com.geolocation.geolocation.util;

import com.geolocation.geolocation.handler.exceptions.ExternalServiceException;
import com.geolocation.geolocation.handler.exceptions.InvalidSourceOrDestinationException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
        final String url = "https://www.merchak.org/route.json?stops=";
        final String param = source.toLowerCase() + "|" + destination.toLowerCase();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url + param, String.class);

        if (HttpStatus.OK == response.getStatusCode())
        {
            JsonObject convertedObject = new Gson().fromJson(response.getBody(), JsonObject.class);
            validateResponse(convertedObject);
            try
            {
                return (int) convertedObject.get("distance").getAsDouble();
            } catch (Exception ignored)
            {
            }
        }

        throw new ExternalServiceException();
    }

    private static void validateResponse(JsonObject jsonObject)
    {
        JsonArray stopsArr = jsonObject.get("stops").getAsJsonArray();
        JsonObject source = stopsArr.get(0).getAsJsonObject();
        JsonObject destination = stopsArr.get(1).getAsJsonObject();

        if (StringUtils.equalsAnyIgnoreCase("invalid",
                source.get("type").getAsString(), destination.get("type").getAsString()))
        {
            throw new InvalidSourceOrDestinationException();
        }
    }
}
