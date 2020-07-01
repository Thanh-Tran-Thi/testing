package com.smartosc.training.controller;

import com.smartosc.training.dto.BankDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.service.RestService;
import com.smartosc.training.utils.JWTUtil;
import com.smartosc.training.utils.RestPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


/**
 * fres-parent
 *
 * @author thuynt
 * @created_at 20/04/2020 - 10:16 AM
 * @created_by thuynt
 * @since 20/04/2020
 */

@Controller
@RequestMapping(value = "/banks")
public class BankController {

    @Autowired
    private RestService restService;

    @Value("${api.url}")
    private String url;

    @Value("${prefix.bank}")
    private String prefixUrl;


    @GetMapping
    public String getAllBankPage(Model model,
                                 @RequestParam(defaultValue = "", required = false) String searchValue,
                                 @RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                 @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                 @RequestParam(defaultValue = "bankId", required = false) String sortBy) {
        APIResponse<RestPageImpl<BankDTO>> responseData = getAllBanks(searchValue, pageNo, pageSize, sortBy);
        RestPageImpl<BankDTO> bank = null;
        if (responseData.getStatus() == HttpStatus.OK.value()) {
            bank = responseData.getData();
        }
        model.addAttribute("data", bank);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("searchValue", searchValue);
        return "banks";
    }

    private APIResponse<RestPageImpl<BankDTO>> getAllBanks(String searchValue, Integer pageNo, Integer pageSize, String sortBy) {
        Map<String, Object> values = new HashMap<>();
        values.put("searchValue", searchValue);
        values.put("pageNo", pageNo);
        values.put("pageSize", pageSize);
        values.put("sortBy", sortBy);
        return restService.execute(
                new StringBuilder(url).append("banks")
                        .append("?searchValue={searchValue}&page={pageNo}&size={pageSize}&sortBy={sortBy}").toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<RestPageImpl<BankDTO>>>() {
                },
                values);
    }

    /**
     * author duongbv
     * get Bank for search
     *
     * @param searchValue
     * @return
     */
    @PostMapping
    public String getListAllBankBySearchValue(@RequestParam("table_search") String searchValue) {
        return "redirect:/banks?searchValue=" + searchValue;
    }

    @GetMapping("/create")
    public String showCreateBankPage(@ModelAttribute("bankDTO") BankDTO bankDTO, Model model) {
        model.addAttribute("bankDTO", bankDTO);
        return "add-bank";
    }

    /**
     * create Bank
     *
     * @param bankDTO
     * @param result
     * @param authentication
     * @return
     */
    @PostMapping("/create")
    public String saveBank(@Valid @ModelAttribute("bankDTO") BankDTO bankDTO, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return "add-bank";
        }
        bankDTO.setCreatedBy(authentication.getName());
        bankDTO.setModifiedBy(authentication.getName());

        Boolean status = restService.execute(
                url + "banks/validate",
                HttpMethod.POST,
                setTokenHeader(),
                bankDTO,
                new ParameterizedTypeReference<APIResponse<Boolean>>() {
                }).getData();

        if (!status) {
            restService.execute(
                    url + "banks",
                    HttpMethod.POST,
                    setTokenHeader(),
                    bankDTO,
                    new ParameterizedTypeReference<APIResponse<BankDTO>>() {
                    });
        } else {
            result.reject("bank.duplicate.errorMsg", "Ngân hàng với thông tin trên đã tồn tại!");
            return "add-bank";
        }

        return "redirect:/banks";
    }

    @GetMapping("/update")
    public String showEditBankPage(@RequestParam(value = "bankId") Integer bankId, Model model) {
        String authToken = JWTUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        if (bankId == null) {
            return "Error";
        } else {
            BankDTO bank = restService.execute(
                    url + "banks/" + bankId,
                    HttpMethod.GET,
                    header,
                    null,
                    new ParameterizedTypeReference<APIResponse<BankDTO>>() {
                    }).getData();
            model.addAttribute("banks", bank);
            return "edit-bank";
        }
    }

    @PostMapping("/update")
    public String updateBank(@Valid @ModelAttribute("banks") BankDTO bankDTO,
                             BindingResult result,
                             @RequestParam("bankId") Integer bankId,
                             Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (result.hasErrors()) {
            model.addAttribute("banks", bankDTO);
            return "edit-bank";
        }
        bankDTO.setModifiedBy(authentication.getName());

        Boolean status = restService.execute(
                url + "banks/validate",
                HttpMethod.POST,
                setTokenHeader(),
                bankDTO,
                new ParameterizedTypeReference<APIResponse<Boolean>>() {
                }).getData();

        if (!status) {
            restService.execute(
                    new StringBuilder(url).append("banks?bankId=").append(bankId).toString(),
                    HttpMethod.PUT,
                    setTokenHeader(),
                    bankDTO,
                    new ParameterizedTypeReference<APIResponse<BankDTO>>() {
                    });
        } else {
            result.reject("bank.duplicate.errorMsg", "Ngân hàng với thông tin trên đã tồn tại!");
            model.addAttribute("banks", bankDTO);
            return "edit-bank";
        }

        return "redirect:/banks";
    }

    /**
     * Delete bank
     * @author Duong NV
     * @since 21/04/2020
     */
    @DeleteMapping("/{bankId}")
    public ResponseEntity<Object> deleteBank(@PathVariable Integer bankId) {
        try{
            APIResponse<Object> apiResponse = restService.execute(url + "banks/" + bankId, HttpMethod.DELETE,
                    setTokenHeader(), null, new ParameterizedTypeReference<APIResponse<Object>>() {});
            return ResponseEntity.ok(apiResponse);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    public HttpHeaders setTokenHeader(){
        String authToken = JWTUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        header.setContentType(MediaType.APPLICATION_JSON);
        return header;
    }

}
