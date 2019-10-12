package com.example.algorithms;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BinaryNumbersSumTest {

	@Test
	public void testHappyPathScenario() {
		BinaryNumbersSum obj = new BinaryNumbersSum();
		
		String binaryNumberStringOne = "10101010";
		String binaryNumberStringTwo = "11001100";

		String stringBinarySum = obj.getBinaryNumbersSum(binaryNumberStringOne, binaryNumberStringTwo);
		assertEquals("101110110", stringBinarySum);
	}
}
