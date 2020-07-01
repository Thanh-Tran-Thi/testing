package com.smartosc.training.controller;

import com.smartosc.training.dto.AccountBankDTO;
import com.smartosc.training.dto.AccountDTO;
import com.smartosc.training.dto.BankDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.request.AccountReq;
import com.smartosc.training.service.RestService;
import com.smartosc.training.utils.JWTUtil;
import com.smartosc.training.utils.RestPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/account-banks")
public class AccountBankController {

    @Autowired
    private RestService restService;

    @Value("${api.url}")
    private String url;

    /**
     * get all accounts and search
     * @author Hien VV
     * @since 05/05/2020
     * @return
     */
    @GetMapping
    public String getAllAccountBank(Model model,
                                    @RequestParam(defaultValue = "0", required = false) Integer page,
                                    @RequestParam(defaultValue = "8", required = false) Integer size,
                                    @RequestParam(defaultValue = "accountId", required = false) String sortBy,
                                    @RequestParam(defaultValue = "", required = false) String searchValue
    ) {
        APIResponse<RestPageImpl<AccountBankDTO>> responseData = getAllAccountBank(page, size, sortBy, searchValue);
        RestPageImpl<AccountBankDTO> accountBankDTOS = null;
        if (responseData.getStatus() == HttpStatus.OK.value()) {
            accountBankDTOS = responseData.getData();
        }
        model.addAttribute("data", accountBankDTOS);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("banks", getBanksActive());
        model.addAttribute("type", getAccountType());
        return "account-bank";
    }

    /**
     * call API get all accounts and search
     * @author Hien VV
     * @since 05/05/2020
     * @return
     */
    private APIResponse<RestPageImpl<AccountBankDTO>> getAllAccountBank(Integer page, Integer size, String sortBy, String searchValue) {
        Map<String, Object> values = new HashMap<>();
        values.put("searchValue", searchValue);
        values.put("page", page);
        values.put("size", size);
        values.put("sortBy", sortBy);
        String link = url + "account-banks?searchValue={searchValue}&page={page}&size={size}&sortBy={sortBy}";
        return restService.execute(
                link, HttpMethod.GET, setTokenHeader(), null,
                new ParameterizedTypeReference<APIResponse<RestPageImpl<AccountBankDTO>>>() {}, values);
    }

    /**
     * get account by id
     * @author Duong NV
     * @since 29/04/2020
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Integer id){
        AccountDTO accountDTO = restService.execute(url + "account-banks/" + id, HttpMethod.GET, setTokenHeader(),
                null, new ParameterizedTypeReference<APIResponse<AccountDTO>>() {}).getData();
        return ResponseEntity.ok(accountDTO);
    }

    /**
     * create account
     * @author Duong NV
     * @since 29/04/2020
     * @return
     */
    @PostMapping("")
    public ResponseEntity<Object> createAccount(@Valid @RequestBody AccountReq req) {
        try{
            APIResponse<Object> apiResponse = restService.execute(url + "account-banks", HttpMethod.POST,
                    setTokenHeader(), req, new ParameterizedTypeReference<APIResponse<Object>>() {});
            return ResponseEntity.ok(apiResponse);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * update account
     * @author Duong NV
     * @since 04/05/2020
     * @return
     */
    @PutMapping("")
    public ResponseEntity<Object> updateAccount(@RequestBody AccountReq req) {
        try{
            APIResponse<Object> apiResponse = restService.execute(url + "account-banks", HttpMethod.PUT,
                    setTokenHeader(), req, new ParameterizedTypeReference<APIResponse<Object>>() {});
            return ResponseEntity.ok(apiResponse);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * get list banks active (status = 1)
     * @author Duong NV
     * @since 04/05/2020
     * @return
     */
    public List<BankDTO> getBanksActive() {
        String link = url + "sendbankconfig/getAllBank";
        return restService.execute(link , HttpMethod.GET, setTokenHeader(), null,
                new ParameterizedTypeReference<APIResponse<List<BankDTO>>>() {}).getData();
    }

    /**
     * get account type
     * @author Duong NV
     * @since 04/05/2020
     * @return
     */
    public Map<Integer, String> getAccountType(){
        Map<Integer, String> accountType = new HashMap<>();
        accountType.put(1, "Chuyên chi");
        accountType.put(2, "Chuyên thu");
        accountType.put(3, "Tài khoản phí");
        return accountType;
    }

    /**
     * Set token to header
     * @author Duong NV
     * @since 29/04/2020
     * @return
     */
    public HttpHeaders setTokenHeader(){
        String authToken = JWTUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        header.setContentType(MediaType.APPLICATION_JSON);
        return header;
    }

}
