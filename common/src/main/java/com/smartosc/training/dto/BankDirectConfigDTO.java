package com.smartosc.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * fres-parent
 *
 * @author duongnch
 * @created_at 22/04/2020 - 10:36 AM
 * @created_by duongnch
 * @since 22/04/2020
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankDirectConfigDTO{
    private  int id;
    private int bankId;
    String code;
    String legalName;
    int status;
    String modifiedBy;
    LocalDateTime modifiedDatetime;
}
