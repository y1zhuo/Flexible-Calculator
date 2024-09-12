package com.ebay.flexible_calculator.Payload;

public class CalculationResult {
    private Number result;

    public CalculationResult(Number result) {
        this.result = result;
    }

    public Number getResult() {
        return result;
    }

    public void setResult(Number result) {
        this.result = result;
    }
}
