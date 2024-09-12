package com.ebay.flexible_calculator.Service;

import com.ebay.flexible_calculator.Enum.Operation;
import com.ebay.flexible_calculator.Payload.OperationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    public void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    public void testCalculate_Addition() {
        Operation operation = Operation.ADD;
        Number result = calculatorService.calculate(operation, 10, 20);
        assertEquals(30.0, result);
    }

    @Test
    public void testCalculate_Subtraction() {
        Operation operation = Operation.SUBTRACT;
        Number result = calculatorService.calculate(operation, 20, 10);
        assertEquals(10.0, result);
    }

    @Test
    public void testCalculate_Multiplication() {
        Operation operation = Operation.MULTIPLY;
        Number result = calculatorService.calculate(operation, 10, 5);
        assertEquals(50.0, result);
    }

    @Test
    public void testCalculate_Division() {
        Operation operation = Operation.DIVIDE;
        Number result = calculatorService.calculate(operation, 20, 4);
        assertEquals(5.0, result);
    }

    @Test
    public void testCalculate_DivideByZero() {
        Operation operation = Operation.DIVIDE;
        assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.calculate(operation, 10, 0);
        });
    }

    @Test
    public void testCalculate_NullOperation() {
        assertThrows(UnsupportedOperationException.class, () -> {
            calculatorService.calculate(null, 10, 20);
        });
    }

    @Test
    public void testCalculate_NullNumber() {
        Operation operation = Operation.ADD;
        assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.calculate(operation, null, 10);
        });
    }

    @Test
    public void testChainOperations_ValidInput() {
        List<OperationRequest> requests = Arrays.asList(
                new OperationRequest("ADD", 10),
                new OperationRequest("MULTIPLY", 2),
                new OperationRequest("SUBTRACT", 5)
        );

        Number result = calculatorService.chainOperations(5, requests);
        assertEquals(25.0, result);
    }

    @Test
    public void testChainOperations_NullInitialValue() {
        List<OperationRequest> requests = Arrays.asList(
                new OperationRequest("ADD", 10)
        );

        assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.chainOperations(null, requests);
        });
    }

    @Test
    public void testChainOperations_NullOperationRequest() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.chainOperations(5, null);
        });
    }

    @Test
    public void testChainOperations_NullOperation() {
        List<OperationRequest> requests = Arrays.asList(
                new OperationRequest(null, 10)
        );

        assertThrows(UnsupportedOperationException.class, () -> {
            calculatorService.chainOperations(5, requests);
        });
    }

    @Test
    public void testChainOperations_NullNumberInRequest() {
        List<OperationRequest> requests = Arrays.asList(
                new OperationRequest("ADD", null)
        );

        assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.chainOperations(5, requests);
        });
    }
}
