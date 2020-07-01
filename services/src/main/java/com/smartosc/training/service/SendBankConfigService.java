package com.smartosc.training.service;


import com.smartosc.training.dto.BankDTO;
import com.smartosc.training.dto.BankDetailDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.request.UpdateBankReq;
import java.util.List;

/**
 * fres-parent
 *
 * @author longtcs
 * @created_at 21/04/2020 - 15:02
 * @created_by longtcs
 * @since 21/04/2020
 */
public interface SendBankConfigService {

    String NOT_FOUND = "No bank detail record found !!";
    String UPDATE_SEVER_ERROR = "Database error ! , can't update bank detail !!";

    public BankDetailDTO addBankDetail(BankDetailDTO bankDetailDTO);

    public List<BankDTO> getAllBankActive();

    public APIResponse<BankDetailDTO> editBankDetail (UpdateBankReq updateBankReq, Integer id);


}
