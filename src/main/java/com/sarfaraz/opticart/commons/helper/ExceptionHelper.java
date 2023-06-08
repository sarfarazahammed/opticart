package com.sarfaraz.opticart.commons.helper;

import com.sarfaraz.opticart.commons.dto.ExceptionResponseDto;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.sarfaraz.opticart.commons.constants.RequestConstants.TRACE;

@Component
public class ExceptionHelper {

    private final boolean printStackTrace;

    public ExceptionHelper(@Value("${app.trace:false}") boolean printStackTrace) {
        this.printStackTrace = printStackTrace;
    }

    private static boolean isTraceOn(HttpServletRequest request) {
        String[] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }

    public ExceptionResponseDto getErrorResponse(HttpServletRequest request, Exception ex, HttpStatus status) {
        ExceptionResponseDto errorResponse = new ExceptionResponseDto(status, ex.getMessage(), ex.getClass().getSimpleName());
        if (printStackTrace && isTraceOn(request)) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(ex));
        }
        return errorResponse;
    }

    public ExceptionResponseDto getErrorResponse(HttpServletRequest request, Exception ex, String message, HttpStatus status) {
        ExceptionResponseDto errorResponse = new ExceptionResponseDto(status, message, ex.getClass().getSimpleName());
        if (printStackTrace && isTraceOn(request)) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(ex));
        }
        return errorResponse;
    }
}
