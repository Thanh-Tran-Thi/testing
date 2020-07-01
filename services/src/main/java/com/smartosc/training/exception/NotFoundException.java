package com.smartosc.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * fres-parent
 *
 * @author Canh Gia Nguyen
 * @created_at 21/04/2020 - 11:02 AM
 * @created_by Canh Gia Nguyen
 * @since 21/04/2020
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
