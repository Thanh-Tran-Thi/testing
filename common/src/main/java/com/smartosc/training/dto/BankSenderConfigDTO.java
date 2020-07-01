package com.smartosc.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * fres-parent
 *
 * @author Canh Gia Nguyen
 * @created_at 21/04/2020 - 10:14 AM
 * @created_by Canh Gia Nguyen
 * @since 21/04/2020
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankSenderConfigDTO {
    int id;
    String code;
    String legalName;
    int value;
    int status;
    String modifiedBy;
    LocalDateTime modifiedDateTime;
}
