package com.ebay.flexible_calculator.Service;
import com.ebay.flexible_calculator.Enum.Operation;
import com.ebay.flexible_calculator.Exception.BadParameterException;
import com.ebay.flexible_calculator.Payload.CalculationRequest;
import com.ebay.flexible_calculator.Payload.OperationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

public class ValidationServiceTest {

    @InjectMocks
    private ValidationService validationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateCalculateRequest_ValidInput() {
        CalculationRequest request = new CalculationRequest();
        request.setOperation("ADD");
        request.setNum1(10);
        request.setNum2(20);

        assertTrue(validationService.validateCalculateRequest(request));
    }

    @Test
    public void testValidateCalculateRequest_NullOperation() {
        CalculationRequest request = new CalculationRequest();
        request.setOperation(null);
        request.setNum1(10);
        request.setNum2(20);

        BadParameterException exception = assertThrows(BadParameterException.class, () -> {
            validationService.validateCalculateRequest(request);
        });

        assertEquals("Invalid operation: null. Supported operations are: ADD, SUBTRACT, MULTIPLY, DIVIDE.", exception.getMessage());
    }

    @Test
    public void testValidateCalculateRequest_NullNumber() {
        CalculationRequest request = new CalculationRequest();
        request.setOperation("SUBTRACT");
        request.setNum1(null);
        request.setNum2(20);

        BadParameterException exception = assertThrows(BadParameterException.class, () -> {
            validationService.validateCalculateRequest(request);
        });

        assertEquals("Invalid input: Numbers cannot be null", exception.getMessage());
    }

    @Test
    public void testValidateCalculateRequest_DivisionByZero() {
        CalculationRequest request = new CalculationRequest();
        request.setOperation("DIVIDE");
        request.setNum1(10);
        request.setNum2(0);

        BadParameterException exception = assertThrows(BadParameterException.class, () -> {
            validationService.validateCalculateRequest(request);
        });

        assertEquals("Invalid input: Divisor cannot be 0", exception.getMessage());
    }

    @Test
    public void testValidateChainRequest_ValidInput() {
        CalculationRequest request = new CalculationRequest();
        List<OperationRequest> operations = new ArrayList<>();
        operations.add(new OperationRequest("ADD", 10));
        operations.add(new OperationRequest("SUBTRACT", 5));
        request.setOperations(operations);

        assertTrue(validationService.validateChainRequest(request));
    }

    @Test
    public void testValidateChainRequest_EmptyOperations() {
        CalculationRequest request = new CalculationRequest();
        request.setOperations(new ArrayList<>());

        BadParameterException exception = assertThrows(BadParameterException.class, () -> {
            validationService.validateChainRequest(request);
        });

        assertEquals("Invalid operation list: Operation list cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testValidateOperation_ValidOperation() {
        Operation operation = validationService.validateAndParseOperation("ADD");
        assertEquals(Operation.ADD, operation);
    }

    @Test
    public void testValidateOperation_InvalidOperation() {
        BadParameterException exception = assertThrows(BadParameterException.class, () -> {
            validationService.validateAndParseOperation("INVALID");
        });

        assertEquals("Invalid operation: INVALID. Supported operations are: ADD, SUBTRACT, MULTIPLY, DIVIDE.", exception.getMessage());
    }
}
