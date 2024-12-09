package by.kiok.motorshow.exceptions;

import by.kiok.motorshow.dtos.ErrorDto;
import jakarta.validation.ValidationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    public static final Long REQUEST_FAIL_CODE = 1_000_000L;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationException.class, PropertyReferenceException.class, EntityException.class})
    public @ResponseBody ErrorDto handleExceptions(Throwable e) {

        return new ErrorDto(REQUEST_FAIL_CODE, e.getMessage());
    }
}
