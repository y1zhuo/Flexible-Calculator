package com.ebay.flexible_calculator.Payload;

import com.ebay.flexible_calculator.Enum.Operation;

import java.util.List;

public class CalculationRequest {

    private String operation;   // Operation: ADD, SUBTRACT, MULTIPLY, DIVIDE

    private Number num1;
    private Number num2;

    private Number initialVal;
    private List<OperationRequest> operations;
    public String getOperation() {
        return operation;
    }

    public List<OperationRequest> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationRequest> operations) {
        this.operations = operations;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Number getNum1() {return num1;}

    public void setNum1(Number num1) {
        this.num1 = num1;
    }

    public Number getNum2() {
        return num2;
    }

    public void setNum2(Number num2) {
        this.num2 = num2;
    }

    public Number getInitialVal() {return initialVal;}

    public void setInitialVal(Number initialVal) {this.initialVal = initialVal;}
}
