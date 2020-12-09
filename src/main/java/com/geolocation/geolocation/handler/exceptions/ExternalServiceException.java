package com.geolocation.geolocation.handler.exceptions;

public class ExternalServiceException extends RuntimeException
{
    @Override
    public String getLocalizedMessage()
    {
        return "source and destination params must contains only letters!";
    }
}
