package com.smartosc.training.controller;

import com.smartosc.training.dto.BankDTO;

import com.smartosc.training.dto.BankDetailDTO;
import com.smartosc.training.dto.BankSenderConfigDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.request.UpdateBankReq;
import com.smartosc.training.service.RestService;
import com.smartosc.training.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Canh Gia Nguyen on 20-Apr-20
 * @project fres-parent
 */
@Controller
@RequestMapping("/")
public class BankConfigurationController {
    @Autowired
    private JWTUtil jwtTokenUtil;

    @Value("${api.url}")
    private String url;

    @Value("${prefix.bankConfig}")
    private String prefixBankConfigUrl;

    @Value("${prefix.bankSenderConfig}")
    private String prefixBankSenderConfig;

    @Autowired
    private RestService restService;



    // Render send bank configuration template
    @GetMapping("/bankSenderConfig")
    public String senBankConfiguration(Model model) {

        APIResponse<List<BankSenderConfigDTO>> responseData = restService.execute(url + prefixBankConfigUrl + prefixBankSenderConfig,
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<List<BankSenderConfigDTO>>>() {
                });

        List<BankSenderConfigDTO> bankSenderConfigDTOList = null;

        // Check if API is called successfully
        if (responseData.getStatus() == HttpStatus.OK.value()) {
            bankSenderConfigDTOList = responseData.getData();
        }
        model.addAttribute("listSendBankConfiguration", bankSenderConfigDTOList);
        model.addAttribute("banks", getAllBank());
        return "send-bank-configuration";
    }


    /**
     * @return
     * @author Tung Lam
     */

    private List<BankDTO> getAllBank() {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<BankDTO> bankDTOList = restService.execute(url + "sendbankconfig/getAllBank",
                HttpMethod.GET,
                httpHeaders,
                null,
                new ParameterizedTypeReference<APIResponse<List<BankDTO>>>() {
                }).getData();
        return bankDTOList;
    }


    /**
     * @param bankDetailDTO
     * @return
     * @author Tung Lam
     */

    @PostMapping("/save")
    @ResponseBody
    public BankDetailDTO save(@RequestBody BankDetailDTO bankDetailDTO) {

        String authToken = jwtTokenUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        header.setContentType(MediaType.APPLICATION_JSON);
        return restService.execute(
                url + "/sendbankconfig/configbanksend",
                HttpMethod.POST,
                header,
                bankDetailDTO,
                new ParameterizedTypeReference<APIResponse<BankDetailDTO>>() {
                }).getData();
    }

    // API edit Send Bank Config
    @PutMapping("/send-bank-configuration/{id}")
    public ResponseEntity<?> editSendBank(@RequestBody UpdateBankReq updateBankReq, @PathVariable("id") Integer id) {

        String authToken = jwtTokenUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        header.setContentType(MediaType.APPLICATION_JSON);

        APIResponse<BankDetailDTO> responseData = restService.execute(url + prefixBankConfigUrl + "/" + id,
                HttpMethod.PUT,
                header,
                updateBankReq,
                new ParameterizedTypeReference<APIResponse<BankDetailDTO>>() {
                });
        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }


}
