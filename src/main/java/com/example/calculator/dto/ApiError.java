package com.example.calculator.dto;

import java.time.Instant;

/**
 * Structured error body for 400 and other failures
 * <p>Example:
 * <pre>
 *  {
 *      "timestamp": "2026-10-28T10:00:00Z",
 *      "status": 400,
 *      "error": "Bad request",
 *      "message": "Cannot divide by zero",
 *      "path": "/api/v1/divide"
 *  }
 * </pre>
 * </p>
 *
 * @param timestamp
 * @param status
 * @param error
 * @param message
 * @param path
 */
public record ApiError(Instant timestamp, int status, String error, String message, String path) {

}
