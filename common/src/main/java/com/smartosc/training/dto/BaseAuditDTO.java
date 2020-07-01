package com.smartosc.training.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: ductd
 * @since: 29/4/2020
 * @version: 1.0
 */
@Data
public abstract class BaseAuditDTO {
    private String modifiedBy;

    private LocalDateTime modifiedDatetime;

    private LocalDateTime createdDatetime;

    private String createdBy;
}
