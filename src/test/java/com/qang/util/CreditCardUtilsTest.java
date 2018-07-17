package com.qang.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class CreditCardUtilsTest {
	
	
	@Test
	public void testValidVisaCard() {
		String cardNumber = CreditCardUtils.generateValidCCNumByBinPrefix("4231");
		System.out.println("CardNumber Visa => " + cardNumber);
		assertTrue(cardNumber.startsWith("423"));
	}

}
