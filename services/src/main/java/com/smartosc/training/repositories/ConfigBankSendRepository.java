package com.smartosc.training.repositories;

import com.smartosc.training.entity.BankDetail;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * fres-parent
 *
 * @author Lam Chuot
 * @created_at 17/04/2020 - 17:36
 * @created_by Lam Chuot
 * @since 17/04/2020
 */
public interface ConfigBankSendRepository extends JpaRepository<BankDetail, Integer> {
}
