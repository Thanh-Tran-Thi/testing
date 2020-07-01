package com.smartosc.training.repositories;


import com.smartosc.training.dto.BankDirectConfigDTO;
import com.smartosc.training.dto.BankSenderConfigDTO;
import com.smartosc.training.entity.BankDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * fres-parent
 *
 * @author longtcs
 * @created_at 21/04/2020 - 15:02
 * @created_by longtcs
 * @author anhdt
 * @project fres-parent
 * @created_at 20/04/2020 - 6:11 PM
 * @created_by anhdt
 * @since 21/04/2020
 * @since 20/04/2020
 * @author duongnch
 * @since 20/04/2020
 * Func findAllBankDirectConfiguration
 * Get Info Direct Bank Configuration
 */
@Repository
public interface BankDetailRepository extends JpaRepository<BankDetail, Integer> {
    // Get all send bank configuration
    @Query(value = "SELECT new com.smartosc.training.dto.BankSenderConfigDTO(bd.id, b.code, b.legalName, bd.value, " +
            " bd.status, bd.modifiedBy, bd.modifiedDatetime) " +
            " FROM Bank b " +
            " JOIN BankDetail bd " +
            " ON b.bankId = bd.bankId " +
            " WHERE bd.type = 'SEND'" +
            " ORDER BY bd.modifiedDatetime DESC")
    List<BankSenderConfigDTO> findAllBankSenderConfig();



    /**
     * @param searchKey
     * @return
     * @author anhdt2
     */
    @Query("select b.code, b.legalName, bd.status, bd.modifiedBy, bd.modifiedDatetime " +
            " from Bank b join BankDetail bd " +
            " on b.bankId = bd.bankId " +
            " where bd.type = 'TRANSIT' " +
            " and (b.code like %:searchKey% or b.legalName like %:searchKey%)")
    List<Object[]> findInterBankConfigByCodeOrByLegalName(@Param("searchKey") String searchKey);

    /**
     * @author duongnch
     * @param pageable
     * @param searchKey
     * @return
     */
    @Query(value = "SELECT new com.smartosc.training.dto.BankDirectConfigDTO (bd.id,b.bankId,b.code, b.legalName, " +
            "bd.status, bd.modifiedBy, bd.modifiedDatetime) " +
            "FROM Bank b JOIN BankDetail bd " +
            "ON b.bankId = bd.bankId " +
            "WHERE bd.type = 'DIRECT' " +
            "AND (b.legalName like %:searchKey% " +
            "OR b.code like %:searchKey% " +
            "OR b.prefixCard like %:searchKey%)")
    Page<BankDirectConfigDTO> findAllBankDirectConfiguration(Pageable pageable, @Param("searchKey") String searchKey);


    Optional<BankDetail> findByBankIdAndType(int id, String type);

    /**
     * @param id
     * @param type
     * @return
     * @author anhdt2
     */
    Optional<BankDetail> findByIdAndType(int id, String type);

    /**
     * Get all intermediary banks
     *
     * @return
     * @author anhdt2
     */
    @Query("select bd.id ,b.code, b.legalName, bd.status, bd.modifiedBy, bd.modifiedDatetime " +
            " from Bank b " +
            " join BankDetail bd " +
            " on b.bankId = bd.bankId " +
            " where bd.type = 'TRANSIT' ")
    List<Object[]> findAllInterBank();
}
