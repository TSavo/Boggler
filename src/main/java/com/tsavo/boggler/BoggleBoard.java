package com.tsavo.boggler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class for the Boggle Board, a 4x4 grid of letters
 * 
 * @author TSavo
 * 
 */

public class BoggleBoard {

	/**
	 * A single character on the board
	 * 
	 */
	public static class BoggleBoardCharacter {
		String character;
		boolean touched = false;
		int row;
		int column;
		Map<String, List<BoggleBoardCharacter>> adjacentCharactersMap = new HashMap<>();

		BoggleBoardCharacter(String aCharacter, int aRow, int aCol) {
			character = aCharacter;
			row = aRow;
			column = aCol;
		}

		/**
		 * Cloning constructor
		 * 
		 * @param aBoardCharacter
		 *          The character to clone from.
		 */
		BoggleBoardCharacter(BoggleBoardCharacter aBoardCharacter) {
			character = aBoardCharacter.getCharacter();
			row = aBoardCharacter.getRow();
			column = aBoardCharacter.getColumn();
			touched = aBoardCharacter.isTouched();
		}

		/**
		 * Find all the adjacent characters that satisfy the next part of the word
		 * 
		 * @param aCharacter
		 *          A single character to search the untouched adjacent characters
		 *          for
		 * @return A list of Characters that contain the next character in our word
		 */
		public List<BoggleBoardCharacter> getUntouchedAdjacentCharacters(String aCharacter) {
			List<BoggleBoardCharacter> adjacent = new ArrayList<>();
			List<BoggleBoardCharacter> matches = adjacentCharactersMap.get(aCharacter);
			if (matches == null) {
				return adjacent;
			}
			for (BoggleBoardCharacter adj : matches) {
				if (!adj.isTouched()) {
					adjacent.add(adj);
				}
			}
			return adjacent;
		}

		/**
		 * Adds a BoggleBoardCharacter in the adjacent list
		 * 
		 * @param aCharacter
		 *          The BoggleBoardCharacter that is adjacent to this one
		 */
		public void addAdjacentCharacter(BoggleBoardCharacter aCharacter) {
			List<BoggleBoardCharacter> list = adjacentCharactersMap.get(aCharacter.getCharacter());
			if (list == null) {
				list = new ArrayList<>();
				adjacentCharactersMap.put(aCharacter.getCharacter(), list);
			}
			list.add(aCharacter);
		}

		public String getCharacter() {
			return character;
		}

		public void setCharacter(String character) {
			this.character = character;
		}

		public boolean isTouched() {
			return touched;
		}

		public void setTouched(boolean touched) {
			this.touched = touched;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getColumn() {
			return column;
		}

		public void setCol(int col) {
			this.column = col;
		}

	}

	/**
	 * Our 4x4 grid of characters
	 */
	List<List<BoggleBoardCharacter>> characters = new ArrayList<>();

	/**
	 * Initial state constructor
	 * 
	 * @param aBoardState
	 *          The initial board state in a single line, left to right, top to
	 *          bottom.
	 */
	public BoggleBoard(String aBoardState) {
		aBoardState = aBoardState.trim();
		if (aBoardState.length() != 16) {
			throw new IllegalArgumentException("Board size must be 4x4 (16 characters)!");
		}
		for (int x = 0; x < 4; x++) {
			List<BoggleBoardCharacter> temp = new ArrayList<>();
			for (int y = 0; y < 4; y++) {
				temp.add(new BoggleBoardCharacter(String.valueOf(aBoardState.charAt(((4 * x) + y))), x + 1, y + 1));
			}
			characters.add(temp);
		}
	}

