package at.spengergasse.IShop.presentation.api;

import at.spengergasse.IShop.presentation.exceptions.NoDataFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = { ApiControllerAdvice.class})
public class ApiControllerAdvice {

    @ExceptionHandler(NoDataFoundException.class)
    public HttpEntity<ApiError> handleNoDataFoundException(NoDataFoundException ndfEx){
        return ResponseEntity.badRequest().
                                body(ApiError.builder()
                                        .errorCode(4711)
                                        .message(ndfEx.getMessage())
                                        .httpStatus(HttpStatus.BAD_REQUEST.value())
                                        .documentationUrl("https://api.spengergasse.at/v1/customers/_id")
                                        .build());
    }
}
