import java.awt.*;
import javax.swing.*;

/**
 * Tutorial
 * 
 * JComponent that contains the text needed in the tutorial panel
 */
@SuppressWarnings("serial")
public class Tutorial extends JComponent {
	// fields
	public static final int WIDTH = 500;
	public static final int HEIGHT = 260;
	private String[] text;

	/**
	 * Constructor
	 */
	public Tutorial() {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key events are handled by its key
		// listener.
		setFocusable(true);
		
		text = new String[12];
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
	}
	
	public String[] getText() {
		return text; 
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		int ypos = 20;
		for (int i = 0; i < text.length; i++) {
			g.drawString(text[i], 10, ypos);
			ypos += 20;
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
}
