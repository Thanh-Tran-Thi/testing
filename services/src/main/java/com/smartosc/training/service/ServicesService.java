package com.smartosc.training.service;

import com.smartosc.training.dto.ServicesDTO;
import com.smartosc.training.entity.Services;
import com.smartosc.training.entity.Status;
import com.smartosc.training.entity.TransStatus;
import com.smartosc.training.exception.NotFoundException;
import com.smartosc.training.repositories.ServicesRepository;
import com.smartosc.training.utils.ConvertUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * fres-parent
 *
 * @author dv-trong
 * @created_at 17/04/2020 - 17:34
 * @created_by dv-trong
 * @since 17/04/2020
 */

@Service
public class ServicesService {

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Get list service
    public List<ServicesDTO> getAllServicesDTOS() {
        List<Services> listService = servicesRepository.findAll(Sort.by("modifiedDatetime").descending());

        return listService.stream().map(e -> ConvertUtils.convertServiceToServiceDTO(e)).collect(Collectors.toList());
    }

    /**
     * Get id services.
     *
     * @param id
     */
    public void deleteService(Integer id) {
        Services responseData = null;
        Optional<Services> result = servicesRepository.findById(id);
        if (result.isPresent()) {
            Services services = result.get();
            services.setStatus(Status.INACTIVE.getValue());
            services.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            responseData = servicesRepository.save(services);
            ConvertUtils.convertServiceToServiceDTO(responseData);
        } else {
            throw new IllegalArgumentException("Id not found.");
        }
    }

    /**
     * Create Service
     *
     * @return
     */
    public Boolean addService(ServicesDTO servicesDTO) {
        try {
            Services services = modelMapper.map(servicesDTO, Services.class);
            services.setStatus(Status.ACTIVE.getValue());

            if (services.getTransStatus() == null || services.getTransStatus() != TransStatus.ACTIVE.getValue()) {
                services.setTransStatus(TransStatus.INACTIVE.getValue());
            }
            services.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            services.setCreatedDatetime(LocalDateTime.now());
            services.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            services.setModifiedDatetime(LocalDateTime.now());
            servicesRepository.save(services);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * update existing Service Object
     *
     * @param updateRequest
     * @return updated ServicesDTO
     * @author hieund
     */
    public ServicesDTO updateService(ServicesDTO updateRequest) {

//		validate update request
        Optional<Services> result = servicesRepository.findById(updateRequest.getServiceId());
        if (result.isPresent()) {
            if (!result.get().getCode().equals(updateRequest.getCode())) {
                throw new IllegalArgumentException("Service Code is immutable");
            }
            Services services = ConvertUtils.convertServicesDTOToServices(updateRequest);
            services.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            servicesRepository.save(services);
            return updateRequest;
        } else {
            throw new IllegalArgumentException("Invalid Serivce ID");
        }
    }

    public Boolean ServiesExxist(ServicesDTO servicesDTO) {
        List<Services> servicesList = servicesRepository.findAll();
        Boolean result = false;
        for (Services services : servicesList) {
            if (servicesDTO.getName().trim().equalsIgnoreCase(services.getName().trim())
                    || servicesDTO.getCode().trim().equalsIgnoreCase(services.getCode().trim())) {
                result = true;
            }
        }
        return result;
    }
}
