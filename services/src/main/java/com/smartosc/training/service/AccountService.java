package com.smartosc.training.service;

import com.smartosc.training.dto.AccountBankDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.smartosc.training.dto.AccountDTO;
import com.smartosc.training.request.AccountReq;


/**
 * @author: ductd
 * @since: 29/4/2020
 * @version: 1.0
 */

@Service
public interface AccountService {
    Page<AccountBankDTO> findByAllAccountBankDTO(Integer page, Integer size, String sortBy, String searchValue);

    AccountDTO getAccountById(Integer id);

    void createAccount(AccountReq accountReq);

    void updateAccount(AccountReq accountReq);
}
