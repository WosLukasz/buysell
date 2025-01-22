package com.wosarch.buysell.buysell.model.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Amount implements Serializable {

    private Double value;
    private String currency;

}
