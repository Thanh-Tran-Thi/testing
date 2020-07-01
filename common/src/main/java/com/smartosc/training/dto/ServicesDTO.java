package com.smartosc.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * fres-parent
 *
 * @author dv-trong
 * @created_at 17/04/2020 - 17:29
 * @created_by dv-trong
 * @since 17/04/2020
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicesDTO {

    private Integer serviceId;
    @NotBlank
    private String code;
    @NotBlank
    private String name;

    private Integer transStatus;

    private Integer status;

    private String modifiedBy;

    private String modifiedDateTime;
}
