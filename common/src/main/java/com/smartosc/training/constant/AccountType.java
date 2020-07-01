package com.smartosc.training.constant;

/**
 * @author: ductd
 * @since: 29/4/2020
 * @version: 1.0
 */
public enum AccountType {
    SPEND(1), RECEIVE(2), FEE(3) ;

    private int value;

    AccountType(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
