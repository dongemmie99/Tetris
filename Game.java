import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable{
	private String username;
	private String[] levels = {"1", "2", "3", "4", "5"};
	private String level;

	public void run() {
    		/*** MENU *********/
        final JFrame menu = new JFrame("MENU");
        menu.setLocation(300, 300);

        // username and level panel
        final JPanel usernameStuff = new JPanel();
        final JLabel instructions = new JLabel(" Please Enter a Username:");
        final JTextField usernameInput = new JTextField();
        usernameInput.setPreferredSize(new Dimension(150, 25));
        usernameStuff.add(instructions);
        usernameStuff.add(usernameInput);
        
        final JLabel levelInstructions = new JLabel("Select a Level:");
        final JComboBox<String> levelSelection = new JComboBox<String>(levels);
        usernameStuff.add(levelInstructions);
        usernameStuff.add(levelSelection);
         
        // tutorial panel 
        final Tutorial tutorial_panel = new Tutorial();
        
        // play button 
        final JButton play = new JButton("Play!");
        play.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			username = usernameInput.getText();
        			level = levelSelection.getSelectedItem().toString();
        			/**** Game **************/
                	// Top-level frame in which game components live
                final JFrame frame = new JFrame("TETRIS");
                frame.setLocation(300, 300);
                        
                // Status panel
                final JPanel status_panel = new JPanel();
                frame.add(status_panel, BorderLayout.SOUTH);
                final JLabel status = new JLabel("Running...");
                status_panel.add(status);
                        
                // Main playing area 
                try {
                		final GameCourt court = new GameCourt(status, level);
                		
                     court.setUsername(username);

                     frame.add(court, BorderLayout.CENTER);
                        
                    // Reset button
                    final JPanel control_panel = new JPanel();
                    frame.add(control_panel, BorderLayout.NORTH);
                    

                    final JButton reset = new JButton("Reset");
                    
                    reset.addActionListener(new ActionListener() {
                    		public void actionPerformed(ActionEvent e) {
                                court.reset();
                        }
                    });
                    
                    control_panel.add(reset);
                    
                    final JButton menuButton = new JButton("Menu");
                    menuButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                court.reset();
                                frame.setVisible(false);
                                menu.setVisible(true);
                            }
                    });
                    
                    control_panel.add(menuButton);
                    
                    // Put the frame on the screen
                    frame.pack();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                    
                    // Start game
                    court.reset();
                    menu.setVisible(false);
                } catch (IOException o) {
                        
                }
             }
          });      
          menu.add(play, BorderLayout.SOUTH);   
          menu.add(usernameStuff, BorderLayout.NORTH);
          menu.add(tutorial_panel, BorderLayout.CENTER);
          
          menu.pack();
          menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          menu.setVisible(true);
        }
	
	/**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
