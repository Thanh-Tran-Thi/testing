package com.smartosc.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * fres-parent
 *
 * @author longtcs
 * @created_at 21/04/2020 - 15:02
 * @created_by longtcs
 * @since 21/04/2020
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateRecordException extends RuntimeException {
    public DuplicateRecordException(String message) {
        super(message);
    }
}
