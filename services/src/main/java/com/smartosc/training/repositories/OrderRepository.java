package com.smartosc.training.repositories;

import com.smartosc.training.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * fres-parent
 *
 * @author Tung lam
 * @created_at 21/04/2020 - 09:38
 * @created_by Tung lam
 * @since 21/04/2020
 */
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT o FROM Orders o INNER JOIN o.users u"
            + " WHERE u.userName LIKE %:searchValue%")
    Page<Orders> findByUsers_UserName(@Param("searchValue") String searchValue, Pageable pageable);

    @Query("SELECT o FROM Orders o INNER JOIN o.users u WHERE u.userName = :userName AND o.status = :status")
    List<Orders> findByUsers_UserName(@Param("userName") String userName, @Param("status") Integer status);

    Long countByStatus(Integer status);
}
