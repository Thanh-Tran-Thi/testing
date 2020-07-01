package com.smartosc.training.repositories;

import com.smartosc.training.entity.Services;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * fres-parent
 *
 * @author dv-trong
 * @created_at 17/04/2020 - 17:33
 * @created_by dv-trong
 * @since 17/04/2020
 */

@Repository
public interface ServicesRepository extends JpaRepository<Services, Integer> {

    @Query("SELECT s FROM Services s WHERE (s.name LIKE %:searchValue% OR s.code LIKE %:searchValue%)")
    Page<Services> findAllBySearchValue(@Param("searchValue") String searchValue, Pageable pageable);

    boolean existsByName(String name);

    boolean existsByCode(String code);
}
