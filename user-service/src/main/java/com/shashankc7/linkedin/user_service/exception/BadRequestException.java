package com.shashankc7.linkedin.user_service.exception;

public class BadRequestException extends RuntimeException
{
    public BadRequestException(String message)
    {
        super(message);
    }
}
