package com.smartosc.training.repositories;

import com.smartosc.training.dto.AccountBankDTO;
import com.smartosc.training.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT NEW com.smartosc.training.dto.AccountBankDTO(a.accountId,a.accountType," +
            "b.bankId,a.accountNumber,a.status,b.code,b.legalName,a.modifiedBy,a.modifiedDatetime) FROM Account a " +
            "JOIN Bank b ON a.bankId = b.bankId " +
            "WHERE (b.code like %:searchValue%  " +
            "OR b.legalName like %:searchValue% " +
            "OR a.accountNumber LIKE %:searchValue%)")
    Page<AccountBankDTO> findByAllAccountBankDTO(@Param("searchValue") String searchValue, Pageable pageable);

    Optional<Account> findByAccountNumber(String accountNumber);
}
