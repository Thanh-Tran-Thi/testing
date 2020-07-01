package com.smartosc.training.controller;

import com.smartosc.training.dto.BankDirectConfigDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.repositories.BankDetailRepository;
import com.smartosc.training.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author duongnch
 * @since 20/04/2020
 */
@Slf4j
@RestController
@RequestMapping("/bankDirect")
public class BankConfigController {
    @Autowired
    BankDetailRepository bankDetailRepository;
    @Autowired
    BankService bankService;

    // API return list send bank configuration
    @GetMapping()
    public ResponseEntity<APIResponse<Page<BankDirectConfigDTO>>> getAllDirectBank
    ( @RequestParam(defaultValue = "", required = false) String searchValue,
      @RequestParam(defaultValue = "0", required = false) Integer defaultPage,
      @RequestParam(defaultValue = "5", required = false) Integer pageSize,
      @RequestParam(defaultValue = "bankId", required = false) String sortBy) {

        log.info("Find All direct banks");
        log.trace(String.format("search key: %s; page: %d; size: %d, sort by: %s ", searchValue, defaultPage, pageSize, sortBy));

        Pageable pageable = PageRequest.of(defaultPage, pageSize, Sort.by(Sort.Direction.DESC,sortBy));
        Page<BankDirectConfigDTO> bankDirectConfigDTOList = bankService.getAllDirectBankConfiguration(pageable, searchValue);
        APIResponse<Page<BankDirectConfigDTO>> responseData = new APIResponse<>();
        responseData.setData(bankDirectConfigDTOList);
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage("Get all DirectBankConfiguration successfully");
        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }

}



