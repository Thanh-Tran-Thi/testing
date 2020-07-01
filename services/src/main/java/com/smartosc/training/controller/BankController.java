package com.smartosc.training.controller;


import com.smartosc.training.dto.BankDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.exception.FieldDuplicateException;
import com.smartosc.training.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.smartosc.training.utility.AppConstants.DELETE_BANK_SUCCESS_MESSAGE;


@RestController
@RequestMapping("/banks")
@Slf4j
public class BankController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankController.class);

    private BankService bankService;

    @Autowired
    public BankController(final BankService bankService) {
        this.bankService = bankService;
    }

    /**
     * Create new bank
     *
     * @param bankDTO
     * @return response data
     */
    @PostMapping
    public ResponseEntity<Object> createBank(@Valid @RequestBody BankDTO bankDTO) {
        BankDTO responseData = null;
        try {
            LOGGER.info("Starting create new bank!");
            responseData = bankService.createBank(bankDTO);
            LOGGER.info("New bank already created! - {}", responseData);
        } catch (FieldDuplicateException ex) {
            throw ex;
        }

        if(responseData.getBankId() != null) {
            return ResponseEntity.ok(new APIResponse<>(
                    HttpStatus.OK.value(),
                    "Create new bank successfully!",
                    responseData,
                    null));
        } else {
            return new ResponseEntity<>(new APIResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Create new bank failed!",
                    responseData,
                    null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<APIResponse<Page<BankDTO>>> getAllBanks(
            @RequestParam(defaultValue = "", required = false) String searchValue,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "8", required = false) Integer size,
            @RequestParam(defaultValue = "bankId", required = false) String sortBy) {
        Page<BankDTO> banks = bankService.getAllBanks(searchValue, page, size, sortBy);
        APIResponse<Page<BankDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(banks);
        responseData.setMessage("get all banks successful");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<APIResponse<BankDTO>> updateBank(@RequestBody BankDTO bankDTO, @RequestParam(value = "bankId") Integer bankId) {
        BankDTO bankDTOUpdate = bankService.updateBank(bankDTO, bankId);
        APIResponse<BankDTO> responseData = new APIResponse<>();
        responseData.setData(bankDTOUpdate);
        return bankDTOUpdate != null ? ResponseEntity.ok(new APIResponse<>(
                HttpStatus.OK.value(), "Update bank successfully!", bankDTO, null))
                : new ResponseEntity<>(new APIResponse<>(HttpStatus.BAD_REQUEST.value(),
                "Update bank failed!", bankDTO, null), HttpStatus.BAD_REQUEST);
    }


    /**
     * Delete bank
     * @param id
     * @return
     * @author Duong NV
     * @since 21/04/2020
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBank(@PathVariable int id) {
        bankService.deleteBank(id);
        return ResponseEntity.ok(new APIResponse<>(HttpStatus.OK.value(), DELETE_BANK_SUCCESS_MESSAGE));
    }

    /**
     * created by Duong
     * find bank by id for update bank
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<BankDTO>> findBankById(@PathVariable("id") Integer id) {
        BankDTO bankDTO = bankService.findById(id);
        APIResponse<BankDTO> responseData = new APIResponse<>();
        responseData.setData(bankDTO);
        responseData.setStatus(bankDTO == null ? HttpStatus.NOT_FOUND.value() : HttpStatus.OK.value());
        responseData.setMessage(bankDTO == null ? "find bank fail" : "find bank success!");
        return new ResponseEntity<>(responseData, null, HttpStatus.OK);
    }


    @PostMapping("validate")
    public ResponseEntity<APIResponse<Boolean>> isDuplicateBank(@RequestBody BankDTO bankDTO) {
        Boolean result = bankService.isBankExist(bankDTO);
        APIResponse<Boolean> responseData = new APIResponse<>();
        responseData.setData(result);
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage(result ? "duplicate bank" : "No duplicate banks");
        return ResponseEntity.ok(responseData);
    }

}
