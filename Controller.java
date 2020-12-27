import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller for the Boggle app.
 *
 */
public class Controller implements ActionListener {

	private BoggleGame model;
	private View view;

	/**
	 * Creates a controller with no model and no view.
	 */
	public Controller() {
		this.model = null;
		this.view = null;
	}
	
	/**
	 * Sets the controller to use the given model.
	 * 
	 * @pre. <code>model</code> must not be null
	 * 
	 * @param model the model that the controller should use
	 */
	public void setModel(BoggleGame model) {
		this.model = model;
	}
	
	/**
   * Sets the controller to use the given view.
   * 
   * @pre. <code>view</code> must not be null
   * 
   * @param view the view that the controller should use
   */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * Responds to events from the view. This method responds to an
	 * event where the action command is either
	 * <code>BoggleView.ROLL_COMMAND</code> or
	 * <code>BoggleView.SUBMIT_COMMAND</code>.
	 * 
	 * @param event an event emitted by the view
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command.equals(View.ROLL_COMMAND)) {
			this.model.shuffleAndRoll();
			this.view.setDice(this.model.getDice());
		}
		else if (command.equals(View.SUBMIT_COMMAND)) {
			String word = this.view.getWord().toLowerCase();
			boolean isOk = this.model.isABoggleWord(word);
			this.view.setWordIsValid(isOk);
			if (isOk) {
				this.view.setSuggestedWords(this.model.wordsSimilarTo(word));
			}
		}
	}
	
	  
}
