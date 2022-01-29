import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ByorkGame {

	//Windows, panels, labels, and buttons
	private JFrame window;
	private JPanel titlePanel, startPanel, controlPanel, endGamePanel, ratingPanel;
	private JTextArea message, ratingMessage;
	private JLabel title, subtitle, youEscaped, rateBjork;
	private JButton startButton, forward, backward, left, right, interact;
	private JRadioButton one, two, three, four, five;
	private ButtonGroup buttons = new ButtonGroup();
	
	//Fonts
	private Font titleFont = new Font("Courier", Font.BOLD, 70);
	private Font subtitleFont = new Font("Courier", Font.BOLD, 16);
	private Font buttonFont = new Font("Courier", Font.BOLD, 14);
	private Font endGameFont = new Font("Courier", Font.BOLD, 50);
	
	//Formatting icons for controls
	ImageIcon forwardArrow = new ImageIcon("icons/ForwardArrow.png", "ForwardArrow");
	ImageIcon backwardArrow = new ImageIcon("icons/BackwardArrow.png", "BackwardArrow");
	ImageIcon leftArrow = new ImageIcon("icons/LeftArrow.png", "LeftArrow");
	ImageIcon rightArrow = new ImageIcon("icons/RightArrow.png", "RightArrow");
	ImageIcon interactIcon = new ImageIcon("icons/Interact.png", "Interact");
	
	//Button listeners/Handlers
	private startButtonListener start = new startButtonListener();
	private forwardButtonListener moveForward = new forwardButtonListener();
	private backwardButtonListener moveBackward = new backwardButtonListener();
	private leftButtonListener moveLeft = new leftButtonListener();
	private rightButtonListener moveRight = new rightButtonListener();
	private interactButtonListener interactListener = new interactButtonListener();
	private ratingButtonsListener ratingButtonsListener = new ratingButtonsListener();
	
	//A boolean for whether the lights are on or not, used in button listener logic
	private boolean lightsOn = false;
	
	//Final window sizes
	private final int WINDOW_WIDTH = 500;
	private final int WINDOW_HEIGHT = 500;
	
	//Starts the game
	public static void main(String[] args) {
		new ByorkGame();
	}
	
	//Creates the beginning of the game, its main window, and panels used in the first screen
	public ByorkGame() {
		
		//Formatting the main window
		window = new JFrame();
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setTitle("Byork: Escape the Room");
		window.getContentPane().setBackground(Color.white);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLayout(null);
		
		//Formatting the title Screen
		titlePanel = new JPanel();
		titlePanel.setBounds(50, 75, 400, 100);
		titlePanel.setBackground(Color.white);
		title = new JLabel("Byork");
		title.setFont(titleFont);
		subtitle = new JLabel("Definitely not a Zork ripoff...");
		subtitle.setFont(subtitleFont);
		
		//Formatting the snart button panel and button
		startPanel = new JPanel();
		startPanel.setBounds(200, 300, 100, 50);
		startPanel.setBackground(Color.white);
		startButton = new JButton("Start");
		startButton.setSize(100, 50);
		startButton.setFont(buttonFont);
		startButton.addActionListener(start);

		//Adding to panels
		titlePanel.add(title);
		titlePanel.add(subtitle);
		startPanel.add(startButton);
		
		//Adding panels to window
		window.add(titlePanel);
		window.add(startPanel);
		window.setVisible(true);
	}
	
	public void createGamePlayScreen() {
		
		//Removing previous pages panels from the screen
		titlePanel.setVisible(false);
		startPanel.setVisible(false);
		
		//Formatting the message area
		message = new JTextArea();
		message.setBounds(20, 20, 460, 50);
		message.setFont(subtitleFont);
		message.setBackground(null);
		message.setLineWrap(true);
		message.setWrapStyleWord(true);
		message.setText("You wake up in a dark room. What do you do?\n(Use controls below: Forward, backward, left, right, interact.)");
		
		//Formatting the control buttons
		forward = new JButton(forwardArrow);
		forward.addActionListener(moveForward);
		backward = new JButton(backwardArrow);
		backward.addActionListener(moveBackward);
		left = new JButton(leftArrow);
		left.addActionListener(moveLeft);
		right = new JButton(rightArrow);
		right.addActionListener(moveRight);
		interact = new JButton(interactIcon);
		interact.addActionListener(interactListener);
		
		//Creating a panel to hold the controls
		controlPanel = new JPanel();
		controlPanel.setBounds(100, 100, 300, 300);
		controlPanel.setLayout(new GridLayout(3,3));
		controlPanel.setBackground(Color.white);
		
		//Adding blank space and control buttons for the correct layout
		controlPanel.add(new JLabel(""));
		controlPanel.add(forward);
		controlPanel.add(new JLabel(""));
		controlPanel.add(left);
		controlPanel.add(interact);
		controlPanel.add(right);
		controlPanel.add(new JLabel(""));
		controlPanel.add(backward);
		
		//adding the panels and refreshing the window
		window.add(message);
		window.add(controlPanel);
		window.repaint();
		window.revalidate();
	}
	
	//The final screen when the game is complete
	public void createEndGameScreen() {
		//Removing panels from previous screen
		message.setVisible(false);
		controlPanel.setVisible(false);
		
		//Creating the panels for the ending screen and formatting the text
		endGamePanel = new JPanel();
		endGamePanel.setBounds(50, 75, 400, 100);
		endGamePanel.setBackground(Color.white);
		youEscaped = new JLabel("You escaped!");
		youEscaped.setFont(endGameFont);
		rateBjork = new JLabel("Did you enjoy this game? Rate 1-5");
		rateBjork.setFont(subtitleFont);
		
		//Adding text to panel
		endGamePanel.add(youEscaped);
		endGamePanel.add(rateBjork);
		
		//Creating a panel to hold radio buttons for a rating
		ratingPanel = new JPanel();
		ratingPanel.setBounds(100, 175, 300, 50);
		ratingPanel.setBackground(Color.white);
		ratingPanel.setLayout(new GridLayout(1,5));
		
		//Creating the radio button
		one = new JRadioButton("1");
		one.setFont(subtitleFont);
		two = new JRadioButton("2");
		two.setFont(subtitleFont);
		three = new JRadioButton("3");
		three.setFont(subtitleFont);
		four = new JRadioButton("4");
		four.setFont(subtitleFont);
		five = new JRadioButton("5");
		five.setFont(subtitleFont);

		//Appplying actions to each button
		one.addActionListener(ratingButtonsListener);
		two.addActionListener(ratingButtonsListener);
		three.addActionListener(ratingButtonsListener);
		four.addActionListener(ratingButtonsListener);
		five.addActionListener(ratingButtonsListener);
	
		//Adding buttons to a group so only one can be selected at a time
		buttons.add(one);
		buttons.add(two);
		buttons.add(three);
		buttons.add(four);
		buttons.add(five);
		
		//Adding buttons to the panel
		ratingPanel.add(one);
		ratingPanel.add(two);
		ratingPanel.add(three);
		ratingPanel.add(four);
		ratingPanel.add(five);
		
		//Formatting panel used for displaying text for when each radio button is pressed
		ratingMessage = new JTextArea();
		ratingMessage.setBackground(Color.white);
		ratingMessage.setFont(subtitleFont);
		ratingMessage.setBounds(50, 250, 400, 50);
		ratingMessage.setLineWrap(true);
		ratingMessage.setWrapStyleWord(true);
		
		//Adding everything to the window and updating it
		window.add(endGamePanel);
		window.add(ratingPanel);
		window.add(ratingMessage);
		window.repaint();
		window.revalidate();
	}
	
	//Listener for the start button, changes to the main gameplay screen
	private class startButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			createGamePlayScreen();
		}
		
	}
	
	//Listener for the forward button, displays text when hit
	private class forwardButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			message.setText("Bang! You walked into a wall");
		}
	}
	
	//Listener for backward button, displays text when hit
	private class backwardButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			message.setText("Your back is up against a wall.");
		}
	}
	
	//Listen for left button.  Displays different text depending on if the lights are turned on
	private class leftButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (lightsOn == false) {
				message.setText("There is a light switch here. Interact to turn it on.");
			}
			else {
				message.setText("There is a light switch here. Interact to turn it off.");
			}
		}
	}
	
	//Right button listener, displays text when hit.
	private class rightButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			message.setText("There is a door here. Interact to open it.");
		}
	}
	
	//Interact button listener, displays different text depending on if the lights or on, and whether you are currently facing a certain direction.
	//If pressed when facing the left side, turns on or off the lights
	private class interactButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			if (lightsOn == false) {
				if (message.getText().equals("There is a light switch here. Interact to turn it on.")) {
					message.setText("You turned on the lights. There is a door to the right.");
					lightsOn = true;
				}
				else if (message.getText().equals("There is a door here. Interact to open it.")) {
					createEndGameScreen();
					
				}
				else {
					message.setText("You flail your hands around aimlessly in the dark.");
				}
			}
			else {
				if (message.getText().equals("There is a light switch here. Interact to turn it off.")) {
					message.setText("You turned off the lights. Ah! It's dark in here!");
					lightsOn = false;
				}
				else if (message.getText().equals("There is a door here. Interact to open it.")) {	
					createEndGameScreen();	
				}
				else {	
					message.setText("You look around the room. There is a door to the right.");
				}
			}
			
		}
	}
	
	//Listener for the rating radio buttons, displays different text for each
	private class ratingButtonsListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			String actionCommand = e.getActionCommand();
			
			if (actionCommand.equals("1")) {
				ratingMessage.setText("I'm sorry you didn't like this game with like five actions. What did you expect?");
			}
			else if (actionCommand.equals("2")) {
				ratingMessage.setText("Well, two is better than one...");
			}
			else if (actionCommand.equals("3")) {
				ratingMessage.setText("Figures, this game is pretty easy to beat.");
			}
			else if (actionCommand.equals("4")) {
				ratingMessage.setText("Wow, I'm surprised you liked it that much...");
			}
			else if (actionCommand.equals("5")) {
				ratingMessage.setText("Thanks for the great rating!");
			}
			
		}
	}
}



