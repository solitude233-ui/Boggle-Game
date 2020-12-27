import java.util.List;
import java.util.SortedSet;
import java.util.ArrayList;

public class BoggleGame {
	/**
	 * The number of dice in a Boggle game.
	 */
	public static final int NUMBER_OF_DICE = 16;

	/**
	 * The letters on the 16 boggle dice (strings for one die on each line).
	 */
	private static final String[][] LETTERS = { { "A", "A", "E", "E", "G", "N" }, { "E", "L", "R", "T", "T", "Y" },
			{ "W", "A", "O", "O", "T", "T" }, { "A", "B", "B", "J", "O", "O" }, { "E", "H", "R", "T", "V", "W" },
			{ "C", "I", "M", "O", "T", "U" }, { "D", "I", "S", "T", "T", "Y" }, { "E", "I", "O", "S", "S", "T" },
			{ "Y", "D", "E", "L", "R", "V" }, { "A", "C", "H", "O", "P", "S" }, { "U", "H", "I", "M", "N", "QU" },
			{ "E", "E", "I", "N", "S", "U" }, { "E", "E", "G", "H", "N", "W" }, { "A", "F", "F", "K", "P", "S" },
			{ "H", "L", "N", "N", "R", "Z" }, { "X", "D", "E", "I", "L", "R" } };

	/**
	 * The 16 boggle dice.
	 */
	private ArrayList<BoggleDie> dice;

	/**
	 * The dictionary.
	 */
	private BoggleDictionary dictionary;

	/**
	 * The shuffler. Students should use an instance of their Shuffler class.
	 */
	private Shuffler<BoggleDie> shuffler;

	/**
	 * Initializes a Boggle game by creating the 16 standard boggle dice and a
	 * dictionary.
	 * 
	 */
	public BoggleGame() {
		this.shuffler = new FisherYatesShuffler<>();
		this.dictionary = new BoggleDictionary();
		this.dice = new ArrayList<BoggleDie>();
		for (int i = 0; i < BoggleGame.NUMBER_OF_DICE; i++) {
			this.dice.add(new BoggleDie(BoggleGame.LETTERS[i]));
		}
	}

	/**
	 * Returns a new list of the 16 dice in their current state. The order of dice
	 * is guaranteed to be stable between calls to <code>shuffleAndRoll</code>; in
	 * other words, all lists returned by this method are equal between calls to
	 * <code>shuffleAndRoll</code> (assuming that the returned lists and the dice in
	 * the lists are not modified).
	 * 
	 * <p>
	 * Clients are unable to modify the game dice using the returned list; i.e.,
	 * modifying the returned list has no effect on the dice held by the
	 * {@code BoggleGame} object, and modifying the dice in the returned list has no
	 * effect on the dice held by the {@code BoggleGame} object.
	 * 
	 * @return a list of the 16 dice in their current state; modifying the list or
	 *         the dice in the list does not modify the state of the Boggle dice
	 */
	public List<BoggleDie> getDice() {
		List<BoggleDie> result = new ArrayList<BoggleDie>();
		for (BoggleDie d : this.dice) {
			result.add(new BoggleDie(d));
		}
		return result;
	}

	/**
	 * Randomly shuffles the order of the dice and rolls all of dice. This simulates
	 * the shaking of the dice in the physical version of the game.
	 * 
	 */
	public void shuffleAndRoll() {
		this.shuffler.shuffle(this.dice);
		for (BoggleDie d : this.dice) {
			d.roll();
		}
	}

	/**
	 * Returns true if the specified string is a legal Boggle word, and false
	 * otherwise. A legal Boggle word is at least 3 letters long and can be found in
	 * the dictionary.
	 * 
	 * @param s a string
	 * @return true if the specified string is a legal Boggle word, and false
	 *         otherwise
	 */
	public boolean isABoggleWord(String s) {
		return s.length() > 2 && this.dictionary.contains(s.toLowerCase());
	}

	/**
	 * Returns a sorted set of all of the words in the dictionary that are similar
	 * to the specified word. Two words are considered similar if they start with
	 * the same letter and their Hamming distance is two or less.
	 * 
	 * @param word a word
	 * @return a sorted set of all of the words in the dictionary that are similar
	 *         to the specified word
	 */
	public SortedSet<String> wordsSimilarTo(String word) {
		return this.dictionary.wordsSimilarTo(word);
	}

	/**
	 * Entry point for the Boggle application. Creates a playable version of the
	 * Boggle game.
	 * 
	 * <p>
	 * DON'T MODIFY THIS METHOD.
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		Controller c = new Controller();
		BoggleGame m = new BoggleGame();
		View v = new View(c);
		c.setModel(m);
		c.setView(v);
		v.setVisible(true);
	}
}
