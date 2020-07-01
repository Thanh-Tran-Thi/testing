package com.smartosc.training.request;

import lombok.Data;

/**
 * fres-parent
 *
 * @author Tung lam
 * @created_at 23/04/2020 - 11:20
 * @created_by Tung lam
 * @since 23/04/2020
 */
@Data
public class AddSendBankDetailReq {
    private int bankId;
    private int value;
    private int status;
}
