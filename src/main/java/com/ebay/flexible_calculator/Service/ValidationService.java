package com.ebay.flexible_calculator.Service;

import com.ebay.flexible_calculator.Enum.Operation;
import com.ebay.flexible_calculator.Exception.BadParameterException;
import com.ebay.flexible_calculator.Payload.OperationRequest;
import org.springframework.stereotype.Service;
import com.ebay.flexible_calculator.Payload.CalculationRequest;

import java.util.Arrays;
import java.util.List;

@Service
public class ValidationService {

    public boolean validateCalculateRequest(CalculationRequest calculateRequest) {
        validateAndParseOperation(calculateRequest.getOperation());
        Number num1 = calculateRequest.getNum1();
        Number num2 = calculateRequest.getNum2();
        return validateInputs(num1, num2);
    }

    public boolean validateChainRequest(CalculationRequest chainRequest) {
        List<OperationRequest> operations = chainRequest.getOperations();
        return validateInputs(operations);
    }

    public boolean validateOperation(String operation) {
        if (operation == null) {
            throw new BadParameterException();
        }
        return true;
    }

    public boolean validateInputs(Number num1, Number num2) {
        if (num1 == null || num2 == null) {
            throw new BadParameterException("Invalid input: Numbers cannot be null");
        }
        if (num2.doubleValue() == 0.0 && num2.doubleValue() == 0.0) {
            throw new BadParameterException("Invalid input: Divisor cannot be 0");
        }
        return true;
    }

    public boolean validateInputs(List<OperationRequest> operations) {
        if (operations == null || operations.isEmpty()) {
            throw new BadParameterException("Invalid operation list: Operation list cannot be null or empty");
        }
        for (OperationRequest operationRequest : operations) {
            validateAndParseOperation(operationRequest.getOperation());
            if (operationRequest.getNum() == null) {
                throw new BadParameterException("Invalid operation request: Operation and number cannot be null");
            }
        }
        return true;
    }

    public Operation validateAndParseOperation(String operationStr) {
        try {
            validateOperation(operationStr);
            Operation operation = Operation.valueOf(operationStr.toUpperCase());
            return operation;
        } catch (BadParameterException e) {
            throw new BadParameterException("Invalid operation: " + operationStr + ". Supported operations are: ADD, SUBTRACT, MULTIPLY, DIVIDE.");
        }
    }
}