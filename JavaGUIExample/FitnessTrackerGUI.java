import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FitnessTrackerGUI {

	//Windows, panels, labels, and buttons
	private JFrame window;
	private JPanel titleDate, selectFile, yearWeek, activities, prevNextWeek, addActivity, clickPromptPanel;
	private JScrollPane lastDayScroller;
	private JLabel title, date, thisYear, thisWeek, clickToView;
	private JTextArea lastDayText; 
	private JButton selectActivitiesFile, selectStepsFile, prevWeekButton, nextWeekButton, addActivityButton, runButton, swimButton, bikeButton, strengthButton;
	
	//Fonts
	private Font titleFont = new Font("Futura", Font.BOLD, 30);
	private Font subTitleFont = new Font("Futura", Font.BOLD, 20);
	private Font buttonFont = new Font("Futura", Font.BOLD, 15);
	private Font textFont = new Font("Futura", Font.PLAIN, 12);
	private Font lastDayFont = new Font("Futura", Font.PLAIN, 15);
	
	//Icons
	ImageIcon runIcon = new ImageIcon("icons/Run.png", "Run");
	ImageIcon bikeIcon = new ImageIcon("icons/Bike.png", "Bike");
	ImageIcon swimIcon = new ImageIcon("icons/Swim.png", "Swim");
	ImageIcon strengthIcon = new ImageIcon("icons/Strength.png", "Strength");
	
	//Button Listeners/Handlers
	private selectActivitiesFileListener selectActivitiesFileListener = new selectActivitiesFileListener();
	private selectStepsFileListener selectStepsFileListener = new selectStepsFileListener();
	private prevButtonListener prevButtonListener = new prevButtonListener();
	private nextButtonListener nextButtonListener = new nextButtonListener();
	private runButtonListener runButtonListener = new runButtonListener();
	private bikeButtonListener bikeButtonListener = new bikeButtonListener();
	private swimButtonListener swimButtonListener = new swimButtonListener();
	private strengthButtonListener strengthButtonListener = new strengthButtonListener();
	private addActivityButtonListener addActivityButtonListener = new addActivityButtonListener();
	
	//Variables
	private double yearlyMiles;
	private int yearlyMinutes;
	private double weeklyMiles;
	private int weeklyMinutes;
	private String yearlyTotals;
	private String weeklyTotals = "TODO";
	private int runTotal = 0;
	private int bikeTotal = 0;
	private int swimTotal = 0;
	private int strengthTotal = 0;
	private String weeklySummary = "";
	
	//Dates
	private LocalDate today = LocalDate.now();
	private String todayDateFormat = today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
	private LocalDate endOfLastWeek;
	private LocalDate beginningOfLastWeek;
	private WeekFields weekFields = WeekFields.of(Locale.getDefault());
	private int weekNumber = today.get(weekFields.weekOfWeekBasedYear());
	private LocalDate lastActiveDay = null;
	
	//Creates array active days of every day of the year. 
	private ActiveDay[] year = new ActiveDay[366];
	
	//Finds the amount of days in the week
	int daysInWeek = FitnessTrackerManager2.getDaysInCurrentWeek(today.getDayOfYear());
	
	//Files
	File selectedFile;
	
	public FitnessTrackerGUI() {
		
		//Formatting the main window
		window = new JFrame();
		window.setSize(1000,700);
		window.setTitle("Fitness Tracker");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(null);
		window.setLocationRelativeTo(null);
		window.setLayout(null);
		window.setResizable(false);
		
		//Formatting the Title/Date Panel
		titleDate = new JPanel();
		titleDate.setBounds(0, 0, 1000, 60);
		titleDate.setBackground(new Color(244, 152, 192));
		titleDate.setLayout(new GridLayout());
		
		title = new JLabel("\tFitness Tracker 3.0");
		title.setHorizontalAlignment(JLabel.LEFT);
		title.setFont(titleFont);
		title.setForeground(Color.white);
		
		date = new JLabel(String.format("Date: %s - Day %s\t", todayDateFormat, today.getDayOfYear()));
		date.setHorizontalAlignment(JLabel.RIGHT);
		date.setFont(titleFont);
		date.setForeground(Color.white);
		
		titleDate.add(title);
		titleDate.add(date);
		
		//Formatting the lastDay Panel
		lastDayText = new JTextArea();
		lastDayText.setFont(lastDayFont);
		lastDayText.setEditable(false);
		lastDayText.setBackground(null);
		lastDayText.setLineWrap(true);
		lastDayText.setWrapStyleWord(true);
		lastDayText.setText("Welcome to Fitness Tracker! Please add activities and steps files using the buttons to the right.");
		
		lastDayScroller = new JScrollPane(lastDayText);
		lastDayScroller.setBounds(0, 100, 500, 520);
		lastDayScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		//Formatting Select File Pane
		selectFile = new JPanel();
		selectFile.setBounds(625, 225, 250, 250);
		selectFile.setBackground(null);
		selectFile.setLayout(new GridLayout(2,0));
		
		selectActivitiesFile = new JButton("Select Activities File");
		selectActivitiesFile.setFont(subTitleFont);
		selectActivitiesFile.addActionListener(selectActivitiesFileListener);
		
		selectStepsFile = new JButton("Select Steps File");
		selectStepsFile.setFont(subTitleFont);
		selectStepsFile.addActionListener(selectStepsFileListener);
		
		selectFile.add(selectActivitiesFile);
		selectFile.add(selectStepsFile);
		
		//Adding Panels to Window
		window.add(titleDate);
		window.add(selectFile);
		window.add(lastDayScroller);
		window.setVisible(true);
		
	}
	
	public void createFitnessTrackerGUIMainScreen() {
		

		
		selectFile.setVisible(false);
		
		lastActiveDay();
		weeklySummary();
		yearlyTotal();
		weeklyTotal();
		activitiesTotal();
		
		String lastActiveDayDateFormat = lastActiveDay.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
		
		lastDayText.setText(String.format("Last active day was %s\n%s", lastActiveDayDateFormat, weeklySummary));
		
		//Formatting the Year/Week Panel
		yearWeek = new JPanel();
		yearWeek.setBounds(0, 60, 1000, 40);
		yearWeek.setBackground(null);
		yearWeek.setLayout(new GridLayout());
		
		thisYear = new JLabel(String.format("\t\t%d Totals: %s", today.getYear(), yearlyTotals));
		thisYear.setFont(subTitleFont);
		thisYear.setHorizontalAlignment(JLabel.LEFT);
		thisWeek = new JLabel(String.format("Week %d Totals: %s\t\t", weekNumber, weeklyTotals));
		thisWeek.setFont(subTitleFont);
		thisWeek.setHorizontalAlignment(JLabel.RIGHT);
		
		yearWeek.add(thisYear);
		yearWeek.add(thisWeek);
				
		//Formatting the activities panel
		activities = new JPanel();
		activities.setBounds(500, 100, 500, 500);
		activities.setBackground(null);
		activities.setLayout(new GridLayout(2,2));

		runButton = new JButton(String.format("Run Activities: %d", runTotal), runIcon);
		runButton.setFont(buttonFont);
		runButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		runButton.setHorizontalTextPosition(SwingConstants.CENTER);
		runButton.addActionListener(runButtonListener);

		bikeButton = new JButton(String.format("Bike Activities: %d", bikeTotal), bikeIcon);
		bikeButton.setFont(buttonFont);
		bikeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		bikeButton.setHorizontalTextPosition(SwingConstants.CENTER);
		bikeButton.addActionListener(bikeButtonListener);
		
		swimButton = new JButton(String.format("Swim Activities: %d", swimTotal), swimIcon);
		swimButton.setFont(buttonFont);
		swimButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		swimButton.setHorizontalTextPosition(SwingConstants.CENTER);
		swimButton.addActionListener(swimButtonListener);
		
		strengthButton = new JButton(String.format("Strength Activities: %d", strengthTotal), strengthIcon);
		strengthButton.setFont(buttonFont);
		strengthButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		strengthButton.setHorizontalTextPosition(SwingConstants.CENTER);
		strengthButton.addActionListener(strengthButtonListener);
		
		activities.add(runButton);
		activities.add(bikeButton);
		activities.add(swimButton);
		activities.add(strengthButton);
		
		clickPromptPanel = new JPanel();
		clickPromptPanel.setBounds(500, 597, 500, 20);
		clickPromptPanel.setBackground(null);
		
		clickToView = new JLabel("Click activity icon to view details.");
		clickToView.setFont(textFont);
		clickPromptPanel.add(clickToView);
		
		
		//Formatting the prevNext week panel
		prevNextWeek = new JPanel();
		prevNextWeek.setBounds(0, 620, 500, 60);
		prevNextWeek.setBackground(null);
		prevNextWeek.setLayout(new GridLayout());
		
		prevWeekButton = new JButton("Previous Week");
		prevWeekButton.setFont(subTitleFont);
		prevWeekButton.addActionListener(prevButtonListener);
		nextWeekButton = new JButton("Next Week");
		nextWeekButton.setFont(subTitleFont);
		nextWeekButton.addActionListener(nextButtonListener);
		
		prevNextWeek.add(prevWeekButton);
		prevNextWeek.add(nextWeekButton);
		
		//Formatting the addActivity Panel
		addActivity = new JPanel();
		addActivity.setBounds(500, 620, 500, 60);
		addActivity.setBackground(null);
		addActivity.setLayout(new GridLayout());
		
		addActivityButton = new JButton("Add Activity");
		addActivityButton.setFont(subTitleFont);
		addActivityButton.addActionListener(addActivityButtonListener);
		
		addActivity.add(addActivityButton);
		
		//Adding Panels to Window
		window.add(yearWeek);
		window.add(activities);
		window.add(clickPromptPanel);
		window.add(prevNextWeek);
		window.add(addActivity);
		window.setVisible(true);
		
	}
	
	
	//Finds the last active day by finding the last day that had an instance of an activity
	private void lastActiveDay() {
		
		//Adds days to the year array. 
		for(int i = 1;i <= 365; i++) {
			ActiveDay newDay = new ActiveDay("Reading 1", i);
			year[i] = newDay;
		}
		
		int dayOfYear = today.getDayOfYear();
		
		while (year[dayOfYear].runs.size() == 0 && year[dayOfYear].bikes.size() == 0 && year[dayOfYear].swims.size() == 0 && year[dayOfYear].strengths.size() == 0 && year[dayOfYear].steps.size() == 0) {
			
			dayOfYear--;
			
		}
		
		lastActiveDay = LocalDate.now().withDayOfYear(dayOfYear);
	}
	
	//Calculates the total distance and minutes from the years activities
	private String yearlyTotal() {
		
		for (int i = 1; i <= today.getDayOfYear(); i++) {
			
			yearlyMiles = yearlyMiles + year[i].getTotalDistance();
			yearlyMinutes = yearlyMinutes + year[i].getTotalMinutes();
			
		}
		
		return yearlyTotals = String.format("%.2f Miles, %d Minutes", yearlyMiles, yearlyMinutes);
	}
	
	//Calculate the total distance and minutes from the weeks activities
	private String weeklyTotal() {
		
		if (weekNumber == today.get(weekFields.weekOfWeekBasedYear())) {
			
			weeklyMiles = 0;
			weeklyMinutes = 0;
			
			int daysInWeek = FitnessTrackerManager2.getDaysInCurrentWeek(today.getDayOfYear());
			
			for (int i = 0; i < daysInWeek; i++) {
				
				int currentDay = today.getDayOfYear() - i;
				
				weeklyMiles = weeklyMiles + year[currentDay].getTotalDistance();
				weeklyMinutes = weeklyMinutes + year[currentDay].getTotalMinutes();
				
			}
		}
		else {
			
			weeklyMiles = 0;
			weeklyMinutes = 0;
			
			int daysInWeek = FitnessTrackerManager2.getDaysInCurrentWeek(endOfLastWeek.getDayOfYear());
			
			if (endOfLastWeek.getDayOfYear() > 7) {
				for (int i = 0; i < daysInWeek; i++) {
					
					int currentDay = endOfLastWeek.getDayOfYear() - i;
					
					weeklyMiles = weeklyMiles + year[currentDay].getTotalDistance();
					weeklyMinutes = weeklyMinutes + year[currentDay].getTotalMinutes();
					
				}
			}
			else {
				for (int i = 0; i < endOfLastWeek.getDayOfYear(); i++) {
					
					int currentDay = endOfLastWeek.getDayOfYear() - i;
					
					weeklyMiles = weeklyMiles + year[currentDay].getTotalDistance();
					weeklyMinutes = weeklyMinutes + year[currentDay].getTotalMinutes();
					
				}
			}
			
		}
		
		return weeklyTotals = String.format("%.2f Miles, %d Minutes", weeklyMiles, weeklyMinutes);
		
	}
	
	//Generates and prints a summary for the week into the JTextArea in the GUI
	private String weeklySummary() {
		
		if (weekNumber == today.get(weekFields.weekOfWeekBasedYear())) {
			
			weeklySummary = "";
			
			int daysInWeek = FitnessTrackerManager2.getDaysInCurrentWeek(today.getDayOfYear());
			
			for (int i = 0; i < daysInWeek; i++) {
				
				ActiveDay currentDay = year[today.getDayOfYear() - i];
				LocalDate currentDate = LocalDate.now().withDayOfYear(today.getDayOfYear() - i);
				
				//The result of the toString method from ActiveDay for no activities. We ignore this in the GUI.
				String skipString = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "No Activities Done Today.\n"
						, currentDay.getDayOfYear(), currentDate.getDayOfWeek(), currentDay.getTotalMinutes(), currentDay.getTotalCalories(), currentDay.getTotalSteps());
				
				if (!currentDay.toString().equals(skipString)) {
					weeklySummary = weeklySummary + currentDay.toString();
				}
				else if (currentDay.toString().equals(skipString) && weeklySummary != "") {
					weeklySummary = weeklySummary + currentDay.toString();
				}
				
			}
		}
		else {
			
			weeklySummary = "";
			int daysInWeek = FitnessTrackerManager2.getDaysInCurrentWeek(endOfLastWeek.getDayOfYear());
			
			if (endOfLastWeek.getDayOfYear() > 7) {
				for (int i = 0; i < daysInWeek; i++) {
					
					ActiveDay currentDay = year[endOfLastWeek.getDayOfYear() - i];
					LocalDate currentDate = LocalDate.now().withDayOfYear(endOfLastWeek.getDayOfYear() - i);
					
					//The result of the toString method from ActiveDay for no activities. We ignore this in the GUI.
					String skipString = String.format("\n=======================================\n"
							+ "Summary of Day %s: %s\n"
							+ "=======================================\n"
							+ "Total Instensity Minutes: %d\n"
							+ "Total Calories Burned: %d\n"
							+ "Total Steps: %d\n"
							+ "---------------------------------------\n"
							+ "No Activities Done Today.\n"
							, currentDay.getDayOfYear(), currentDate.getDayOfWeek(), currentDay.getTotalMinutes(), currentDay.getTotalCalories(), currentDay.getTotalSteps());
					
					if (!currentDay.toString().equals(skipString)) {
						weeklySummary = weeklySummary + currentDay.toString();
					}
					else if (currentDay.toString().equals(skipString) && weeklySummary != "") {
						weeklySummary = weeklySummary + currentDay.toString();
					}
				}
			}
			else {
				for (int i = 0; i < endOfLastWeek.getDayOfYear(); i++) {
									
					ActiveDay currentDay = year[endOfLastWeek.getDayOfYear() - i];
					LocalDate currentDate = LocalDate.now().withDayOfYear(endOfLastWeek.getDayOfYear() - i);
					
					//The result of the toString method from ActiveDay for no activities. We ignore this in the GUI.
					String skipString = String.format("\n=======================================\n"
							+ "Summary of Day %s: %s\n"
							+ "=======================================\n"
							+ "Total Instensity Minutes: %d\n"
							+ "Total Calories Burned: %d\n"
							+ "Total Steps: %d\n"
							+ "---------------------------------------\n"
							+ "No Activities Done Today.\n"
							, currentDay.getDayOfYear(), currentDate.getDayOfWeek(), currentDay.getTotalMinutes(), currentDay.getTotalCalories(), currentDay.getTotalSteps());
					
					if (!currentDay.toString().equals(skipString)) {
						weeklySummary = weeklySummary + currentDay.toString();
					}
					else if (currentDay.toString().equals(skipString) && weeklySummary != "") {
						weeklySummary = weeklySummary + currentDay.toString();
					}
				}
			}

		}
		
		if (weeklySummary == "") {
			weeklySummary = "\nNo activities done this week.";
		}
		
		return weeklySummary;
		
	}
	
	//Calculates the total amount of each activity done in the year
	public void activitiesTotal() {
		
		for(int i = 1; i <= today.getDayOfYear(); i++) {
			
			runTotal = runTotal + year[i].runs.size();
			bikeTotal = bikeTotal + year[i].bikes.size();
			swimTotal = swimTotal + year[i].swims.size();
			strengthTotal = strengthTotal + year[i].strengths.size();
			
		}
		
	}
	
	//Allows the user to add an activity
	public void addActivity() {
		
		try {
			
		BufferedWriter bw = new BufferedWriter(new FileWriter("data/activities.txt", true));
		
		String type = JOptionPane.showInputDialog("Enter the type of activity using the following three letter keycodes: RUN = run, BKE = bike, SWM = swim, or STR = strength).");
		String day = JOptionPane.showInputDialog("Enter the day of the activitiy (1-365).");
		String title = JOptionPane.showInputDialog("Enter a title for your activitiy.");
		String description = JOptionPane.showInputDialog("Enter a description for your activitiy.");
		String calories = JOptionPane.showInputDialog("Enter the amount of calories burned.");
		String minutes = JOptionPane.showInputDialog("Enter the amount of minutes spent doing the activity");
		String distance = "";
		if (type != null && !type.equals("STR")){
			distance = JOptionPane.showInputDialog("Enter the distance ran,biked, or swam.");
		}
		String location = JOptionPane.showInputDialog("Enter the location of your activity.");
		
		String fileFormat = null;
		if (type != null && day != null && title != null && description != null && calories != null && minutes != null && location!= null) {
			if (!distance.equals("")) {
				fileFormat = String.format("%s@@%s@@%s@@%s@@%s@@%s@@%s@@%s", type, day, title, description, calories, minutes, distance, location);
			}
			else {
				fileFormat = String.format("%s@@%s@@%s@@%s@@%s@@%s@@%s@@%s", type, day, title, description, calories, minutes, location);
			}
		}
	
		JOptionPane.showMessageDialog(window, "Restart to see changes.");
		
		bw.newLine();
		
		if (fileFormat != null) {
			bw.write(fileFormat);
		}
		
		bw.close();

		FitnessTrackerManager2.readActivities(new File(selectedFile.getAbsolutePath()));
			
		}
		catch(IOException e){
			
		}
	
		window.setVisible(false);
		window.invalidate();
		window.revalidate();
		window.repaint();
		window.setVisible(true);
		
	}
	
	//Button Listeners
	private class selectActivitiesFileListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			//Setting up the File Chooser
			//File Chooser
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File("./data"));
			int result = fc.showOpenDialog(window);
			
			if(result == JFileChooser.APPROVE_OPTION) {
				selectedFile = fc.getSelectedFile();
				FitnessTrackerManager2.readActivities(selectedFile);
				lastDayText.setText(FitnessTrackerManager2.getAddedActivities());
			}
			else {
				lastDayText.setText(FitnessTrackerManager2.getExceptionMessage());
			}
		}
	}
	
	private class selectStepsFileListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			//Setting up the File Chooser
			//File Chooser
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File("./data"));
			int result = fc.showOpenDialog(window);
			
			if(result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fc.getSelectedFile();
				FitnessTrackerManager2.readSteps(selectedFile);
				lastDayText.append("\nSteps file added!");
				createFitnessTrackerGUIMainScreen();
			}
			else {
				lastDayText.append(FitnessTrackerManager2.getExceptionMessage());
			}
		}
	}
	
	private class prevButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			if (weekNumber == today.get(weekFields.weekOfWeekBasedYear())) {
				 endOfLastWeek = LocalDate.now().minusDays(FitnessTrackerManager2.getDaysInCurrentWeek(today.getDayOfYear()));
				 beginningOfLastWeek = endOfLastWeek.minusDays(7);
				 String endOfLastWeekDateFormat = endOfLastWeek.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
				 String beginningOfLastWeekDateFormat = beginningOfLastWeek.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
				 
				 weekNumber--;
				 weeklyTotal();
				 weeklySummary();
				 thisWeek.setText(String.format("Week %d Totals: %s\t\t", weekNumber, weeklyTotals));
				 lastDayText.setText(String.format("Week %d(%s - %s) Summary:\n%s", weekNumber, beginningOfLastWeekDateFormat, endOfLastWeekDateFormat, weeklySummary));

				 
				 window.repaint();
			}
			else if (weekNumber > 1 && endOfLastWeek.getDayOfYear() > 7) {
				
				weekNumber--;
				endOfLastWeek = LocalDate.now().withDayOfYear(endOfLastWeek.getDayOfYear()-7);
				beginningOfLastWeek = endOfLastWeek.minusDays(7);
				String endOfLastWeekDateFormat = endOfLastWeek.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
				String beginningOfLastWeekDateFormat = beginningOfLastWeek.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
				
				weeklyTotal();
				weeklySummary();
				thisWeek.setText(String.format("Week %d Totals: %s\t\t", weekNumber, weeklyTotals));
				lastDayText.setText(String.format("Week %d(%s - %s) Summary:\n%s", weekNumber, beginningOfLastWeekDateFormat, endOfLastWeekDateFormat, weeklySummary));
				window.repaint();
				
			}
			else if (weekNumber > 1) {
				
				weekNumber--;
				beginningOfLastWeek = endOfLastWeek.minusDays(endOfLastWeek.getDayOfYear()-1);
				String endOfLastWeekDateFormat = endOfLastWeek.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
				String beginningOfLastWeekDateFormat = beginningOfLastWeek.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
				
				weeklyTotal();
				weeklySummary();
				thisWeek.setText(String.format("Week %d Totals: %s\t\t", weekNumber, weeklyTotals));
				lastDayText.setText(String.format("Week %d(%s - %s) Summary:\n%s", weekNumber, beginningOfLastWeekDateFormat, endOfLastWeekDateFormat, weeklySummary));
				window.repaint();
				
			}
		}
	}

	private class nextButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			if (weekNumber < today.get(weekFields.weekOfWeekBasedYear())) {
				
				weekNumber++;
				endOfLastWeek = LocalDate.now().withDayOfYear(endOfLastWeek.getDayOfYear()+7);
				beginningOfLastWeek = endOfLastWeek.minusDays(7);
				String endOfLastWeekDateFormat = endOfLastWeek.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
				String beginningOfLastWeekDateFormat = beginningOfLastWeek.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
				
				weeklyTotal();
				weeklySummary();
				thisWeek.setText(String.format("Week %d Totals: %s\t\t", weekNumber, weeklyTotals));
				lastDayText.setText(String.format("Week %d(%s - %s) Summary:\n%s", weekNumber, beginningOfLastWeekDateFormat, endOfLastWeekDateFormat, weeklySummary));
				window.repaint();
				

			}

		}
	}
	
	private class runButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			String runsString = "";
			
			for (int i = 1; i <= today.getDayOfYear(); i ++) {
				if(year[i].runs.size() > 0) {
					String currentRun = year[i].runs.toString();
					currentRun = currentRun.substring(1, currentRun.length() - 1);
					runsString = runsString + currentRun;
				}
			}
			
			lastDayText.setText(String.format("Summary of All Run Activities:\n\n%s", runsString));
			
		}
	}
	
	private class bikeButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			String bikesString = "";
			
			for (int i = 1; i <= today.getDayOfYear(); i ++) {
				if(year[i].bikes.size() > 0) {
					String currentBike = year[i].bikes.toString();
					currentBike = currentBike.substring(1, currentBike.length() - 1);
					bikesString = bikesString + currentBike;
				}
			}
			
			lastDayText.setText(String.format("Summary of All Bike Activities:\n\n%s", bikesString));
			
		}
	}
	
	private class swimButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			String swimsString = "";
			
			for (int i = 1; i <= today.getDayOfYear(); i ++) {
				if(year[i].swims.size() > 0) {
					String currentSwim = year[i].swims.toString();
					currentSwim = currentSwim.substring(1, currentSwim.length() - 1);
					swimsString = swimsString + currentSwim;
				}
			}

			lastDayText.setText(String.format("Summary of All Swim Activities:\n\n%s", swimsString));
			
		}
	}
	private class strengthButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			String strengthsString = "";
			
			for (int i = 1; i <= today.getDayOfYear(); i ++) {
				if(year[i].strengths.size() > 0) {
					String currentStrength = year[i].strengths.toString();
					currentStrength = currentStrength.substring(1, currentStrength.length() - 1);
					strengthsString = strengthsString + currentStrength;
				}
			}

			lastDayText.setText(String.format("Summary of Strength Run Activities:\n\n%s", strengthsString));
			
		}
	}
	
	private class addActivityButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			addActivity();
		}
	}
}
