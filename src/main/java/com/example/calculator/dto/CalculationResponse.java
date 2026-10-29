package com.example.calculator.dto;

/**
 * The exact JSON shape required:
 * <pre>
 * { "operation": "add",
 *   "a": 2,
 *   "b": 3,
 *   "result": 5
 * }
 * </pre>
 *
 * @param operation
 * @param a
 * @param b
 * @param result
 */
public record CalculationResponse(String operation, double a, double b, double result) {

}
