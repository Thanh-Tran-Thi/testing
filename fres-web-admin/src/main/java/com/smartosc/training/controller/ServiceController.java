package com.smartosc.training.controller;

import com.smartosc.training.dto.ServicesDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.service.RestService;
import com.smartosc.training.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * fres-parent
 *
 * @author Mai Mai
 * @created_at 22/04/2020 - 9:50 AM
 * @created_by Mai Mai
 * @since 22/04/2020
 */

@Controller
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private RestService restService;

    @Value(value = "${api.url}")
    private String url;
    @Value(value = "${prefix.services}")
    private String prefixServices;

    // Get all services
    @GetMapping
    public String getAllServices(Model model) {
        APIResponse<List<ServicesDTO>> responseData = restService.execute(
                url + prefixServices,
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<List<ServicesDTO>>>() {
                });

        List<ServicesDTO> servicesDTOS = null;
        if (responseData.getStatus() == HttpStatus.OK.value()) {
            servicesDTOS = responseData.getData();
        }
        model.addAttribute("listServices", servicesDTOS);
        return "service/index";
    }


    @GetMapping("/add")
    public String create(@ModelAttribute("serviceRequest") ServicesDTO serviceRequest, Model model) {
        model.addAttribute("serviceRequest", serviceRequest);
        return "service/create";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("serviceRequest") @Valid ServicesDTO serviceRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "service/create";
        }
        Boolean status = restService.execute(url + prefixServices + "/validate", HttpMethod.POST, JWTUtil.getHearder(), serviceRequest,
                new ParameterizedTypeReference<APIResponse<Boolean>>() {
                }).getData();
        if (!status) {
            restService.execute(url + prefixServices + "/add", HttpMethod.POST, JWTUtil.getHearder(), serviceRequest,
                    new ParameterizedTypeReference<APIResponse<Boolean>>() {
                    });
        } else {
            bindingResult.reject("service.dupicate.errorMessage", "Thông tin trên đã tồn tại");
            return "service/create";
        }
        return "redirect:/service";
    }

    /**
     * @param serviceId
     * @param servicesDTO
     * @return
     * @author Tung Lam
     */
    @GetMapping("/delete")
    public String delete(@RequestParam("service_id") Integer serviceId, ServicesDTO servicesDTO) {
        restService.execute(url + prefixServices + "/" + serviceId, HttpMethod.DELETE, JWTUtil.getHearder(), servicesDTO,
                new ParameterizedTypeReference<APIResponse<Integer>>() {
                });
        return "redirect:/service";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ServicesDTO editService(@RequestBody ServicesDTO servicesDTO) {
        APIResponse<ServicesDTO> response = restService.execute(url + prefixServices,
                HttpMethod.PUT,
                JWTUtil.getHearder(),
                servicesDTO,
                new ParameterizedTypeReference<APIResponse<ServicesDTO>>() {
                });
        return response.getData();
    }

}
