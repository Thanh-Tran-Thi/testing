package com.smartosc.training.service;

import com.smartosc.training.dto.BankDTO;
import com.smartosc.training.dto.BankDetailDTO;
import com.smartosc.training.dto.BankSenderConfigDTO;
import com.smartosc.training.entity.APIResponse;
import org.springframework.stereotype.Service;

import com.smartosc.training.request.UpdateBankReq;

import java.util.List;

/**
 * @author longtcs on 20/04/2020
 * @project fres-parent
 */
@Service
public interface BankSenderConfigurationService {

    List<BankSenderConfigDTO> getAllBankSenderConfig();

    BankDetailDTO addBankDetail(BankDetailDTO bankDetailDTO);

    List<BankDTO> getAllBankActive();

    BankDetailDTO editBankDetail (UpdateBankReq updateBankReq, Integer id);

}
