package com.smartosc.training.dto;

import lombok.Data;
@Data
public class AccountDTO extends BaseAuditDTO {
    private Integer accountId;
    private String accountNumber;
    private Integer accountType;
    private Integer bankId;
    private Integer status;
}
