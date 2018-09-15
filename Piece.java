import java.awt.*;

/** 
 * Tetris Pieces
 * 
 * stores positions(indexes) of the piece on the board array and includes functions 
 * to move the piece around the game court
 */

public class Piece {
	// fields
	private Color color;
	private static int currentColor = 1; 
	private int type; 
	private int start;
	
	private int[] xIndex;
	private int[] yIndex;
	
	// keeps track of if there is still room for the piece to keep moving in the directions
	private boolean moveableDown;
	private boolean moveableRight;
	private boolean moveableLeft;
	
	/**
     * Constructor
     */
    public Piece(int t) {
    		moveableDown = true;
    		moveableRight = true;
    		moveableLeft = true;
    		
    	    if (currentColor == 4) {
    	    		currentColor = 1;
    	    } else {
    	    		currentColor++;
    	    }
    	    if (currentColor == 1) {
    	    		this.color = Color.BLUE;
    	    } else if (currentColor == 2) {
    	    		this.color = Color.MAGENTA;
    	    } else if (currentColor == 3) {
    	    		this.color = Color.GREEN;
    	    } else {
    	    		this.color = Color.ORANGE;
    	    }
    	    
    		this.type = t;
    		
    		// set indexes of each square in the block
    		xIndex = new int[4];
	    yIndex = new int[4];
    		if (type == 1) {
    			// block
    			start = (int)(Math.random() * 12 + 0);
    			xIndex[0] = 0 + start; // leftmost
    			xIndex[1] = 0 + start;
    			xIndex[2] = 1 + start;
    			xIndex[3] = 1 + start; // rightmost
    			if (xIndex[3] == 14) moveableRight = false;
    			if (xIndex[0] == 0) moveableLeft = false;
    			
    			yIndex[0] = 0; 
    			yIndex[1] = 1;
    			yIndex[2] = 0;
    			yIndex[3] = 1;
    		} else if (type == 2 || type == 5) {
    			// line
    			start = (int)(Math.random() * 13 + 0);
    			xIndex[0] = 0 + start; // leftmost&rightmost
    			xIndex[1] = 0 + start;
    			xIndex[2] = 0 + start;
    			xIndex[3] = 0 + start;
    			if (xIndex[0] == 14) moveableRight = false;
    			if (xIndex[0] == 0) moveableLeft = false;
    			
    			yIndex[0] = 0;
    			yIndex[1] = 1;
    			yIndex[2] = 2;
    			yIndex[3] = 3;

    		} else if (type == 3) {
    			// tu
    			start = (int)(Math.random() * 11 + 0);
    			xIndex[0] = 1 + start;
    			xIndex[1] = 0 + start; // leftmost
    			xIndex[2] = 1 + start;
    			xIndex[3] = 2 + start; // rightmost
    			if (xIndex[3] == 14) moveableRight = false;
    			if (xIndex[1] == 0) moveableLeft = false;
    			
    			yIndex[0] = 0;
    			yIndex[1] = 1;
    			yIndex[2] = 1;
    			yIndex[3] = 1;

    		} else if (type == 4) {
    			// zigzag
    			start = (int)(Math.random() * 11 + 0);
    			xIndex[0] = 0 + start; // leftmost
    			xIndex[1] = 1 + start; 
    			xIndex[2] = 1 + start;
    			xIndex[3] = 2 + start; // rightmost
    			if (xIndex[3] == 14) moveableRight = false;
    			if (xIndex[0] == 0) moveableLeft = false;
    			
    			yIndex[0] = 0;
    			yIndex[1] = 0;
    			yIndex[2] = 1;
    			yIndex[3] = 1;

    		} else {
    			throw new IllegalArgumentException("invalid type");
    		}
    		
    }
    
    /*** GETTERS **********************************************************************************/
    /**
     * getting X positions
     * @return an int array of the X indices of each square in the piece
     */
    public int[] getXIndex() {
    		//return Arrays.copyOf(xIndex, xIndex.length);
    		return xIndex;
    }
    
    /**
     * getting Y positions
     * @return an int array of the Y indices of each square int the piece
     */
    public int[] getYIndex() {
		//return Arrays.copyOf(yIndex, yIndex.length);
    		return yIndex;
    }
    
    /**
     * Gets if piece can move down 
     * @return true if piece can continue down, false if not
     */
    public boolean getMoveableDown() {
		return moveableDown;
    }
    
    /**
     * Gets if piece can move right
     * @return true if piece can move right, false if not
     */
    public boolean getMoveableRight() {
		return moveableRight;
    }
    
    /**
     * Gets if piece can move left
     * @return true if piece can move left, false if not
     */
    public boolean getMoveableLeft() {
		return moveableLeft;
    }
    
    /**
     * Gets the color of the piece
     * @return color of the piece
     */
    public Color getColor() {
		return color;
    }
    
    /**
     * Gets the color of the piece
     * @return color of the piece
     */
    public int getType() {
		return type;
    }
    
    
    /*** UPDATES AND OTHER METHODS ****************************************************************/
    /**
     * shifts the piece left 1 position
     */
    public void moveLeft() {
    		// checks if its possible to move piece left
    		moveableRight = true;
    		if (moveableLeft) {
			for (int i = 0; i < xIndex.length; i++) {
				xIndex[i]--;
				if (xIndex[i] == 0) {
					moveableLeft = false;
				}
			}
		}
    }
    
    /**
     * shifts the piece right 1 position
     */
    public void moveRight() {
    		// checks if its possible to move piece right
    		moveableLeft = true;
    		if (moveableRight) {
			for (int i = 0; i < xIndex.length; i++) {
				xIndex[i]++;
				if (xIndex[i] == 14) {
					moveableRight = false;
				}
			}
		}
    }
    
    /**
     * shifts the piece down 1 position
     */
    public void moveDown() {
		if (moveableDown) {
			for (int i = 0; i < yIndex.length; i++) {
				yIndex[i]++;
			}
		}
    }
    /**
     * updates if the piece can continue moving down or not
     * @param down the boolean value to change moveableDown to
     */
    public void updateMoveableDown(boolean down) {
		moveableDown = down;
    }
    
    /**
     * updates if the piece can continue moving right or not
     * @param right the boolean value to change moveableRight to
     */
    public void updateMoveableRight(boolean right) {
		moveableRight = right;
    }
    
    /**
     * updates if the piece can continue moving left or not
     * @param left the boolean value to change moveableLeftto
     */
    public void updateMoveableLeft(boolean left) {
		moveableLeft = left;
    }

    /**
     * draw method
     * @param g
     */
    public void draw (Graphics g) {
    		g.setColor(this.color);
    		// squares of dimentions 20x20 pixels. x and y are top corners of rect 
    		for (int i = 0; i < xIndex.length; i++) {
    			g.fillRect(xIndex[i] * 20, yIndex[i] * 20, 20, 20);
    		}
    }
    /**
     * alternate draw method that draws the piece with its (0, 0) coordinates
     * at a different location
     * @param g
     * @param x shifted x position 
     * @param y shifted y position
     */
    public void drawAltered (Graphics g, int x, int y) {
		g.setColor(this.color);
		// squares of dimentions 20x20 pixels. x and y are top corners of rect 
		for (int i = 0; i < xIndex.length; i++) {
			g.fillRect((xIndex[i] - start) * 20 + x, yIndex[i] * 20 + y, 20, 20);
		}
    }
}

