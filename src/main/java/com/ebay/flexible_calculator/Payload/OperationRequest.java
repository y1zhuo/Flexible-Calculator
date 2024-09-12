package com.ebay.flexible_calculator.Payload;

import com.ebay.flexible_calculator.Enum.Operation;

import javax.swing.*;

public class OperationRequest {
    private String operation;  // Operation: ADD, SUBTRACT, MULTIPLY, DIVIDE
    private Number num;

    public OperationRequest(String operation, Number num) {
        this.operation = operation;
        this.num = num;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Number getNum() {return num;}

    public void setNum(Number num) {
        this.num = num;
    }
}
