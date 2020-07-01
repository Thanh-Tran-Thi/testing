package com.smartosc.training.controller;

import com.smartosc.training.dto.PageMetaData;
import com.smartosc.training.dto.ServicesDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.service.ServicesService;

import java.util.List;
import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * fres-parent
 *
 * @author dv-trong
 * @created_at 17/04/2020 - 17:51
 * @created_by dv-trong
 * @since 17/04/2020
 */

@RestController
@RequestMapping("/services")
public class ServiceController {
    @Autowired
    private ServicesService servicesService;


    // Get all services
    @GetMapping
    public ResponseEntity<Object> getAllService() {
        List<ServicesDTO> ListServicesDTO = servicesService.getAllServicesDTOS();
        APIResponse<List<ServicesDTO>> responseData = new APIResponse<>();

        // Check if list is null
        if (ListServicesDTO != null) {
            responseData.setStatus(HttpStatus.OK.value());
            responseData.setData(ListServicesDTO);
            responseData.setMessage("Get all Services successfully");
        } else {
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setMessage("Get all Services failed");
        }

        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        try {
            servicesService.deleteService(id);
            return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(), "Delete promotion with ID = " + id + " successfully!", id, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new APIResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null, null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse<Boolean>> addService(@RequestBody ServicesDTO servicesDTO) {
        Boolean result = servicesService.addService(servicesDTO);
        APIResponse<Boolean> responseData = new APIResponse<>();
        responseData.setStatus(result ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value());
        responseData.setMessage(result ? "Add successful" : "add fail");
        responseData.setData(result);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    /**
     * update existing service
     *
     * @param modifiedService modified serviceDTO sent from client
     * @return
     * @author hieund
     */
    @PutMapping
    public ResponseEntity<Object> update(@RequestBody ServicesDTO modifiedService) {
        try {
            ServicesDTO service = servicesService.updateService(modifiedService);
            return new ResponseEntity<>(
                    new APIResponse<>(HttpStatus.OK.value(), "Service updated successfully", service, null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new APIResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), modifiedService, null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<APIResponse<Boolean>> isDuplicateService(@RequestBody ServicesDTO servicesDTO) {
        Boolean result = servicesService.ServiesExxist(servicesDTO);
        APIResponse<Boolean> responseData = new APIResponse<>();
        responseData.setData(result);
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage(result ? "duplicate service" : "No duplicate service");
        return ResponseEntity.ok(responseData);
    }
}
