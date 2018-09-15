import static org.junit.Assert.*;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JLabel;
import org.junit.Test;

public class GameCourtTest {

	@Test
	public void testConstructorReset() {
		JLabel status = new JLabel("Running...");
		try {
			GameCourt testCourt = new GameCourt(status, "1");
			assertFalse("game should be playing", testCourt.playing);
			testCourt.setUsername("HI");
			assertEquals("username should be HI", "HI", testCourt.getUsername());
			assertEquals("level should be 1", 1, testCourt.getLevel());
			assertEquals("interval should be 1000", 1000, testCourt.getInterval());
			
			testCourt.reset();
			assertTrue("court should be playing", testCourt.playing);
			assertEquals("score should be 0", 0, testCourt.getScore());
			
			boolean[][] expectedBoard = new boolean[15][30];
			assertArrayEquals(expectedBoard, testCourt.getBoard());
			
			assertEquals("dimensions should be 500 by 600", 
					new Dimension(500, 600), testCourt.getPreferredSize());		
		} catch (IOException e) {
			
		}
	}
	
	@Test
	public void testMovePieceRightLeft() {
		JLabel status = new JLabel("Running...");
		try {
			GameCourt testCourt = new GameCourt(status, "1");
			testCourt.reset();
			int[] x = testCourt.getCurrent().getXIndex();
			int rightMost = x[0];
			for (int i = 1; i < x.length; i++) {
				if (x[i] > rightMost) {
					rightMost = x[i];
				}
			}
			
			for (int i = 0; i < 15 - rightMost; i++) {
				testCourt.getCurrent().moveRight();
			}
			
			assertFalse("moveableRight should be false", testCourt.getCurrent().getMoveableRight());
			
			int[] x2 = testCourt.getCurrent().getXIndex();
			int leftMost = x2[0];
			for (int i = 1; i < x2.length; i++) {
				if (x2[i] < leftMost) {
					leftMost = x2[i];
				}
			}
			
			for (int i = 0; i < leftMost; i++) {
				testCourt.getCurrent().moveLeft();
			}
			
			assertFalse("moveableLeft should be false", testCourt.getCurrent().getMoveableLeft());
			
		} catch (IOException e) {
			
		}
	}
	
	@Test
	public void testMovePieceRightLeftCollide() {
		JLabel status = new JLabel("Running...");
		try {
			GameCourt testCourt = new GameCourt(status, "1");
			testCourt.reset();
			boolean[][] newBoard = new boolean[15][30];
			for (int i = 0; i < 29; i++) {
				newBoard[0][i] = true;
				newBoard[14][i] = true;
			}
			
			int[] x = testCourt.getCurrent().getXIndex();
			int rightMost = x[0];
			for (int i = 1; i < x.length; i++) {
				if (x[i] > rightMost) {
					rightMost = x[i];
				}
			}
		
			for (int i = 0; i < 15 - rightMost - 1; i++) {
				testCourt.getCurrent().moveRight();
			}
			
			assertFalse("moveableRight should be false", testCourt.getCurrent().getMoveableRight());
			
			int[] x2 = testCourt.getCurrent().getXIndex();
			int leftMost = x2[0];
			for (int i = 1; i < x2.length; i++) {
				if (x2[i] < leftMost) {
					leftMost = x2[i];
				}
			}
			
			for (int i = 0; i < leftMost; i++) {
				testCourt.getCurrent().moveLeft();
			}
			
			assertFalse("moveableLeft should be false", testCourt.getCurrent().getMoveableLeft());
			
		} catch (IOException e) {
			
		}
	}
	
	@Test
	public void testScore() {
		JLabel status = new JLabel("Running...");
		try {
			GameCourt testCourt = new GameCourt(status, "1");
			testCourt.reset();
			
			boolean[][] newBoard = new boolean[15][30];
			for (int i = 0; i < 15; i++) {
				newBoard[i][29] = true;
			}
			
			testCourt.setBoard(newBoard);
			testCourt.tick();
			
			assertEquals("score should be 10", 20, testCourt.getScore());
			
			testCourt.reset();
			assertEquals("score should be 0", 0, testCourt.getScore());

		} catch (IOException e) {
			
		}
	}
	
	@Test
	public void testGameOver() {
		JLabel status = new JLabel("Running...");
		try {
			GameCourt testCourt = new GameCourt(status, "1");
			testCourt.reset();
			
			boolean[][] newBoard = new boolean[15][30];
			for (int i = 0; i < 15; i++) {
				for (int j = 29; j > 1; j--) {
					newBoard[i][j] = true;
				}
			}
			
			testCourt.setBoard(newBoard);
			testCourt.setCurrent(new Piece(1));
			testCourt.tick();
			
			assertFalse("game should not be playing", testCourt.playing);
		} catch (IOException e) {
			
		}
	}
	
	@Test
	public void testHitBottom() {
		JLabel status = new JLabel("Running...");
		try {
			GameCourt testCourt = new GameCourt(status, "1");
			testCourt.reset();
			testCourt.setCurrent(new Piece(1));
			
			Piece before = testCourt.getCurrent();
			
			for (int i = 0; i < 29; i++) {
				testCourt.tick();
			}
			
			assertFalse("different piece created after hitting bottom", before.equals(testCourt.getCurrent()));

		} catch (IOException e) {
			
		}
	}
	
	@Test
	public void testHitOtherPieces() {
		JLabel status = new JLabel("Running...");
		try {
			GameCourt testCourt = new GameCourt(status, "1");
			testCourt.reset();
			testCourt.setCurrent(new Piece(1));
			
			Piece before = testCourt.getCurrent();
			
			boolean[][] newBoard = new boolean[15][30];
			for (int i = 0; i < 15; i++) {
				for (int j = 29; j > 19; j--) {
					newBoard[i][j] = true;
				}
			}
			
			testCourt.setBoard(newBoard);
			testCourt.tick();
			
			assertEquals("same peice after one tick", before, testCourt.getCurrent());
			
			for (int i = 0; i < 28; i++) {
				testCourt.tick();
			}
			
			assertFalse("new peice created after hitting another piece", before.equals(testCourt.getCurrent()));

		} catch (IOException e) {
			
		}
	}

}

