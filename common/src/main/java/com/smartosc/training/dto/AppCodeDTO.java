package com.smartosc.training.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Created by DucTD on 17/4/2020
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppCodeDTO {

    private Integer appCodeId;
    private String code;
    private String description;
    private Integer status;
    private String createdBy;
    private String modifiedBy;
    private LocalDateTime createdDatetime;
    private LocalDateTime modifiedDatetime;

    public AppCodeDTO(String code, String description, Integer status, String createdBy, String modifiedBy, LocalDateTime createdDatetime, LocalDateTime modifiedDatetime) {
        this.code = code;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdDatetime = createdDatetime;
        this.modifiedDatetime = modifiedDatetime;
    }
}
