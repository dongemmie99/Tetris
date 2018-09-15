import static org.junit.Assert.*;

import java.awt.Dimension;

import org.junit.Test;

public class TutorialTest {

	@Test
	public void testConstructor() {
		Tutorial tutorialPanel = new Tutorial();
		String[] text = new String[12];
		text[0] = "TETRIS TUTORIAL:";
		text[1] = "";
		text[2] = "Fill in the gaps with game pieces to complete rows to earn points. When";
		text[3] = "a row is completed, it will be deleted. If the pieces stack up and reach";
		text[4] = "the top of the gameboard, the game is over.";
		text[5] = "";
		text[6] = "\"UP NEXT\" displays upcoming game pieces where the topmost piece is next.";
		text[7] = "";
		text[8] = "Controls: ";
		text[9] = "Right Arrow- moves game piece to the right";
		text[10] = "Left Arrow- moves game piece to the left";
		text[11] = "Down Arrow- moves game piece the bottom faster";
		
		assertEquals("dimensions should be 500 by 260", 
				new Dimension(500, 260), tutorialPanel.getPreferredSize());
		assertArrayEquals("expected text", text, tutorialPanel.getText());
	}

}
