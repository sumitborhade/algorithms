package com.example.algorithms;

public class BinaryNumbersSum {

	public String getBinaryNumbersSum(String binaryNumberStringOne, String binaryNumberStringTwo) {
		Long binaryNumberOne = Long.parseLong(binaryNumberStringOne, 2);
		Long binaryNumberTwo = Long.parseLong(binaryNumberStringTwo, 2);
		
		long sum = binaryNumberOne + binaryNumberTwo;
		
		return Long.toBinaryString(sum);
	}
}
