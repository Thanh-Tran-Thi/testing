package com.smartosc.training.controller;

import com.smartosc.training.dto.AccountBankDTO;
import com.smartosc.training.dto.AccountDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.request.AccountReq;
import com.smartosc.training.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.smartosc.training.utility.AppConstants.CREATE_ACCOUNT_SUCCESS_MESSAGE;


@RestController
@RequestMapping("/account-banks")
public class AccountBankController {

    @Autowired
    private AccountService accountService;

    /**
     * @author hienvv
     * @since 4/5/2002
     * @param page
     * @param size
     * @param sortBy
     * @param searchValue
     * @return
     */
    @GetMapping
    public ResponseEntity<APIResponse<Page<AccountBankDTO>>> getListAllAccountBank(
            @RequestParam(defaultValue = "0",required = false) Integer page,
            @RequestParam(defaultValue = "8",required = false) Integer size,
            @RequestParam(defaultValue = "accountId",required = false) String sortBy,
            @RequestParam(defaultValue = "",required = false) String searchValue){

        Page<AccountBankDTO> result = accountService.findByAllAccountBankDTO(page, size, sortBy, searchValue);
        APIResponse<Page<AccountBankDTO>> responseData = new APIResponse<>();
        responseData.setData(result);
        responseData.setMessage("Get list account bank successfully!");
        responseData.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(responseData);

    }

    /**
     * get account by id
     * @author Duong NV
     * @since 04/05/2020
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<AccountDTO>> getAccountById(@PathVariable("id") Integer id){
        APIResponse<AccountDTO> response = new APIResponse<>();
        response.setData(accountService.getAccountById(id));
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * create account
     * @author Duong NV
     * @since 04/05/2020
     * @return
     */
    @PostMapping("")
    public ResponseEntity<Object> createAccount(@Valid @RequestBody AccountReq accountReq){
        accountService.createAccount(accountReq);
        return ResponseEntity.ok(new APIResponse<>(HttpStatus.OK.value(), CREATE_ACCOUNT_SUCCESS_MESSAGE));
    }

    /**
     * update account
     * @author Duong NV
     * @since 04/05/2020
     * @return
     */
    @PutMapping("")
    public ResponseEntity<Object> updateAccount(@Valid @RequestBody AccountReq accountReq){
        accountService.updateAccount(accountReq);
        return ResponseEntity.ok(new APIResponse<>(HttpStatus.OK.value(), CREATE_ACCOUNT_SUCCESS_MESSAGE));
    }

}
