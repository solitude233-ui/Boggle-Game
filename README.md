# Boggle-Game

Boggle is a word game played with 16 dice each having 6 faces labelled with a letter of the English alphabet. One die has a face labelled with the string QU to reflect the fact that Q is usually followed by U in English words.

Our Boggle app has many features:

    - The player starts the game by pressing the Re-roll button. This causes the dice to be shaken and rolled randomly.
    - It uses buttons to represent the dice; the user clicks on the buttons to form words. When the player clicks on a button, the button background is set to blue and the button is disabled so that the button can only be used once per word. Also, all of the other buttons not adjacent to the last clicked button are disabled; in this way the player is forced to use letters that form a connected path.
    - As the player clicks on the dice buttons, the word being formed is shown in the text field at the bottom middle.
    - The player presses the Submit button when they are finished spelling the word. The word is checked to see if it is spelled correctly and is at least 3 letters long; if both of these are true, then the word is added to the list of correct words. Otherwise, the word is added to the list of incorrect words. In either case, the word is cleared from the text field, and all of the buttons are enabled and set to white so that the player can enter the next word.
    - When a valid word is submitted, our version of the game displays a list of similar words that are in the dictionary. A word is considered similiar to another word if it starts with the same letter, has the same number of letters, and differs from the other word in at most one letter.
