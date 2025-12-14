package com.sharom.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        Map<String, Object> error = new HashMap<>();
        int status = 500;
        String message = exception.getMessage();

        // Наш кастомный BadRequestException
        if (exception instanceof BadRequestException) {
            BadRequestException bre = (BadRequestException) exception;
            status = bre.getStatus().getStatusCode();
            message = bre.getMessage();
            error.put("error", "Bad Request");
        }
        // Не найдено
        else if (exception instanceof jakarta.ws.rs.NotFoundException) {
            status = 404;
            message = "Not Found";
            error.put("error", "Not Found");
        }
        // Остальные ошибки
        else {
            error.put("error", "Internal Server Error");
        }

        error.put("status", status);
        error.put("message", message);


        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
