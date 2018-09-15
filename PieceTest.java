import static org.junit.Assert.*;
import java.awt.Color;
import org.junit.Test;

public class PieceTest {

	@Test
	public void Constructor() {
		Piece p = new Piece(1);
		assertTrue("type is correct", p.getType() == 1);
		assertTrue("Color is correct", p.getColor().equals(Color.BLUE)
				|| p.getColor().equals(Color.MAGENTA) 
				|| p.getColor().equals(Color.ORANGE) 
				|| p.getColor().equals(Color.GREEN));
		Piece q = new Piece(2);
		assertTrue("type is correct", q.getType() == 2);
	}
	
    @Test
    public void InvalidConstructor() {
        try {
        		Piece p = new Piece(10);
        		p.getType();
        		fail ("10 is an invalid type");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }
    
	@Test public void GetMoveable() {
		Piece p = new Piece((int)(Math.random() * 5 + 1));
		boolean right = p.getMoveableRight();
		boolean left = p.getMoveableLeft();
		int[] x = p.getXIndex();
		boolean mostRight = false;
		for (int i = 0; i < 4; i++) {
			if (x[i] == 13) {
				mostRight = true;
				assertFalse("moveableRight", right);
			}
		}
		if (!mostRight) {
			assertTrue("moveableRight", right);
		}
		boolean mostLeft = false;
		for (int i = 0; i < 4; i++) {
			if (x[i] == 0) {
				mostLeft = true;
				assertFalse("moveableLeft", left);
			}
		}
		if (!mostLeft) {
			assertTrue("moveableLeft", left);
		}
		
		assertTrue("moveableDown", p.getMoveableDown());
	}
	
	@Test
	public void GetY() {
		Piece p = new Piece((int)(Math.random() * 5 + 1));
		int[] y = p.getYIndex();
		if (p.getType() == 1) {
			int[] expected = {0, 1, 0, 1};
			assertArrayEquals("y coords", expected, y);
		}
		
		if (p.getType() == 2 || p.getType() == 5) {
			int[] expected = {0, 1, 2, 3};
			assertArrayEquals("y coords", expected, y);
		}
		
		if (p.getType() == 3) {
			int[] expected = {0, 1, 1, 1};
			assertArrayEquals("y coords", expected, y);
		}
		
		if (p.getType() == 4) {
			int[] expected = {0, 0, 1, 1};
			assertArrayEquals("y coords", expected, y);
		}
		

	}
	
	@Test
	public void MoveRight() {
		Piece p = new Piece((int)(Math.random() * 5 + 1));
		int[] xBefore = p.getXIndex();
		int[] yBefore = p.getYIndex();
		if (p.getMoveableRight()) {
			int[] xAfterExpected = new int[4];
			for (int i = 0; i < 4; i++) {
				xAfterExpected[i] = xBefore[i] + 1;
			}
			p.moveRight();
			int[] xAfter = p.getXIndex();
			assertArrayEquals("x indexes moved right 1", xAfterExpected, xAfter);
		} else {
			p.moveRight();
			assertArrayEquals("x does not change, not moveable", xBefore, p.getXIndex());
		}
		
		assertArrayEquals("y indexes don't change", yBefore, p.getYIndex());
		
		for (int i = 0; i< 15; i++) {
			p.moveRight();
		}
		assertFalse("should not be moveable right", p.getMoveableRight());
		assertTrue("should be moveable left", p.getMoveableLeft());
	}
	
	@Test
	public void MoveLeft() {
		Piece p = new Piece((int)(Math.random() * 5 + 1));
		int[] xBefore = p.getXIndex();
		int[] yBefore = p.getYIndex();
		if (p.getMoveableLeft()) {
			int[] xAfterExpected = new int[4];
			for (int i = 0; i < 4; i++) {
				xAfterExpected[i] = xBefore[i] - 1;
			}
			p.moveLeft();
			int[] xAfter = p.getXIndex();
			assertArrayEquals("x indexes moved left 1", xAfterExpected, xAfter);
		} else {
			p.moveLeft();
			assertArrayEquals("x does not change, not moveable", xBefore, p.getXIndex());
		}
		
		assertArrayEquals("y indexes don't change", yBefore, p.getYIndex());
		
		for (int i = 0; i< 15; i++) {
			p.moveLeft();
		}
		assertFalse("should not be moveable left", p.getMoveableLeft());
		assertTrue("should be moveable right", p.getMoveableRight());
	}
	
	@Test
	public void MoveDown() {
		Piece p = new Piece((int)(Math.random() * 5 + 1));
		int[] xBefore = p.getXIndex();
		int[] yBefore = p.getYIndex();
		if (p.getMoveableDown()) {
			int[] yAfterExpected = new int[4];
			for (int i = 0; i < 4; i++) {
				yAfterExpected[i] = yBefore[i] + 1;
			}
			p.moveDown();
			int[] yAfter = p.getYIndex();
			assertArrayEquals("y indexes moved down 1", yAfterExpected, yAfter);
		} else {
			p.moveLeft();
			assertArrayEquals("y does not change, not moveable", yBefore, p.getYIndex());
		}
		assertArrayEquals("x indexes don't change", xBefore, p.getXIndex());
	
	}
	
	@Test
	public void updateMoveable() {
		Piece p = new Piece((int)(Math.random() * 5 + 1));
		p.updateMoveableRight(true);
		assertTrue("should be moveable right", p.getMoveableRight());
		p.updateMoveableRight(false);
		assertFalse("should not be moveable right", p.getMoveableRight());
		
		p.updateMoveableLeft(true);
		assertTrue("should be moveable left", p.getMoveableLeft());
		p.updateMoveableLeft(false);
		assertFalse("should not be moveable left", p.getMoveableLeft());
		
		p.updateMoveableDown(true);
		assertTrue("should be moveable down", p.getMoveableDown());
		p.updateMoveableDown(false);
		assertFalse("should not be moveable down", p.getMoveableDown());
	}

}
