package com.geolocation.geolocation.handler.exceptions;

public class ApiParametersAreNotAlphaStringException extends RuntimeException
{
    @Override
    public String getLocalizedMessage()
    {
        return "'source' and 'destination' params must contain only letters";
    }
}
