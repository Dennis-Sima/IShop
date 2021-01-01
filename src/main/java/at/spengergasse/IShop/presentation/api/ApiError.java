package at.spengergasse.IShop.presentation.api;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiError {
    private final Integer errorCode;
    private final Integer httpStatus;
    private final String message;
    private final String documentationUrl;
}
