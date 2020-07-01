package com.smartosc.training.service;


import com.smartosc.training.dto.BankDTO;
import com.smartosc.training.dto.BankDirectConfigDTO;
import com.smartosc.training.dto.DirectBankDTO;
import com.smartosc.training.entity.Bank;
import com.smartosc.training.entity.BankDetail;
import com.smartosc.training.exception.FieldDuplicateException;
import com.smartosc.training.exception.InternalServerException;
import com.smartosc.training.exception.NotFoundException;
import com.smartosc.training.repositories.BankDetailRepository;

import com.smartosc.training.repositories.BankRepository;
import com.smartosc.training.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.text.SimpleDateFormat;

import static com.smartosc.training.utility.AppConstants.EXCEPTION_BANK_INTERNAL_SERVER;
import static com.smartosc.training.utility.AppConstants.EXCEPTION_BANK_NOT_FOUND;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;


/**
 * @author duongnch
 * @since 20/04/2020
 */
@Slf4j
@Service
public class BankService {
    private static final String BANK_TYPE = "DIRECT";
    private BankDetailRepository bankDetailRepository;

    private BankRepository bankRepository;


    @Autowired
    public BankService(BankDetailRepository bankDetailRepository, BankRepository bankRepository) {
        this.bankDetailRepository = bankDetailRepository;
        this.bankRepository = bankRepository;
    }

    static String message;

    /**
     * get all Banks
     *
     * @param searchValue
     * @param pageNo
     * @param sizeNo
     * @param sortBy
     * @return page
     */
    public Page<BankDTO> getAllBanks(String searchValue, Integer pageNo, Integer sizeNo, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, sizeNo, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Bank> pageResult = bankRepository.searchByNameAndDes(searchValue, pageable);
        return pageResult.map(ConvertUtils::convertBankToBankDTO);
    }

    public BankDTO createBank(BankDTO bankDTO) throws FieldDuplicateException {
        LOGGER.info("Starting check duplicate");
        if (bankRepository.findByCode(bankDTO.getCode()).isPresent()) {
            throw new FieldDuplicateException("Bank code: " + bankDTO.getCode() + " already exist!");
        } else if (bankRepository.findByLegalName(bankDTO.getLegalName()).isPresent()) {
            throw new FieldDuplicateException("Bank legal name: " + bankDTO.getLegalName() + " already exist!");
        } else if (bankRepository.findByPrefixCard(bankDTO.getPrefixCard()).isPresent()) {
            throw new FieldDuplicateException("Bank prefix card " + bankDTO.getPrefixCard() + " already exist!");
        } else {
            LOGGER.info("No duplicate found!");
            Bank result = ConvertUtils.convertBankDTOToBank(bankDTO);
            return ConvertUtils.convertBankToBankDTO(bankRepository.save(result));
        }
    }

    /**
     * create by tuan on 17/04/2020
     *
     * @param bankDTO
     * @return
     */
    public BankDTO updateBank(BankDTO bankDTO, Integer bankId) {
        LOGGER.info("Starting check duplicate");
        Optional<Bank> responseData = null;
        if ((responseData = bankRepository.findByCode(bankDTO.getCode())).isPresent()) {
            if (!bankDTO.getBankId().equals(responseData.get().getBankId()))
                throw new FieldDuplicateException("Bank code: " + bankDTO.getCode() + " already exist!");
        }
        if ((responseData = bankRepository.findByLegalName(bankDTO.getLegalName())).isPresent()) {
            if (!bankDTO.getBankId().equals(responseData.get().getBankId()))
                throw new FieldDuplicateException("Bank legal name: " + bankDTO.getLegalName() + " already exist!");
        }
        if ((responseData = bankRepository.findByPrefixCard(bankDTO.getPrefixCard())).isPresent()) {
            if (!bankDTO.getBankId().equals(responseData.get().getBankId()))
                throw new FieldDuplicateException("Bank prefix card " + bankDTO.getPrefixCard() + " already exist!");
        }
        Optional<Bank> bank = bankRepository.findById(bankId);
        if (bank.isPresent()) {
            LOGGER.info("No duplicate found!");
            Bank bankUpdate = bankRepository.save(ConvertUtils.convertBankDTOToBankUpdate(bankDTO, bank.get(), bankId));
            LOGGER.info("update successful!");
            return ConvertUtils.convertBankToBankDTO(bankUpdate);
        }
        LOGGER.info("update fail by not found bank!");
        return null;
    }

    public BankDTO findById(Integer id) {
        Optional<Bank> result = bankRepository.findById(id);
        return result.map(ConvertUtils::convertBankToBankDTO).orElse(null);
    }

    /**
     * @param id
     * @return void
     * @author Duong NV
     * @since 21/04/2020
     */

    public void deleteBank(int id){
        if (!bankRepository.findById(id).isPresent()) {
            throw new NotFoundException(EXCEPTION_BANK_NOT_FOUND);
        }
        try {
            bankRepository.deleteById(id);
        }catch (Exception e){
            throw new InternalServerException(EXCEPTION_BANK_INTERNAL_SERVER);
        }

    }

    /**
     * Check bank is duplicate or not
     *
     * @param bankDTO
     * @return bank already exist
     * @author DatNT5
     */
    public Boolean isBankExist(BankDTO bankDTO) {
        Boolean result = false;
        List<Bank> banks = bankRepository.findByCodeOrLegalNameOrPrefixCard(
                bankDTO.getCode(), bankDTO.getLegalName(), bankDTO.getPrefixCard());
        for (Bank bank : banks) {
            if (bankDTO.getBankId() == null || !bankDTO.getBankId().equals(bank.getBankId())) {
                result = true;
            }
        }
        return result;
    }

    /**
     * @author duongnch
     * @param pageable
     * @param searchKey
     * @return
     */
    public Page<BankDirectConfigDTO> getAllDirectBankConfiguration(Pageable pageable, String searchKey) {
        try {
            log.info("Retrieving Direct bank");
            Page<BankDirectConfigDTO> listDirectBank = bankDetailRepository.findAllBankDirectConfiguration(pageable,searchKey);
            log.info("Convert into BankDirectConfigDto Success");
            log.debug("Paging bank result: {}", listDirectBank);
            return listDirectBank;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new NullPointerException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<DirectBankDTO> findAllDirectBank(String searchKey, Integer pageNo, Integer pageSize, String sortBy) {

        log.info("Retrieving intermediary banks");
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Bank> result = bankRepository.finAllDirectBank(searchKey, BANK_TYPE, pageable);
        log.debug("Paging bank result: {}", result.getContent());
        return result.map(bank -> {

            BankDetail bankDetail = bankDetailRepository.findByBankIdAndType(bank.getBankId(), BANK_TYPE).get();
            return ConvertUtils.convertBankToDirectBankDTO(bank, bankDetail);


        });
    }
}

