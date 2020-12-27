import java.io.InputStream;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

/**
 * The {@code BoggleDictionary} class initiates a list that represents an
 * English dictionary consists of words all in lower-case and sorted order. The
 * class also allows user to get the total number of words currently in the
 * dictionary, add a word to the dictionary, checks the dictionary contains a
 * word, calculate the hamming distance between two words and checks to see if
 * any words in the dictionary is similar to a given word.
 */
public class BoggleDictionary {

	// add your fields here
	private List<String> wordDict;

	/**
	 * Reads the dictionary file and stores the words from the file in the list
	 * this.wordDict. The words in this file are in all lower case and sorted.
	 * 
	 * <p>
	 * The dictionary file is named dictionary.txt and needs to be located in the
	 * same package as this file.
	 * 
	 * @throws RuntimeException if dictionary.txt cannot be found
	 * 
	 */
	private final void readDictionary() {
		InputStream in = this.getClass().getResourceAsStream("dictionary.txt");
		if (in == null) {
			throw new RuntimeException("dictionary.txt is missing");
		}
		Scanner dictionaryInput = new Scanner(in);
		while (dictionaryInput.hasNext()) {
			String word = dictionaryInput.next();
			this.wordDict.add(word.trim());
		}
		dictionaryInput.close();
	}

	/**
	 * Initializes the dictionary by calling {@code readDictionary} to read all
	 * words from a file into the list, all words in the dictionary are in lower
	 * case and sorted.
	 */
	public BoggleDictionary() {
		this.wordDict = new ArrayList<String>();
		this.readDictionary();
	}

	/**
	 * Return the current size of the dictionary.
	 * 
	 * @return the current size of the dictionary.
	 */
	public int size() {
		return this.wordDict.size();
	}

	/**
	 * First checks to see if the word is in the dictionary by calling
	 * {@code contains()} method and if it is not, add it to the dictionary.
	 * 
	 * @param word the string to be checked to add to the dictionary.
	 */
	public void add(String word) {
		if (!this.wordDict.contains(word)) {
			this.wordDict.add(word);
		}
	}

	/**
	 * This method performs a binary search on the sorted dictionary to check if the
	 * dictionary contains the given word. The overall complexity of this method is
	 * O(log n) which is much more efficient than checking every entries in the
	 * dictionary which has the complexity of O(n).
	 * 
	 * @param word the string to be checked if it is contained in the dictionary.
	 * @return true if the dictionary contains the word and false otherwise.
	 */
	public boolean contains(String word) {
		int low = 0;
		int high = this.size() - 1;
		while (low <= high) {
			int mid = (high + low) / 2;
			if (this.wordDict.get(mid).equals(word)) {
				return true;
			} else if (this.wordDict.get(mid).compareTo(word) > 0) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return false;
	}

	/**
	 * Calculates the hamming distance between two words by first making sure they
	 * are equal length otherwise just return 10 to make sure they have a rather
	 * large hamming distance. Then checks if they have the same character at the
	 * same index, if not, then the hamming distance is increased by 1.
	 * 
	 * @param s string 1 to be compared.
	 * @param t string 2 to be compared.
	 * @return cnt the hamming distance between s and t.
	 */
	public static int hammingDistance(String s, String t) {
		int cnt = 0;
		if (s.length() == t.length()) {
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) != t.charAt(i)) {
					cnt++;
				}
			}
			return cnt;
		}
		return 10;
	}

	/**
	 * This method checks to see if there are any words in the dictionary that is
	 * similar to the given word. For two words to be similar, their starting
	 * letters have to be the same and their hamming distance has to be 1 or less.
	 * This method can be broken down into 3 parts:
	 * 
	 * <p>
	 * First, it checks to see if the given word begins with "a" if it does, then it
	 * jump straight to the last step with j = 0 because in a dictionary words
	 * starting with letter "a" are at the beginning. But otherwise performs a
	 * binary search to get the index i of a random word in the dictionary which has
	 * the same starting letter as the given parameter.
	 * 
	 * <p>
	 * Then, it searches for the location of first appearance of the word in the
	 * dictionary which has the same starting letter as the given word by checking
	 * to see if the word comes before it has the right starting letter. The index
	 * of the first word with the same starting letter is stored in j.
	 * 
	 * <p>
	 * Lastly, A sorted set called similar is declared and from the position of j,
	 * it checks to see if every word starting at j is a similar word to the given
	 * word by calling {@code hammingDistance()} and all similar words are added to
	 * the set similar. As the index increases, the process stops if two words don't
	 * have the same starting letter anymore.
	 * 
	 * <p>
	 * Overall, the techniques used to implement this method is more efficient that
	 * O(n) because it doesn't need to check every entries in the dictionary.
	 * 
	 * @param word the word to be compared with for similarity.
	 * @return SortedSet similar which contains all similar words to the given word.
	 */
	public SortedSet<String> wordsSimilarTo(String word) {
		SortedSet<String> similar = new TreeSet<String>();
		int low = 0;
		int i = 0, j = 0;
		int high = this.wordDict.size() - 1;

		if (word.startsWith("a")) {
			j = 0;
		} else {
			while (low <= high) {
				int mid = (high + low) / 2;
				if (this.wordDict.get(mid).charAt(0) == word.charAt(0)) {
					i = mid;
					break;
				} else if (this.wordDict.get(mid).charAt(0) > word.charAt(0)) {
					high = mid - 1;
				} else {
					low = mid + 1;
				}
			}

			while (true) {
				if (this.wordDict.get(i).charAt(0) != word.charAt(0)) {
					j = i + 1;
					break;
				}
				i--;
			}
		}

		while (this.wordDict.get(j).charAt(0) == word.charAt(0)) {
			if (hammingDistance(word, this.wordDict.get(j)) <= 1) {
				similar.add(this.wordDict.get(j));
			}
			j++;
		}
		return similar;
	}

}