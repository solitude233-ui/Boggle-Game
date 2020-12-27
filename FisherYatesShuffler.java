import java.util.ArrayList;
import java.util.Random;

/**
 * Fisher-Yates shuffling algorithm implementation. This is the same algorithm
 * that is used in the {@code Collections.shuffle} method.
 * 
 * <p>
 * This implementation traverses the list backwards, from the last element up to
 * the second, repeatedly swapping a randomly selected element into the "current
 * position". Elements are randomly selected from the portion of the list that
 * runs from the first element to the current position, inclusive.
 *
 * @param <T> the element type of the list to be shuffled
 */
public class FisherYatesShuffler<T> implements Shuffler<T> {

	private Random rng;

	/**
	 * Initializes this shuffler with a source of randomness.
	 */
	public FisherYatesShuffler() {
		this.rng = new Random();
	}

	/**
	 * Randomly shuffles the elements of the specified list.
	 * 
	 * <p>
	 * This is an example of a generic algorithm. The type of the element in the
	 * list is {@code T} which is a generic name for the actual element type of the
	 * list. For example, if someone called this method with a list of
	 * {@code String} then the type {@code T} would be {@code String}, but there is
	 * no way for the method to know the actual type.
	 * 
	 * <p>
	 * As the method illustrates, you can create variables of type {@code T} such as
	 * {@code ei} and {@code ej} but you don't know the actual type that {@code T}
	 * corresponds to. This means that you can't call any methods using {@code ei}
	 * and {@code ej} except for methods that are defined in
	 * {@code java.lang.Object} (such as {@code equals}, {@code hashCode}, and
	 * {@code toString}.
	 * 
	 * @param t the list to shuffle
	 */
	@Override
	public void shuffle(ArrayList<T> t) {
		for (int i = t.size() - 1; i >= 1; i--) {
			int j = this.rng.nextInt(i + 1);
			T ei = t.get(i);
			T ej = t.get(j);
			t.set(i, ej);
			t.set(j, ei);
		}
	}

	public static void main(String[] args) {
		ArrayList<Integer> t = new ArrayList<>();
		for (int i = 1; i <= 6; i++) {
			t.add(i);
		}
		System.out.println("list: " + t);

		FisherYatesShuffler<Integer> shuffler = new FisherYatesShuffler<>();

		int n = 10;
		for (int i = 0; i < n; i++) {
			shuffler.shuffle(t);
			System.out.println("shuffled: " + t);
		}

	}
}
