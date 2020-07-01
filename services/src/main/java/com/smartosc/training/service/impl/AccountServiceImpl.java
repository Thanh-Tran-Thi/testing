package com.smartosc.training.service.impl;

import com.smartosc.training.dto.AccountBankDTO;
import com.smartosc.training.repositories.AccountRepository;
import com.smartosc.training.dto.AccountDTO;
import com.smartosc.training.entity.Account;
import com.smartosc.training.exception.DuplicateRecordException;
import com.smartosc.training.exception.InternalServerException;
import com.smartosc.training.exception.NotFoundException;
import com.smartosc.training.request.AccountReq;
import com.smartosc.training.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Component;


import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.smartosc.training.utility.AppConstants.*;

/**
 * @author: ductd
 * @since: 29/4/2020
 * @version: 1.0
 */
@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a");

    /**
     * get list all
     *
     * @author:hienvv
     * @since 4/5/2020
     * @param page
     * @param size
     * @param sortBy
     * @param searchValue
     * @return
     */
    @Override
    public Page<AccountBankDTO> findByAllAccountBankDTO(Integer page, Integer size, String sortBy, String searchValue) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,sortBy));
        return accountRepository.findByAllAccountBankDTO(searchValue,pageable);

    }

    /**
     * get account by id
     * @author Duong NV
     * @since 04/05/2020
     * @return
     */
    @Override
    public AccountDTO getAccountById(Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        if(!account.isPresent())
            throw new NotFoundException(EXCEPTION_ACCOUNT_NOT_FOUND);
        return modelMapper.map(account.get(), AccountDTO.class);
    }

    /**
     * create account
     * @author Duong NV
     * @since 04/05/2020
     * @return
     */
    @Override
    public void createAccount(AccountReq accountReq) {

        if (accountRepository.findByAccountNumber(accountReq.getAccountNumber()).isPresent()) {
            throw new DuplicateRecordException(EXCEPTION_ACCOUNT_DUPLICATE);
        }
        try {
            Account account = setData(new Account(), accountReq);
            account.setAccountNumber(accountReq.getAccountNumber());
            account.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            accountRepository.save(account);
        }catch (Exception e){
            throw new InternalServerException(EXCEPTION_ACCOUNT_INTERNAL_SERVER);
        }

    }

    /**
     * update account
     * @author Duong NV
     * @since 04/05/2020
     * @return
     */
    @Override
    public void updateAccount(AccountReq accountReq) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountReq.getAccountNumber());
        if (!optionalAccount.isPresent()) {
            throw new NotFoundException(EXCEPTION_ACCOUNT_NOT_FOUND);
        }
        try {
            accountRepository.save(setData(optionalAccount.get(), accountReq));
        }catch (Exception e){
            throw new InternalServerException(EXCEPTION_ACCOUNT_INTERNAL_SERVER);
        }
    }

    /**
     * set data to account
     * @author Duong NV
     * @since 04/05/2020
     * @return
     */
    public Account setData(Account account, AccountReq accountReq){
        account.setBankId(accountReq.getBankId());
        account.setStatus(accountReq.getStatus());
        account.setAccountType(accountReq.getAccountType());
        account.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return account;
    }

}
