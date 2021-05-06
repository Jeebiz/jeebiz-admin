/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sequence;

import org.junit.jupiter.api.Test;

import net.jeebiz.boot.api.sequence.Sequence;

public class SequenceTest {

	protected Sequence sequence = new Sequence(0);
	
	@Test
	public void test() {
		for (int i = 0; i < 1000; i++) {
			System.out.println(sequence.nextId());	
		}
	}
	
}
