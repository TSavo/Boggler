package com.tsavo.boggler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void printWords(List<String> someWords) {
		for (String word : someWords) {
			System.out.println(word);
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println("Please enter the board on a single line.");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String boardFormat = br.readLine();
		BoggleBoard board = new BoggleBoard(boardFormat);
		InputStream wordInputStream;
		List<String> words = new ArrayList<>();
		String line;
		wordInputStream = new FileInputStream("words.txt");
		try (BufferedReader brf = new BufferedReader(new InputStreamReader(wordInputStream, Charset.forName("UTF-8")))) {
			while ((line = brf.readLine()) != null) {
				words.add(line);
			}
		}
		List<String> foundWords = board.getWordsContained(words);
		for (String word : foundWords) {
			System.out.println(word);
		}
	}
}
