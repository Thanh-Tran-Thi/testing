package com.smartosc.training.entity;

/**
 * fres-parent
 *
 * @author Mai Mai
 * @created_at 22/04/2020 - 5:47 PM
 * @created_by Mai Mai
 * @since 22/04/2020
 */
public enum TransStatus {
    ACTIVE(1), INACTIVE(0);
    private Integer value;

    TransStatus(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
}
