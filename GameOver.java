import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

/**
 * GameOver
 * 
 * a JComponent to put in the GameOver frame
 * also reads the high scores and associated usernames to display on the screen. 
 */
@SuppressWarnings("serial")
public class GameOver extends JComponent {
	// fields
	public static final int WIDTH = 300;
	public static final int HEIGHT = 300;
	private String[] highScores;
	private LinkedList<String> names;
	private LinkedList<Integer> scores;
	private int[] inOrderIndices;
	private BufferedReader r;
	private int score;

	/**
	 * Constructor
	 */
	public GameOver(int score) throws IOException {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		names = new LinkedList<String>();
		scores = new LinkedList<Integer>();
		this.score = score;
		// read all the scores
		r = new BufferedReader(new FileReader("files/HighScoresFile"));
		
		try {
            while (r.ready()) {
            		String line = r.readLine();
            		int commaIndex = line.indexOf(',');
            		if (commaIndex >= 0) {
            			names.add(line.substring(0, commaIndex));
            			scores.add(Integer.parseInt(line.substring(commaIndex + 1)));
            		}	
            }
		} catch (NullPointerException e) {
			r.close();
		}

		inOrderIndices = new int[scores.size()];
		int[] copyOfScores = new int[scores.size()];
		for (int i = 0; i <inOrderIndices.length; i++) {
			inOrderIndices[i] = i;
			copyOfScores[i] = scores.get(i);
		}
		
		for (int i = 0; i < inOrderIndices.length - 1; i++) {
			for (int j = i + 1; j < inOrderIndices.length; j++) {
				if (copyOfScores[i] <= copyOfScores[j]) {
					int temp = inOrderIndices[i];
					int tempScore = copyOfScores[i];
					inOrderIndices[i] = inOrderIndices[j];
					copyOfScores[i] = copyOfScores[j];
					inOrderIndices[j] = temp;
					copyOfScores[j] = tempScore;
				}
			}
		}

		// initialize String array 
		highScores = new String[5];
		for (int i = 0; i < highScores.length; i++) {
			highScores[i] = (i + 1) + ". ";
		}
		
		// fill in high scores
		int counter = 0;
		while (counter < 5 && counter < inOrderIndices.length) {
			highScores[counter] =  (counter + 1) + ". " + scores.get(inOrderIndices[counter]) +
					" " + names.get(inOrderIndices[counter]);
			counter++;
		}
		
		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key events are handled by its key
		// listener.
		setFocusable(true);
	}
	
	/**
	 * get the high scores
	 * @return a String array of the each line of the score display
	 */
	protected String[] getHighScores() {
		return Arrays.copyOf(highScores, highScores.length); 
	}
	
	/**
	 * gets the array of indices
	 * @return an int array of the indexes of the corresponding name/score
	 */
	protected int[] getInOrderIndices() {
		return Arrays.copyOf(inOrderIndices, inOrderIndices.length); 
	}
	
	/**
	 * gets the ArrayList of names
	 * @return a copy of the names ArrayList
	 */
	protected ArrayList<String> getNames() {
		return new ArrayList<String>(names);
	}
	
	/**
	 * gets the ArrayList of scores
	 * @return a copy of the scores ArrayList
	 */
	protected ArrayList<Integer> getScores() {
		return new ArrayList<Integer>(scores);
	}
	
	/**
	 * gets the score
	 * @return score
	 */
	public int getScore() {
		return score;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		g.setColor(Color.BLACK);
		g.drawString("GAME OVER! ", 75, 80);
		g.drawString("SCORE: " + score, 75, 100);
		g.drawString("HIGH SCORES: ", 75, 130);
		int ypos = 150;
		for (int i = 0; i < highScores.length; i++) {
			g.drawString(highScores[i], 75, ypos);
			ypos += 20;
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
}
