import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;

import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. 
 */
@SuppressWarnings("serial")
public class GameCourt extends JComponent {
	// fields
	private boolean[][] board; // indicating which squares are filled
	private Color[][] boardColors; // indicating colors of the squares filled
	private Piece current; // current piece thats being moved
	private LinkedList<Piece> upcoming; // list of upcoming pieces
	private int score; // current score 
	private String username; // current username
	private int level;
	public boolean playing; // whether the game is running
	private JLabel status; // Current status text, "Running.." "Game Over"

	// Game constants
	public static final int COURT_WIDTH = 500;
	public static final int COURT_HEIGHT = 600;

	// Update interval for timer, in milliseconds
	private int interval;
	
	// Reader/Writer
	private BufferedWriter w;
	
	// make sure score is only written once
	private boolean written;

	/**
	 * Constructor
	 */
	public GameCourt(JLabel status, String lvl) throws IOException {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		w = new BufferedWriter(new FileWriter("files/HighScoresFile", true));
		level = Integer.parseInt(lvl);
		if (level == 1) {
			interval = 1000;
		} else if (level == 2) {
			interval = 400;
		} else if (level == 3) {
			interval = 150;
		} else if (level == 4) {
			interval = 50;
		} else {
			interval = 10;
		}
		
		// triggers an action periodically with the given interval
		Timer timer = new Timer(interval, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); 

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key events are handled by its key
		// listener.
		setFocusable(true);

		// This key listener allows the tetris piece to move as long as an arrow key is
		// pressed.
		// right/left keys make it move in that direction, down makes it move faster
		// down.
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int[] checkY = current.getYIndex();
				int[] checkX = current.getXIndex();
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (current.getMoveableLeft()) {
						boolean move = true;
						for (int i = 0; i < checkX.length; i++) {
							if (board[checkX[i] - 1][checkY[i]]) {
								move = false;
							}
						}
						if (move) {
							current.moveLeft();
							repaint();
						}
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (current.getMoveableRight()) {
						boolean move = true;
						for (int i = 0; i < checkX.length; i++) {
							if (board[checkX[i] + 1][checkY[i]]) {
								move = false;
							}
						}
						if (move) {
							current.moveRight();
							repaint();
						}
					}
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					tick();
					tick();
				} 	
			}
		});
		this.status = status;
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
		board = new boolean[15][30];
		boardColors = new Color[15][30];
		current = new Piece((int)(Math.random() * 5 + 1));
		score = 0;
		upcoming = new LinkedList<Piece>();
		upcoming.add(new Piece((int)(Math.random() * 5 + 1)));
		upcoming.add(new Piece((int)(Math.random() * 5 + 1)));
		upcoming.add(new Piece((int)(Math.random() * 5 + 1)));
		upcoming.add(new Piece((int)(Math.random() * 5 + 1)));
		playing = true;
		written = false;
		status.setText("Running...");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			// check if tetris piece has reached the bottom/another piece
			int[] checkY = current.getYIndex();
			int[] checkX = current.getXIndex();
			for (int i = 0; i < checkY.length; i++) {
				if (checkY[i] == 29) {
					current.updateMoveableDown(false);
				} else if (board[checkX[i]][checkY[i] + 1]) {
					current.updateMoveableDown(false);
				}
			}

			// if not able to move down, store the pieces
			if (!current.getMoveableDown()) {
				for (int i = 0; i < checkX.length; i++) {
					board[checkX[i]][checkY[i]] = true;
					boardColors[checkX[i]][checkY[i]] = current.getColor();
				}
			} else {
				// make tetris piece move down
				current.moveDown();
			}

			// check for the game end conditions, if pieces hit top of array
			for (int i = 0; i < checkY.length; i++) {
				if (checkY[i] == 0) {
					playing = false;
					current.updateMoveableDown(false);
					current.updateMoveableLeft(false);
					current.updateMoveableRight(false);
					status.setText("Game Over!");
					try {
						if (!written) {
							w.write(username + "," + score);
							w.newLine();
							w.flush();
							written = true;
						}
					} catch (IOException e) {
						
					}
				}
			}

			// check if there's a complete row
			for (int y = 0; y < board[0].length; y++) {
				boolean rowComplete = true;
				for (int x = 0; x < board.length - 1; x++) {
					if (!board[x][y]) {
						rowComplete = false;
					}
				}
				if (rowComplete) {
					score += 20;
					for (int n = y; n > 0; n--) {
						for (int m = 0; m < board.length; m++) {
							board[m][n] = board[m][n - 1];
							boardColors[m][n] = boardColors[m][n - 1];
						}
					}
				}
			}

			// make new piece if game is still in progress
			if (playing && !current.getMoveableDown()) {
				current = upcoming.removeFirst();
				upcoming.add(new Piece((int)(Math.random() * 5 + 1)));
				score++;
			}

			// update the display
			repaint();
		}
	}
	
	/**
	 * sets the username 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		current.draw(g);
		// drawing already existing pieces
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j]) {
					g.setColor(boardColors[i][j]);
					g.fillRect(i * 20, j * 20, 20, 20);
				}
			}
		}
		if (!playing) {
			g.setColor(Color.WHITE);
			g.fillRect(50, 50, 200, 200);
			g.setColor(Color.BLACK);
			g.drawRect(50, 50, 200, 200);
			try {
				GameOver gameOverPanel = new GameOver(score);
				gameOverPanel.paintComponent(g);
			} catch (IOException e) {
				
			}
		}
		g.setColor(Color.BLACK);
		g.drawLine(300, 0, 300, 600);
		g.drawLine(0, 600, 1000, 600);
		g.drawString("LEVEL: " + level, 350, 30);
		g.drawString("SCORE: " + score, 350, 550);
		g.drawString("USERNAME: " + username, 350, 575);
	
		// drawing upcoming pieces
		g.drawString("UP NEXT:", 350, 75);
		int startY = 125;
		for (Piece piece: upcoming) {
			piece.drawAltered(g, 360, startY);
			startY += 100;
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
	/**** METHODS FOR TESTING PURPOSES ONLY  ************************/
	
	/**
	 * 
	 * @return current piece
	 */
	protected Piece getCurrent() {
		return current;
	}
	
	/**
	 * 
	 * @return board state
	 */
	protected boolean[][] getBoard() {
		return board;
	}
	
	/** 
	 * 
	 * @return board colors
	 */
	protected Color[][] getboardColors() {
		return boardColors;
	}
	
	/**
	 * 
	 * @return upcoming pieces
	 */
	protected LinkedList<Piece> getUpcoming() {
		return upcoming;
	}
	
	/**
	 * 
	 * @return current score
	 */
	protected int getScore() {
		return score;
	}
	
	/** 
	 * 
	 * @return username
	 */
	protected String getUsername() {
		return username;
	}
	
	/**
	 * 
	 * @return level
	 */
	protected int getLevel() {
		return level;
	}
	
	/**
	 * 
	 * @return interval
	 */
	protected int getInterval() {
		return interval;
	}
	
	/**
	 * redo the board
	 * @param newBoard
	 */
	protected void setBoard(boolean[][] newBoard) {
		board = newBoard;
	}
	
	/**
	 * redo current piece
	 * @param p
	 */
	protected void setCurrent(Piece p) {
		current = p;
	}
	
	
}
