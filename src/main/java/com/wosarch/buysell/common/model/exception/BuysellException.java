package com.wosarch.buysell.common.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuysellException extends Exception {

    private String message;

}
