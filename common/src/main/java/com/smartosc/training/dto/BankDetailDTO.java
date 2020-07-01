package com.smartosc.training.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * Response structure bank data
 */
public class BankDetailDTO {

    private Integer id;

    private Integer bankId;

    private Integer status;
    private String modifiedBy;
    private LocalDateTime modifiedDatetime;
    private String createdBy;
    private LocalDateTime createdDatetime;
    private String type;
    private Integer value;

}
