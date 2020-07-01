package com.smartosc.training.entity;

/**
 * fres-parent
 * @author thanhttt
 * @created_at 21/04/2020 - 6:14 PM
 */
public enum Status {

    ACTIVE(1), INACTIVE(0);

    private Integer value;

    Status(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
