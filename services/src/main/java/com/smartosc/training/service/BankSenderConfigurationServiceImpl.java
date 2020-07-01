package com.smartosc.training.service;

import com.smartosc.training.dto.BankDTO;
import com.smartosc.training.dto.BankDetailDTO;
import com.smartosc.training.dto.BankSenderConfigDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.entity.Bank;
import com.smartosc.training.entity.BankDetail;
import com.smartosc.training.exception.DuplicateRecordException;
import com.smartosc.training.exception.InternalServerException;
import com.smartosc.training.exception.NotFoundException;
import com.smartosc.training.mapper.BankDetailMapper;
import com.smartosc.training.repositories.BankDetailRepository;
import com.smartosc.training.repositories.BankRepository;
import com.smartosc.training.request.UpdateBankReq;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author longtcs on 20/04/2020
 * @project fres-parent
 */
@Component
public class BankSenderConfigurationServiceImpl implements BankSenderConfigurationService {
    private static final String BANK_TYPE_SEND = "SEND";

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${exception.not.found}")
    private String notFound;

    @Value("${expception.update.sever.error}")
    private String updateServerError;

    @Value("${update.successful}")
    private String updateSuccess;

    @Autowired
    private BankDetailRepository bankDetailRepository;


    // Get list send bank configuration
    /**
     *
     * @return
     */
    @Override
    public List<BankSenderConfigDTO> getAllBankSenderConfig() {
        return bankDetailRepository.findAllBankSenderConfig();
    }

    // edit send bank config
    @Override
    public BankDetailDTO editBankDetail(UpdateBankReq updateBankReq, Integer id) {
        Optional<BankDetail> bankDetail = bankDetailRepository.findById(id);
        BankDetail bankDetail1 = bankDetail.get();
        if (!bankDetail.isPresent()) {
            throw new NotFoundException(notFound);
        }
        try {
            // check type of bank detail
            if (bankDetail1.getType().trim().equalsIgnoreCase("SEND")) {
                bankDetail1.setStatus(updateBankReq.getStatus());
                bankDetail1.setValue(updateBankReq.getValue());
                bankDetail1.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                bankDetailRepository.save(bankDetail.get());

            }
            return BankDetailMapper.toBankDetailDTO(bankDetail1);
        } catch (Exception e) {
            throw new InternalServerException(updateServerError);
        }
    }

    // Add bank detail
    @Override
    public BankDetailDTO addBankDetail(BankDetailDTO bankDetailDTO) {
        Optional<BankDetail> bankDetail = bankDetailRepository.findByBankIdAndType(bankDetailDTO.getBankId(),
                BANK_TYPE_SEND);
        if (bankDetail.isPresent()) {
            throw new DuplicateRecordException("Duplicate , please choose again");
        }
        BankDetail newBankDetail = new BankDetail();
        newBankDetail.setBankId(bankDetailDTO.getBankId());
        newBankDetail.setStatus(bankDetailDTO.getStatus());
        newBankDetail.setValue(bankDetailDTO.getValue());
        newBankDetail.setType(BANK_TYPE_SEND);
        newBankDetail.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        newBankDetail.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return modelMapper.map(bankDetailRepository.save(newBankDetail), BankDetailDTO.class);
    }

    // Get list banks
    @Override
    public List<BankDTO> getAllBankActive() {
        List<Bank> banks = bankRepository.findByStatus(1);
        List<BankDTO> bankDTOS = new ArrayList<>();
        banks.forEach(b -> {
            BankDTO bankDTO = new BankDTO();
            bankDTO = modelMapper.map(b, BankDTO.class);
            bankDTOS.add(bankDTO);
        });
        return bankDTOS;
    }
}
