package com.example.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/* @SpringBootTest allows your test to verify that the application context can be initialized with the required
	configuration and components.
*/
@SpringBootTest
class CalculatorApplicationTests {

	/* This is empty, because we are not testing arithmetic here, it will just check whether "Application context"
		loads correctly or not, suppose if we forget to specify @Service in CalculatorService and CalculatorController
		requires it to be initialized using constructor injection then initialization will fail.

		If initialization fails, the test fails even though the method itself contains no statements.
		In this situation contextLoads() fails otherwise the test passes
	 */
	@Test
	void contextLoads() {
	}

}
