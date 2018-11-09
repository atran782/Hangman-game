import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Hangman.java class.
 * @author Jonny Nieblas
 * @author Vicky Lym
 * @author Anthony Tran
 * @version 1.1.1
 */
public class HangmanTest {
	List<String> dictionary = new ArrayList<String>();
	SortedSet<Character> guesses = new TreeSet<Character>();
    Hangman hanager = null;
    
	@Before
	public void setUp(){
		dictionary.add("four");
		dictionary.add("six");
		hanager = new Hangman(dictionary, 4, 11);
		hanager.pattern();
		hanager.words();
		hanager.record('x');
		hanager.record('u');
		hanager.record('o');
		hanager.record('r');
		hanager.record('h');
		hanager.record('i');
		hanager.record('s');
	}

	@Test
	public void testWords() {
		hanager.record('f');
		assertEquals("four", hanager.pattern());
	}
	
	@Test
	public void testWrongGuessLimit(){
		assertEquals(11, hanager.wrongGuessLimit());
	}

	@Test
	public void testGuessesLeft(){
		assertEquals(7, hanager.guessesLeft());
	}
	
	@Test
	public void testGuesses(){
		SortedSet<Character> guesses = new TreeSet<>();
		guesses.add('x');
		guesses.add('o');
		guesses.add('u');
		guesses.add('r');
		guesses.add('h');
		guesses.add('i');
		guesses.add('s');
		assertEquals(guesses, hanager.guesses());
	}
	
	@Test
	public void testPattern(){
		assertEquals(hanager.pattern(), "-our");
	}
	
	/**
	 * Test for private method getStringRepresentation().
	 * Since pattern() uses this private method, if pattern passes
	 * the correct string value, then getStringRepresentation()
	 * functions properly.
	 */
	@Test
	public void testGetStringRepresentation(){
		assertEquals(hanager.pattern(), "-our");
	}
	
	@Test
    public void testRecord(){
        dictionary.add("aaaaa");
        Hangman hanager2 = new Hangman(dictionary, 5, 11);
        hanager2.pattern();
        hanager2.words();
        int correct = hanager2.record('a');
        assertEquals(5, correct);
    }
}
