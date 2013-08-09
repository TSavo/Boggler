package com.tsavo.boggler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BogglerTest {

	@Test
	public void testBoard() {
		BoggleBoard board = new BoggleBoard("aaaabbbbccccdddd");
		List<String> results = board.getWordsContained(Arrays.asList("abcd", "1234", "abc", "aab", "aaab", "bdca", "dcba", "dcbc", "aaaabbbbccccdddd", "abcddcbaabcddcba", "aaaabbbbccccdddde"));
		assertTrue(results.contains("abcd"));
		assertFalse(results.contains("1234")); // not in there at all
		assertTrue(results.contains("abc"));
		assertTrue(results.contains("aab"));
		assertTrue(results.contains("aaab"));
		assertFalse(results.contains("bdca")); // d can't touch b
		assertTrue(results.contains("dcba"));
		assertTrue(results.contains("dcbc"));
		assertTrue(results.contains("aaaabbbbccccdddd")); // longest road test
		assertTrue(results.contains("abcddcbaabcddcba")); // alt longest road
		assertFalse(results.contains("aaaabbbbccccdddde"));// not in there, tests
																												// the 'runs out of
																												// room' edge case

	}

}
