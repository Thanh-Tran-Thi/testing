package com.smartosc.training.controller;


import com.smartosc.training.dto.BankDTO;
import com.smartosc.training.dto.BankDetailDTO;
import com.smartosc.training.dto.BankSenderConfigDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.request.UpdateBankReq;
import com.smartosc.training.service.BankSenderConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * fres-parent
 *
 * @author longtcs
 * @created_at 21/04/2020 - 15:02
 * @created_by longtcs
 * @since 21/04/2020
 */
@RestController
@RequestMapping("/sendbankconfig")
public class SendBankConfigController {

    @Autowired
    private BankSenderConfigurationService bankSenderConfigurationService;


    // API return list send bank confidguration
    /**
     *
     * @return
     */
    @GetMapping("/bankSenderConfig")
    public ResponseEntity<Object> getAllSendBankConfiguration() {
        List<BankSenderConfigDTO> listBankSenderConfig =
                bankSenderConfigurationService.getAllBankSenderConfig();

        APIResponse<List<BankSenderConfigDTO>> responseData = new APIResponse<>();

        // Check if list is null
        if (listBankSenderConfig != null) {
            responseData.setStatus(HttpStatus.OK.value());
            responseData.setData(listBankSenderConfig);
            responseData.setMessage("Get all BankSenderConfiguration successfully");
        } else {
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setMessage("Get all BankSenderConfiguration failed");
        }

        return ResponseEntity.ok(responseData);

    }

    // API edit send bank config
    @PutMapping("/{id}")
    public ResponseEntity<?> editBankDetail(@RequestBody UpdateBankReq updateBankReq, @PathVariable("id") Integer id) {
        BankDetailDTO result = bankSenderConfigurationService.editBankDetail(updateBankReq, id);
        APIResponse<BankDetailDTO> response = new APIResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setData(result);
        response.setMessage("Update successfull !!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/configbanksend")
    public ResponseEntity<Object> addBankDetail(@RequestBody BankDetailDTO bankDetailDTO) {
        try {
            BankDetailDTO result = bankSenderConfigurationService.addBankDetail(bankDetailDTO);
            APIResponse<BankDetailDTO> response = new APIResponse<>();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Add successful");
            response.setData(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return
     */

    @GetMapping("/getAllBank")
    public ResponseEntity<?> getAllBankActive() {
        List<BankDTO> bankDTOS = bankSenderConfigurationService.getAllBankActive();
        APIResponse<List<BankDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());

        // Check if list is null
        if (bankDTOS != null) {
            responseData.setData(bankDTOS);
            responseData.setMessage("Get all bankDTOS successfully");
        } else {
            responseData.setMessage("Get all bankDTOS failed");
        }
        return ResponseEntity.ok(responseData);
    }
}
