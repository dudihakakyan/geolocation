package com.geolocation.geolocation.handler.exceptions;

public class InvalidSourceOrDestinationException extends RuntimeException
{
    @Override
    public String getLocalizedMessage()
    {
        return "Invalid 'source' and/or 'destination'";
    }
}
