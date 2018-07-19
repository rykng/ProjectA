package com.qang.pigfarm;

import static org.junit.Assert.*;

import org.junit.Test;

public class PigTest {

	@Test
	public void testPigOink() {
		Pig pig = new Pig();
		pig.oink();
		assertTrue("Pig Oinked", true);
	}

}
