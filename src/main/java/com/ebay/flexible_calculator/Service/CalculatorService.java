package com.ebay.flexible_calculator.Service;
import com.ebay.flexible_calculator.Enum.Operation;
import com.ebay.flexible_calculator.Payload.OperationRequest;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CalculatorService {
    // Method to perform a single operation
    public Number calculate(Operation operation, Number num1, Number num2) throws IllegalArgumentException{
        if(operation == null)
            throw new UnsupportedOperationException();
        if(num1 == null || num2 == null)
            throw new IllegalArgumentException();
        return operation.apply(num1, num2);
    }

    // Method to chain multiple operations on an initial value
    public Number chainOperations(Number initialValue, List<OperationRequest> requests) throws IllegalArgumentException{
        if(initialValue == null || requests == null)
            throw new IllegalArgumentException();
        Number result = initialValue;

        for (OperationRequest request : requests) {
            if(request.getNum() == null)
                throw new IllegalArgumentException();
            if(request.getOperation() == null)
                throw new UnsupportedOperationException();
            Operation operation = Operation.valueOf(request.getOperation().toUpperCase());
            result = operation.apply(result, request.getNum());
        }

        return result;
    }
}
