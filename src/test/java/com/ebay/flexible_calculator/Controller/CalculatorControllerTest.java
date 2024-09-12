package com.ebay.flexible_calculator.Controller;
import com.ebay.flexible_calculator.Service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testValidCalculation() throws Exception {
        String requestBody = "{\"operation\": \"ADD\", \"num1\": 5, \"num2\": 3}";

        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(8.0));
    }

    @Test
    public void testUnsupportedOperationException() throws Exception {
        String requestBody = "{\"operation\": \"XXX\", \"num1\": 5, \"num2\": 3}";

        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDivisionByZero() throws Exception {
        String requestBody = "{\"operation\": \"DIVIDE\", \"num1\": 5, \"num2\": 0}";

        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testChainedOperations() throws Exception {
        String requestBody = "{\"initialVal\": 0 ," +
                "\"operations\":["
                + "{\"operation\": \"ADD\", \"num\": 5},"
                + "{\"operation\": \"MULTIPLY\", \"num\": 2},"
                + "{\"operation\": \"DIVIDE\", \"num\": 4}"
                + "]}";

        mockMvc.perform(post("/api/v1/calculator/chain")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(2.5));
    }

    @Test
    public void testInvalidOperationInChainedOperations() throws Exception {
        String requestBody = "{initialVal: 0 ," +
                "\"operations\":["
                + "{\"operation\": \"ADD\", \"num\": 5},"
                + "{\"operation\": \"XXX\", \"num\": 2},"
                + "{\"operation\": \"DIVIDE\", \"num\": 4}"
                + "]}";

        mockMvc.perform(post("/api/v1/calculator/chain")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testNullOperand() throws Exception {
        String requestBody = "{\"operation\": \"ADD\", \"num1\": null, \"num2\": 3}";

        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testVeryLargeNumberForAddition() throws Exception {
        String requestBody = "{\"operation\": \"ADD\", \"num1\": \"9999999999999999999999999999999999999999999\", \"num2\": 1}";

        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidNumberFormatForSubtraction() throws Exception {
        String requestBody = "{\"operation\": \"SUBTRACT\", \"num1\": \"abc123\", \"num2\": 1}";

        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testVeryLargeNumberForMultiplication() throws Exception {
        String requestBody = "{\"operation\": \"MULTIPLY\", \"num1\": \"9999999999999999999999999999999999999999999\", \"num2\": 2}";

        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidNumberFormatForDivision() throws Exception {
        String requestBody = "{\"operation\": \"DIVIDE\", \"num1\": \"not_a_number\", \"num2\": 1}";

        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testCalculatorStateReset() throws Exception {
        // Perform an operation
        String requestBody1 = "{\"operation\": \"ADD\", \"num1\": 5, \"num2\": 3}";
        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType("application/json")
                        .content(requestBody1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(8.0));

        // Perform another operation to ensure state reset
        String requestBody2 = "{\"operation\": \"SUBTRACT\", \"num1\": 10, \"num2\": 2}";
        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType("application/json")
                        .content(requestBody2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(8.0)); // result should not carry over
    }
}
