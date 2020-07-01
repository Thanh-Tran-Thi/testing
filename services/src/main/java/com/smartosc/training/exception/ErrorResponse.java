package com.smartosc.training.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * fres-parent
 *
 * @author Canh Gia Nguyen
 * @created_at 21/04/2020 - 11:01 AM
 * @created_by Canh Gia Nguyen
 * @since 21/04/2020
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private HttpStatus status;
    private String message;
}
