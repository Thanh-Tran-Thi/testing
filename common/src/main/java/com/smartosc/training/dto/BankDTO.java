package com.smartosc.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author anhdt
 * @project fres-parent
 * @created_at 22/04/2020 - 3:21 PM
 * @created_by anhdt
 * @since 22/04/2020
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BankDTO extends BaseAuditDTO{
    @NotBlank(message = "Không được bỏ trống")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Mã phải gồm chữ cái viết hoa và số")
    private String code;
    private Integer bankId;

    @NotBlank(message = "Không được bỏ trống")
    private String legalName;

    @NotBlank(message = "Không được bỏ trống")
    @Pattern(regexp = "^[0-9]+$", message = "Đầu số thẻ chỉ được nhập số")
    private String prefixCard;

    private String shortName;

    private Integer status;

    @Override
    public String toString() {
        return "BankDTO{" +
                "bankId=" + bankId +
                ", code='" + code + '\'' +
                ", shortName='" + shortName + '\'' +
                ", legalName='" + legalName + '\'' +
                ", prefixCard='" + prefixCard + '\'' +
                ", status=" + status +
                '}';
    }

}
