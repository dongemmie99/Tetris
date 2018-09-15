import static org.junit.Assert.*;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class GameOverTest {

	@Test
	public void ConstructorTest() {
		try {
			GameOver gameOverPanel = new GameOver(50);
			String[] highScores = gameOverPanel.getHighScores();
			ArrayList<String> names = gameOverPanel.getNames();
			ArrayList<Integer> scores = gameOverPanel.getScores();
			int[] inOrderIndices = gameOverPanel.getInOrderIndices();
			
			assertEquals("score should be 50", 50, gameOverPanel.getScore());
			assertTrue("hi yooo is a name", names.contains("hi yooo"));
			assertTrue("?? is a name", names.contains("??"));
			assertTrue("90000000 is a score", scores.contains(90000000));
			assertTrue("6089000 is a score", scores.contains(6089000));
			assertEquals("name should match index of its score", 
					names.indexOf("hi yooo"), scores.indexOf(90000000));
			assertEquals("name should match index of its score", 
					names.indexOf("??"), scores.indexOf(6089000));
			
			names.add("encapsulationBreak");
			scores.add(1803945);
			assertFalse("doesn't break encapsulation", gameOverPanel.getNames().size() == names.size());
			assertFalse("doesn't break encapsulation", gameOverPanel.getScores().size() == scores.size());
			assertFalse("doesn't break encapsulation", gameOverPanel.getNames().contains("encapsulationBreak"));
			assertFalse("doesn't break encapsulation", gameOverPanel.getScores().contains(1803945));
			
			inOrderIndices[2] = 2532142;
			assertFalse("doesn't break encapsulation", gameOverPanel.getInOrderIndices().equals(inOrderIndices));
			
			assertEquals("1. 90000000 hi yooo", highScores[0]);
			assertEquals("2. 6089000 ??", highScores[1]);
			assertEquals("first high score index should be 0", 0, inOrderIndices[0]);
			assertEquals("second high score index should be 2", 2, inOrderIndices[1]);
			
			assertEquals("dimensions should be 300 by 300", 
					new Dimension(300, 300), gameOverPanel.getPreferredSize());
			
		} catch (IOException e) {
			
		}
	}
	
	

}
