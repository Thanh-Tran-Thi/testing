package com.smartosc.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AccountBankDTO {
    private Integer accountId;
    private Integer accountType;
    private Integer bankId;
    private String accountNumber;
    private Integer status;
    private String code;
    private String legalName;
    private String modifiedBy;
    private LocalDateTime modifiedDatetime;
}