	/**
	 * Cloning constructor
	 * 
	 * @param aBoard
	 *          The board to clone from
	 */
	public BoggleBoard(BoggleBoard aBoard) {
		for (List<BoggleBoardCharacter> bList : aBoard.characters) {
			List<BoggleBoardCharacter> temp = new ArrayList<>();
			for (BoggleBoardCharacter b : bList) {
				temp.add(new BoggleBoardCharacter(b));
			}
			characters.add(temp);
		}
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				BoggleBoardCharacter aCharacter = characters.get(x).get(y);
				int leftCol = y - 1;
				int rightCol = y + 1;
				int aboveRow = x - 1;
				int belowRow = x + 1;

				addAdjacentLetter(aCharacter, aboveRow, leftCol);
				addAdjacentLetter(aCharacter, aboveRow, y);
				addAdjacentLetter(aCharacter, aboveRow, rightCol);

				addAdjacentLetter(aCharacter, x, leftCol);
				// addAdjacentLetter(aCharacter, aRow, aCol);
				addAdjacentLetter(aCharacter, x, rightCol);

				addAdjacentLetter(aCharacter, belowRow, leftCol);
				addAdjacentLetter(aCharacter, belowRow, y);
				addAdjacentLetter(aCharacter, belowRow, rightCol);
			}
		}

	}

	/**
	 * Given a list of Strings to look for, return a list of Strings containing
	 * all the words found on the board.
	 * 
	 * @param someWords
	 *          A list of Strings to search for.
	 * @return A list of words found on the board.
	 */
	public List<String> getWordsContained(List<String> someWords) {
		List<String> foundWords = new ArrayList<>();
		for (String word : someWords) {
			if (contains(word)) {
				foundWords.add(word);
			}
		}
		Collections.sort(foundWords);
		return foundWords;
	}

	/**
	 * Given a word to search for, return true if the word is found on the board
	 * 
	 * @param aWord
	 *          A word to search for
	 * @return true if the word is on the board, otherwise false
	 */
	public boolean contains(String aWord) {
		for (List<BoggleBoardCharacter> lists : characters) {
			for (BoggleBoardCharacter character : lists) {
				if (String.valueOf(aWord.charAt(0)).equals(character.getCharacter()) && remainingWordSolvable(character, aWord, 1)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Given a row and column index, return the character at that row and column
	 * 
	 * @param aRow
	 *          Row index
	 * @param aCol
	 *          Column index
	 * @return The character at that row and column
	 */
	public BoggleBoardCharacter getLetter(int aRow, int aCol) {
		if ((aRow < 1) || (aRow > 4) || (aCol < 1) || (aCol > 4)) {
			return null;
		}

		return characters.get(aRow - 1).get(aCol - 1);
	}

	/**
	 * Given a specific character on the board, a word we're search for, and where
	 * we are in the word, this will recursively call itself incrementing the
	 * position in the word each time, and duplicating the state of the system in
	 * order to explore all possible paths without tainting the path for another
	 * search, and search each adjacent character until we find what we're looking
	 * for.
	 * 
	 * @param aCharacter
	 *          The current character we're on
	 * @param aWord
	 *          the word we're looking for
	 * @param aWordPosition
	 *          The index into the word we're in
	 * @return true if we reach the end of the word, or our recursive call reaches
	 *         the end of the word, otherwise false
	 */
	public boolean remainingWordSolvable(BoggleBoardCharacter aCharacter, String aWord, int aWordPosition) {
		if ((aWordPosition) == aWord.length())
			return true;

		BoggleBoard duplicateBoard = new BoggleBoard(this);
		BoggleBoardCharacter duplicateLetter = duplicateBoard.getLetter(aCharacter.getRow(), aCharacter.getColumn());
		int currPosition = aWordPosition + 1;

		duplicateLetter.setTouched(true);

		List<BoggleBoardCharacter> adj = duplicateLetter.getUntouchedAdjacentCharacters(String.valueOf(aWord.charAt(aWordPosition)));

		if (adj.size() > 0) {
			for (BoggleBoardCharacter adjacent : adj) {
				if (duplicateBoard.remainingWordSolvable(adjacent, aWord, currPosition)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Add an adjacent letter to our list of adjacents
	 * 
	 * @param aCharacter
	 *          the adjacent character
	 * @param aRow
	 *          the row index
	 * @param aColumn
	 *          the column index
	 */
	public void addAdjacentLetter(BoggleBoardCharacter aCharacter, int aRow, int aColumn) {
		if ((aRow > -1) && (aRow < 4) && (aColumn > -1) && (aColumn < 4)) {
			aCharacter.addAdjacentCharacter(characters.get(aRow).get(aColumn));
		}
	}

}
