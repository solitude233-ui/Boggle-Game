import java.util.ArrayList;

public interface Shuffler<T> {

	/**
	 * Randomly permutes the elements of the specified list.
	 * 
	 * @param t a list to shuffle
	 */
	public void shuffle(ArrayList<T> t);
	
}
