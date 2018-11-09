import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Class Hangman creates an instance of the hangman game and
 * keeps track of the player's guesses and current word set.
 * @author Jonny Nieblas
 * @author Vicky Lym
 * @author Anthony Tran
 * @version 2.1.1
 */
 
public class Hangman implements HangmanManager{
	/** Creates a list of eligible words contained in the dictionary. */
	private final List<String> dictionary;
	/** Sets the length of the word to be guessed. */
	private final int length;
	/** Sets the amount of incorrect guesses the player has. */
	private final int wrongGuessLimit;
	/** Contains all of the letters guessed by the player. */
	private SortedSet<Character> guesses = new TreeSet<Character>();
	/** The number of remaining incorrect guesses the player has. */
	private int guessesLeft;
	/** Contains the word the user is trying to guess. */
	private String goalWord = "*";
	/** Contains a list of possible words that can be used. */
	private Set<String> setOfWords = new HashSet<>();
	
	/**
	 * This creates the instance of the game and sets the
	 * game parameters.
	 */
	public Hangman(final List<String> dictionary, final int length, 
					final int wrongGuessLimit){
		this.dictionary = dictionary;
		this.length = length;
		this.wrongGuessLimit = wrongGuessLimit;
		this.guessesLeft = wrongGuessLimit;
	}
	
	/**
	 * This selects the word the player is trying to guess.
	 * @return finalWord 
	 * @return ret The words that are eligible for the game.
	 */
	@Override
	public Set<String> words() {
		List<String> words = dictionary.stream().
							filter(string ->(string.length()==length))
							.collect(Collectors.toList());
		
		Collections.shuffle(words);
		List<String> selectedWord = new ArrayList<>();
		selectedWord.add(words.get(0));
		Set<String> ret = new HashSet<String>(selectedWord);
		if(guessesLeft != wrongGuessLimit){
			selectedWord.remove(0);
			selectedWord.add(goalWord);
			Set<String> finalWord = new HashSet<String>(selectedWord);
			this.goalWord = "*";
			return finalWord;
		}
		
		return ret;
	}

	/**
	 * This method returns the amount of guesses the player can
	 * incorrectly input.
	 * @return this.wrongGuessLimit The limit of wrong guesses the user has.
	 */
	@Override
	public int wrongGuessLimit() {
		return this.wrongGuessLimit;
	}

	/**
	 * This method returns the amount of incorrect guesses
	 * the player has before losing.
	 * @return this.guessesLeft The number of wrong guesses the user has left.
	 */
	@Override
	public int guessesLeft() {
		return this.guessesLeft;
	}

	/**
	 * This method returns the set of letters the player
	 * has guessed in alphabetical order.
	 * @return this.guesses The letters the user has already guessed.
	 */
	@Override
	public SortedSet<Character> guesses() {
		return this.guesses;
	}

	/**
	 * This method puts the goal word's letters into an array
	 * and compares the guesses to the word.
	 */
	@Override
	public String pattern() {
		while(goalWord == "*"){
			this.setOfWords = words();
			this.goalWord = setOfWords.iterator().next();
		}
		String pattern = "";
		List<Character> goalWordList = new ArrayList<Character>();
		for(char c : goalWord.toCharArray()){
			goalWordList.add(c);
		}
		//PATTERN GENERATION
		List<Character> patternList = goalWordList.stream().map
			(o -> {
				if(guesses().contains(o)){
					return o;
					}
				else{
					return o = '-';
					}
				}
			).collect(Collectors.toList());
		pattern = getStringRepresentation(patternList);		
		return pattern;
	}
	
	/**
	 * This method puts the correctly guessed letters into a string
	 * so that it may be displayed.
	 * @param patternList Holds the letters guessed.
	 */
	private String getStringRepresentation(List<Character> patternList){
		StringBuilder patternBuilder = new StringBuilder(patternList.size());
		for(Character ch: patternList){
			patternBuilder.append(ch);
		}
		return patternBuilder.toString();
	}

	/**
	 * This method processes the letter guessed by the user and informs them of
	 * the result of their guess.
	 * @param guess The letter the player entered for their guess.
	 * @return letterOccurrences How many times the guess is in the goal word.
	 */
	@Override
	public int record(char guess) {
		int letterOccurrences = 0;
		if(guessesLeft == 0){
			throw new IllegalStateException("No more guesses are allowed.");
		}
		if(goalWord.isEmpty()){
			throw new IllegalStateException("There is no goal word available.");
		}
		if(guesses.contains(guess)){
			throw new IllegalArgumentException("You have already guessed this letter.");
		}
		for(int i = 0; i < goalWord.length(); i++){
			if(goalWord.charAt(i) == guess){
				letterOccurrences++;
			}
		}
		if(letterOccurrences == 0){
			this.guessesLeft--;
		}
		guesses.add(guess);
		return letterOccurrences;
	}
}
