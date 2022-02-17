import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class FitnessTrackerManager2 {

	//Creates variables expected to be sent to ActiveDay
	private static String type;
	private static int dayOfYear;
	private static String title;
	private static String notes;
	private static int calories;
	private static int minutes;
	private static double distance;
	private static String location;
	private static String addedActivities;
	private static String exceptionMessage;
	
	//Creates array lists to store the activities from the file being read
	private static ArrayList<Run> runs = new ArrayList<>();
	private static ArrayList<Bike> bikes = new ArrayList<>();
	private static ArrayList<Swim> swims = new ArrayList<>();
	private static ArrayList<Strength> strengths = new ArrayList<>();
	private static ArrayList<DailySteps> steps = new ArrayList<>();
	
	//Reads an activities file
	public static void readActivities(File f) {
		
			//Counters to report invalid activity formats
			int runError = 0;
			int bikeError = 0;
			int swimError = 0;
			int strengthError = 0;
			int overallError = 0;

			//Reads, splits, and parses line from the file.  Then creates activity objects from the data and stores them into array lists.
			try {
				
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line = null;
				
				while ((line = br.readLine()) != null) { 
					
					try {
						
						//Splits each line into parts and parses them to correct variable type
						String [] parts = line.split("@@");
						
						//If the activity type is not valid, it will skip it and add to the overall error counter.
						if (!parts[0].equals("RUN") && !parts[0].equals("BKE") && !parts[0].equals("SWM") && !parts[0].equals("STR")) { 
							overallError++;
							continue;
						}
						
						type = parts[0].trim();
						dayOfYear = Integer.parseInt(parts[1].trim());
						title = parts[2].trim();
						notes = parts[3].trim();
						calories = Integer.parseInt(parts[4].trim());
						minutes = Integer.parseInt(parts[5].trim());

							//Creating activity objects from the file and storing them in respective array lists.
							if (type.equals("RUN")) {
								
								distance = Double.parseDouble(parts[6].trim());
								location = parts[7].trim();
								Run run = new Run(title, location, notes, calories, minutes, distance, dayOfYear);
								runs.add(run);
								
							}	
							else if (type.equals("BKE")) {
								
								distance = Double.parseDouble(parts[6].trim());
								location = parts[7].trim();
								Bike bike = new Bike(title, location, notes, calories, minutes, distance, dayOfYear);
								bikes.add(bike);	
								
							} else if (type.equals("SWM")) {
								
								distance = Double.parseDouble(parts[6].trim());
								location = parts[7].trim();
								Swim swim = new Swim(title, location, notes, calories, minutes, distance, dayOfYear);
								swims.add(swim);
								
							} else if (type.equals("STR")) {
				
								location = parts[6].trim();
								Strength strength = new Strength(title, location, notes, calories, minutes, dayOfYear);
								strengths.add(strength);
								
							}
					
						}
					//If the line is formatted incorrectly, it will add to the error counter for the respective activity type
					catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
						if (type.equals("RUN")) {
							runError++;
						}
						else if (type.equals("BKE")) {
							bikeError++;
						}
						else if (type.equals("SWM")) {
							swimError++;
						}
						else if (type.equals("STR")) {
							strengthError++;
						}
						continue; // if input is invalid, restart the loop
					}
					
				}
				
				br.close();
				fr.close();
			} 
			catch (IOException e) { //Catches IOException and prints the standard message
				setExceptionMessage(("Invalid file. Try one called activities.txt"));
			}	

		//Prints the results of reading the file. 
		setAddedActivities(String.format("Adding %d run acitivies.\nIngoring %d invalid run activities.\nAdding %d bike activities.\nIgnoring %d invalid bike activities.\n"
				+ "Adding %d swim activities.\nIgnoring %d invalid swim activities.\nAdding %d strength activivites.\nIgnoring %d invalid strength activities.\n%d activities ignored for invalid type.\n\nNow add a steps file.", 
				runs.size(), runError, bikes.size(), bikeError, swims.size(), swimError, strengths.size(), strengthError, overallError));
	
	}
	
	//Reads a steps file
	public static void readSteps(File f) {
		
		//Reads, splits, and parses each line and stores the data into an object. Then adds to steps array list. 
		try {
			
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			
			while ((line = br.readLine()) != null) { 
				
				try {
					
					//Splits each line into parts and parses them to correct variable type
					String [] parts = line.split("@@");
					if (parts.length == 0) { 
						System.out.println("Activity ignored. Invalid type.");
						continue;
					}
					dayOfYear = Integer.parseInt(parts[0].trim());
					int numberOfSteps = Integer.parseInt(parts[1].trim());
					
					DailySteps step = new DailySteps(dayOfYear, numberOfSteps);
					steps.add(step);
					
				}
				catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
			br.close();
			fr.close();
		}
		catch (IOException e) {
			setExceptionMessage(("Invalid file. Try one called steps.txt"));
		}
	}
	
	//Generates a random day of the year between 1 and 14
	public static int generateDayOfYear() {
		Random rn = new Random();
		dayOfYear =  rn.nextInt(14)+1;
		return dayOfYear;
	}
	
	//Get the amount of days in the week of the given day, returns this number as an integer. 
	public static int getDaysInCurrentWeek(int d) {
		int daysInCurrentWeek = 0;
		DayOfWeek dayOfWeek;
		LocalDate today = LocalDate.now().withDayOfYear(d);
		dayOfWeek = today.getDayOfWeek();
		
		if (dayOfWeek == DayOfWeek.MONDAY) {
			daysInCurrentWeek = 1;
		}
		else if (dayOfWeek == DayOfWeek.TUESDAY) {
			daysInCurrentWeek = 2;
		}
		else if (dayOfWeek == DayOfWeek.WEDNESDAY) {
			daysInCurrentWeek = 3;
		}
		else if (dayOfWeek == DayOfWeek.THURSDAY) {
			daysInCurrentWeek = 4;
		}
		else if (dayOfWeek == DayOfWeek.FRIDAY) {
			daysInCurrentWeek = 5;
		}
		else if (dayOfWeek == DayOfWeek.SATURDAY) {
			daysInCurrentWeek = 6;
		}
		else if (dayOfWeek == DayOfWeek.SUNDAY) {
			daysInCurrentWeek = 7;
		}
		
		return daysInCurrentWeek;
	}
	
	
	//Getters and setters
	public static String getType() {
		return type;
	}
	public static void setType(String t) {
		type = t;
	}
	public static int getDayOfYear() {
		return dayOfYear;
	}
	public static void setDayOfYear(int d) {
		dayOfYear = d;
	}
	public static String getTitle() {
		return title;
	}
	public static void setTitle(String t) {
		title = t;
	}
	public static String getNotes() {
		return notes;
	}
	public static void setNotes(String n) {
		notes = n;
	}
	public static int getCalories() {
		return calories;
	}
	public static void setCalories(int c) {
		calories = c;
	}
	public static int getMinutes() {
		return minutes;
	}
	public static void setMinutes(int m) {
		minutes = m;
	}
	public static double getDistance() {
		return distance;
	}
	public static void setDistance(double d) {
		distance = d;
	}
	public static String getLocation() {
		return location;
	}
	public static void setLocation(String l) {
		location = l;
	}
	public static ArrayList<Run> getRuns() {
		return runs;
	}
	public static void setRuns(ArrayList<Run> r) {
		runs = r;
	}
	public static ArrayList<Bike> getBikes() {
		return bikes;
	}
	public static void setBikes(ArrayList<Bike> b) {
		bikes = b;
	}
	public static ArrayList<Swim> getSwims() {
		return swims;
	}
	public static void setSwims(ArrayList<Swim> s) {
		swims = s;
	}
	public static ArrayList<Strength> getStrengths() {
		return strengths;
	}
	public static void setStrengths(ArrayList<Strength> s) {
		strengths = s;
	}
	public static ArrayList<DailySteps> getSteps() {
		return steps;
	}
	public static void setSteps(ArrayList<DailySteps> s) {
		steps = s;
	}

	public static String getAddedActivities() {
		return addedActivities;
	}

	public static void setAddedActivities(String addedActivities) {
		FitnessTrackerManager2.addedActivities = addedActivities;
	}

	public static String getExceptionMessage() {
		return exceptionMessage;
	}

	public static void setExceptionMessage(String exceptionMessage) {
		FitnessTrackerManager2.exceptionMessage = exceptionMessage;
	}
	
}
