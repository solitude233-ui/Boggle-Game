import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * The view for the Boggle app.
 *
 */
public class View extends JFrame implements ActionListener {

	/**
	 * The string representing the clear command. The view listens for its own clear
	 * event.
	 */
	public static final String CLEAR_COMMAND = "clear";

	/**
	 * The string representing the roll command.
	 */
	public static final String ROLL_COMMAND = "roll";

	/**
	 * The string repesenting the submit command.
	 */
	public static final String SUBMIT_COMMAND = "submit";

	private List<JButton> diceButtons;
	private JTextField word;
	private Set<JButton> usedButtons;
	private JTextArea correctWords;
	private JTextArea incorrectWords;
	private JTextArea suggestedWords;

	/**
	 * Create the Boggle user interface. Please see the lab for a detailed
	 * description of the user interface.
	 * 
	 * @param controller the controller that listens for submit and roll events
	 */
	public View(Controller controller) {
		super("Boggle");
		this.diceButtons = new ArrayList<JButton>();
		this.usedButtons = new HashSet<JButton>();

		JPanel contentPanel = new JPanel();
		JPanel validWordsPanel = this.makeValidWordsPanel();
		JPanel invalidWordsPanel = this.makeInvalidWordsPanel();
		JPanel suggestedWordsPanel = this.makeSuggestedWordsPanel();
		JPanel middlePanel = this.makeMiddlePanel(controller);
		contentPanel.add(validWordsPanel);
		contentPanel.add(middlePanel);
		contentPanel.add(invalidWordsPanel);
		contentPanel.add(suggestedWordsPanel);
		this.setContentPane(contentPanel);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Creates the panel that contains the buttons representing the Boggle dice.
	 * 
	 * @return the <code>JPanel</code> that contains the buttons representing the
	 *         Boggle dice.
	 * 
	 */
	private JPanel makeDicePanel() {
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 32);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(4, 4));
		p.setMaximumSize(new Dimension(450, 450));
		for (int i = 0; i < 16; i++) {
			JButton b = new JButton("" + i);
			b.setPreferredSize(new Dimension(100, 100));
			b.setMaximumSize(b.getSize());
			b.setFont(font);
			b.setBackground(Color.WHITE);
			b.setActionCommand("" + i);
			b.addActionListener(this);
			p.add(b);
			this.diceButtons.add(b);
		}
		return p;
	}

	/**
	 * Returns the buttons surrounding the button representing the die that was last
	 * selected by the user. These are the buttons that could legally be chosen next
	 * by the user when forming a word.
	 * 
	 * @param idx the index of the button representing the die that was last
	 *            selected by the user
	 * @return the buttons surrounding the last selected die
	 */
	private List<JButton> findNeighbors(int idx) {
		List<JButton> neighbors = new ArrayList<JButton>();
		final int row = idx / 4;
		final int col = idx % 4;
		final int minRow = Math.max(0, row - 1);
		final int maxRow = Math.min(3, row + 1);
		final int minCol = Math.max(0, col - 1);
		final int maxCol = Math.min(3, col + 1);
		for (int i = minRow; i <= maxRow; i++) {
			for (int j = minCol; j <= maxCol; j++) {
				int n = i * 4 + j;
				if (n != idx) {
					neighbors.add(this.diceButtons.get(n));
				}
			}
		}
		return neighbors;
	}

	/**
	 * Disable all of the buttons representing the dice.
	 */
	private void disableAllDiceButtons() {
		for (JButton b : this.diceButtons) {
			b.setEnabled(false);
		}
	}

	/**
	 * Enable all of the buttons representing the dice.
	 */
	private void enableAllDiceButtons() {
		for (JButton b : this.diceButtons) {
			b.setEnabled(true);
			b.setBackground(Color.WHITE);
		}
	}

	/**
	 * Responds to events from the view. This method responds to an event where the
	 * action command is either <code>BoggleView.CLEAR_COMMAND</code>,
	 * <code>BoggleView.ROLL_COMMAND</code>, or
	 * <code>BoggleView.SUBMIT_COMMAND</code>.
	 * 
	 * @param event an event emitted by the view
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command.equals(CLEAR_COMMAND)) {
			this.clearCurrentWord();
		} else if (command.equals(ROLL_COMMAND)) {
			this.clearCorrectWords();
			this.clearIncorrectWords();
			this.clearSuggestedWords();
			this.clearCurrentWord();
		} else {
			try {
				int d = Integer.parseInt(command);
				JButton b = this.diceButtons.get(d);
				b.setBackground(Color.BLUE);
				this.word.setText(this.word.getText() + b.getText());
				this.usedButtons.add(b);
				this.disableAllDiceButtons();
				List<JButton> neighbors = findNeighbors(d);
				for (JButton n : neighbors) {
					if (!this.usedButtons.contains(n)) {
						n.setEnabled(true);
					}
				}
			} catch (NumberFormatException ex) {

			}
		}
	}

	/**
	 * Creates the panel for the valid submitted words.
	 * 
	 * @return the panel for the valid submitted words with all of its necessary
	 *         components
	 */
	private JPanel makeValidWordsPanel() {
		// create the panel
		JPanel p = new JPanel();

		// set the layout for the panel to use a BoxLayout;
		// BoxLayout stacks its components vertically or horizontally
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		// create a label for the list of correct words and add it to the panel
		JLabel label = new JLabel("Correct Words");
		p.add(label);

		// create the list of correct words, remove the ability for the user to
		// edit the list, and add it to the panel
		this.correctWords = new JTextArea(30, 16);
		this.correctWords.setEditable(false);
		p.add(this.correctWords);

		return p;
	}

	/**
	 * Creates the panel for the invalid submitted words.
	 * 
	 * @return the panel for the invalid submitted words with all of its necessary
	 *         components
	 */
	private JPanel makeInvalidWordsPanel() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		JLabel label = new JLabel("Incorrect Words");
		p.add(label);
		this.incorrectWords = new JTextArea(30, 16);
		this.incorrectWords.setEditable(false);
		p.add(this.incorrectWords);
		return p;
	}
	
	/**
	 * Creates the panel for the suggested words.
	 * 
	 * @return the panel for the suggested words with all of its necessary
	 *         components
	 */
	private JPanel makeSuggestedWordsPanel() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		JLabel label = new JLabel("Suggested Words");
		p.add(label);
		this.suggestedWords = new JTextArea(30, 16);
		this.suggestedWords.setEditable(false);
		JScrollPane areaScrollPane = new JScrollPane(this.suggestedWords);
		areaScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p.add(areaScrollPane);
		return p;
		
		
	}

	/**
	 * Creates the middle panel.
	 * 
	 * @return the middle <code>JPanel</code> with all of its necessary components
	 */
	private JPanel makeMiddlePanel(Controller controller) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		JPanel dicePanel = this.makeDicePanel();
		p.add(dicePanel);
		JPanel controlPanel = this.makeControlPanel(controller);
		p.add(controlPanel);
		return p;
	}

	/**
	 * Creates the panel that contains the clear, submit, and re-roll buttons, and
	 * the text field for the word.
	 * 
	 * @return the <code>JPanel</code> that contains the controls below the dice
	 * 
	 */
	private JPanel makeControlPanel(Controller controller) {
		JPanel p = new JPanel();

		JButton clear = new JButton("Clear");
		clear.setActionCommand(CLEAR_COMMAND);
		clear.addActionListener(this);
		p.add(clear);

		this.word = new JTextField(16);
		this.word.setEditable(false);
		p.add(this.word);

		JButton submit = new JButton("Submit");
		submit.setActionCommand(View.SUBMIT_COMMAND);
		submit.addActionListener(controller);
		p.add(submit);

		JButton roll = new JButton("Re-roll");
		roll.setActionCommand(View.ROLL_COMMAND);
		roll.addActionListener(controller);
		roll.addActionListener(this);
		p.add(roll);

		return p;
	}

	/**
	 * Get the current string that is in the word text field.
	 * 
	 * @return the current string that is in the word text field
	 */
	public String getWord() {
		return this.word.getText();
	}

	/**
	 * Sets the text on the buttons representing the dice.
	 * 
	 * @pre. <code>dice.size() == 16</code>
	 * 
	 * @param dice a list of 16 Boggle dice
	 */
	public void setDice(List<BoggleDie> dice) {
		for (int i = 0; i < 16; i++) {
			BoggleDie d = dice.get(i);
			JButton b = this.diceButtons.get(i);
			b.setText(d.currentFace());
		}
	}

	/**
	 * Causes the view to update after the submitted word is evaluated for
	 * correctness. If <code>isValid == true</code> then the current word is added
	 * to the list of correct words. If <code>isValid == false</code> then the
	 * current word is added to the list of incorrect words. In both cases, the
	 * current word is cleared.
	 * 
	 * @param isValid <code>true</code> if the current word has been determined to
	 *                be a legal Boggle word, <code>false</code> otherwise
	 */
	public void setWordIsValid(boolean isValid) {
		if (isValid) {
			this.correctWords.append(this.getWord());
			this.correctWords.append("\n");
		} else {
			this.incorrectWords.append(this.getWord());
			this.incorrectWords.append("\n");
		}
		this.clearCurrentWord();
	}

	/**
	 * Clears the current word and prepares the view to accept a new word. This
	 * requires re-enabling all of the dice buttons and clearing the set
	 * this.usedButtons
	 */
	private void clearCurrentWord() {
		this.enableAllDiceButtons();
		this.word.setText("");
		this.usedButtons.clear();
	}

	/**
	 * Clears the list of correct words.
	 */
	private void clearCorrectWords() {
		this.correctWords.setText("");
	}

	/**
	 * Clears the list of incorrect words.
	 */
	private void clearIncorrectWords() {
		this.incorrectWords.setText("");
	}
	
	/**
	 * Clears the list of suggested words.
	 */
	private void clearSuggestedWords() {
		this.suggestedWords.setText("");
	}
	
	/**
	 * Sets the list of suggested words using all of 
	 * the words in the specified set.
	 * 
	 * @param words a sorted set of suggested words
	 */
	public void setSuggestedWords(SortedSet<String> words) {
		this.suggestedWords.setText("");
		for (String s : words) {
			this.suggestedWords.append(s.toUpperCase());
			this.suggestedWords.append("\n");
		}
	}

}
