package com.ebay.flexible_calculator.Controller;

import com.ebay.flexible_calculator.Enum.Operation;
import com.ebay.flexible_calculator.Payload.CalculationRequest;
import com.ebay.flexible_calculator.Payload.CalculationResult;
import com.ebay.flexible_calculator.Service.CalculatorService;
import com.ebay.flexible_calculator.Service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/calculator")
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private ValidationService validationService;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody CalculationRequest request) {
        if(validationService.validateCalculateRequest(request)) {
            Number result = 0;
            Operation op = validationService.validateAndParseOperation(request.getOperation());
            result = calculatorService.calculate(op, request.getNum1(), request.getNum2());
            return new ResponseEntity<>(new CalculationResult(result), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/chain")
    public ResponseEntity<?> chainCalculate(@RequestBody CalculationRequest request) {
        if(validationService.validateChainRequest(request)) {
            Number result = 0;
            result = calculatorService.chainOperations(result, request.getOperations());
            return new ResponseEntity<>(new CalculationResult(result), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
