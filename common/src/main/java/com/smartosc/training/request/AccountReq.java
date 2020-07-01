package com.smartosc.training.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccountReq {

    private Integer accountType;

    private Integer bankId;

    @Pattern(regexp = "\\d+$", message = "accountNumber include only numbers!")
    private String accountNumber;

    private Integer status;
}
