package com.example.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.closeTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CalculatorController - MockMvc integration tests")
public class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/add returns 200 + correct JSON")
    void addEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/add").param("a", "4").param("b", "6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("add"))
                .andExpect(jsonPath("$.a").value(4.0))
                .andExpect(jsonPath("$.b").value(6.0))
                .andExpect(jsonPath("$.result").value(10.0));
    }

    @Test
    @DisplayName("GET /api/v1/divide with b = 0 returns 400 + error JSON")
    void divideByZeroReturns400() throws Exception {
        mockMvc.perform(get("/api/v1/divide").param("a", "52").param("b", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Cannot divide by zero"))
                .andExpect(jsonPath("$.path").value("/api/v1/divide"));
    }

    @Test
    @DisplayName("GET /api/v1/add with missing parameter returns 400 + error JSON")
    void missingParamReturns400() throws Exception {
        mockMvc.perform(get("/api/v1/add").param("a", "42"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.path").value("/api/v1/add"));
    }

    @Test
    @DisplayName("GET /api/v1/multiply returns 200 + correct product and JSON")
    void multiplyEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/multiply").param("a", "4.5").param("b", "2.5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("multiply"))
                .andExpect(jsonPath("$.a").value(4.5))
                .andExpect(jsonPath("$.b").value(2.5))
                .andExpect(jsonPath("$.result", closeTo(11.25, 1e-9)));
    }

    @Test
    @DisplayName("GET /api/v1/subtract returns 200 + correct diff and JSON")
    void subtractEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/subtract").param("a", "4").param("b", "5.5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("subtract"))
                .andExpect(jsonPath("$.a").value(4))
                .andExpect(jsonPath("$.b").value(5.5))
                .andExpect(jsonPath("$.result", closeTo(-1.5, 1e-9)));
    }

    @Test
    @DisplayName("GET /api/v1/divide returns 200 + correct division and JSON")
    void divideEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/divide").param("a", "10").param("b", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("divide"))
                .andExpect(jsonPath("$.a").value(10))
                .andExpect(jsonPath("$.b").value(4))
                .andExpect(jsonPath("$.result", closeTo(2.5, 1e-9)));
    }

    @Test
    @DisplayName("GET /api/v1/add with invalid parameter returns 400 + error JSON")
    void invalidParamReturns400() throws Exception {
        mockMvc.perform(get("/api/v1/add").param("a", "abc").param("b", "3"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.path").value("/api/v1/add"));
    }

    @Test
    @DisplayName("GET /api/v1/square returns 404")
    void unknownEndpointReturns404() throws Exception {
        mockMvc.perform(get("/api/v1/square"))
                .andExpect(status().isNotFound());
    }

}
