package com.smartosc.training.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: ductd
 * @since: 29/4/2020
 * @version: 1.0
 */
@Entity
@Table(name = "account")
@Data
public class Account extends BaseAudit{
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    @Column(name = "account_number")
    private String accountNumber;

    //1 chuyen chi, 2 chuyen thu, 3 tai khoan phi
    @Column(name = "account_type")
    private Integer accountType;

    @Column(name = "bank_id")
    private Integer bankId;

    //1 hoat dong, 2 khong hoat dong
    @Column(name = "status")
    private Integer status;
}
