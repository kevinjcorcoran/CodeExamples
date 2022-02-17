import java.util.ArrayList;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class ActiveDay {

	private int dayOfYear;
	private int totalMinutes = 0;
	private int totalCalories = 0;
	private int totalSteps = 0;
	private double totalDistance = 0;
	private boolean dateIsValid = false;
	private String activeDayPrint; //A variable to hold the string from the toString method, used for unit testing
	private String readingTitle; //Adds "empty" parameter to allow for a new File reading constructor.  readingTitle will be the a name the user gives to the current reading
	Run run;
	Bike bike;
	Swim swim;
	Strength strength;
	DailySteps step;
	//Arrays to take activities from FitnessTrackerManager place them into new Arrays for certain days
	ArrayList<Run> runs = new ArrayList<>();
	ArrayList<Bike> bikes = new ArrayList<>();
	ArrayList<Swim> swims = new ArrayList<>();
	ArrayList<Strength> strengths = new ArrayList<>();
	ArrayList<DailySteps> steps = new ArrayList<>();
	
	
	//Constructor
	public ActiveDay(String f, int d) {
		setReadingTitle(f); 
		setDayOfYear(d);
		dateCheck();
	
		//Adds data from FitnessTrackerManager2 into new arrays for day d and calculates the fields for that day
		for (Run nextRun: FitnessTrackerManager2.getRuns()) {
			if (nextRun.getDayOfYear() == d) {
				run = nextRun;
				runs.add(run);
				totalMinutes += run.getMinutes();
				totalCalories += run.getCalories();
				setTotalDistance(getTotalDistance() + run.getDistance());
			}
		}
		for (Bike nextBike: FitnessTrackerManager2.getBikes()) {
			if (nextBike.getDayOfYear() == d) {
				bike = nextBike;
				bikes.add(bike);
				totalMinutes += bike.getMinutes();
				totalCalories += bike.getCalories();
				setTotalDistance(getTotalDistance() + bike.getDistance());
			} 
		}
		for (Swim nextSwim: FitnessTrackerManager2.getSwims()) {
			if (nextSwim.getDayOfYear() == d) {
				swim = nextSwim;
				swims.add(swim);
				totalMinutes += swim.getMinutes();
				totalCalories += swim.getCalories();
				setTotalDistance(getTotalDistance() + swim.getDistance());
			}
		}
		for (Strength nextStrength: FitnessTrackerManager2.getStrengths()) {
			if (nextStrength.getDayOfYear() == d) {
				strength = nextStrength;
				strengths.add(strength);
				totalMinutes += strength.getMinutes();
				totalCalories += strength.getCalories();
			}
		}
		for (DailySteps nextSteps: FitnessTrackerManager2.getSteps()) {
			if (nextSteps.getDayOfYear() == d) {
				step = nextSteps;
				steps.add(step);
				totalSteps += step.getNumberOfSteps();
			}
		}
		
	}
	
	//No activities constructor
		public ActiveDay(int d) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = 0;
			totalCalories = 0;
		}
		
		//All activities constructor
		public ActiveDay(int d, Run r, Bike b, Swim s, Strength l) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = r.getMinutes() + b.getMinutes() + s.getMinutes() + l.getMinutes();
			totalCalories = r.getCalories() + b.getCalories() + s.getCalories() + l.getCalories();
			run = r;
			bike = b;
			swim = s;
			strength = l;
		}
		
		//Run constructor
		public ActiveDay(int d, Run r) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = r.getMinutes();
			totalCalories = r.getCalories();
			run = r;
		}
		
		//Bike constructor
		public ActiveDay(int d, Bike b) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = b.getMinutes();
			totalCalories = b.getCalories();
			bike = b;
		}
		
		//Swim constructor
		public ActiveDay(int d, Swim s) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = s.getMinutes();
			totalCalories = s.getCalories();
			swim = s;
		}
		
		//Strength constructor
		public ActiveDay(int d, Strength l) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = l.getMinutes();
			totalCalories = l.getCalories();
			strength = l;
		}
		
		//Run and bike constructor
		public ActiveDay(int d, Run r, Bike b) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = r.getMinutes() + b.getMinutes();
			totalCalories = r.getCalories() + b.getCalories();
			run = r;
			bike = b;
		}
		
		//Run and Swim constructor
		public ActiveDay(int d, Run r, Swim s) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = r.getMinutes() + s.getMinutes();
			totalCalories = r.getCalories() + s.getCalories();
			run = r;
			swim = s;
		}
		
		//Run and Strength constructor
		public ActiveDay(int d, Run r, Strength l) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = r.getMinutes() + l.getMinutes();
			totalCalories = r.getCalories() + l.getCalories();
			run = r;
			strength = l;
		}
		
		//Bike and Swim constructor
		public ActiveDay(int d,Bike b, Swim s) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = b.getMinutes() + s.getMinutes();
			totalCalories = + b.getCalories() + s.getCalories();
			bike = b;
			swim = s;
		}
		
		//Bike and Strength constructor
		public ActiveDay(int d, Bike b, Strength l) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = b.getMinutes() + l.getMinutes();
			totalCalories = b.getCalories() + l.getCalories();
			bike = b;
			strength = l;
		}
		
		//Swim and Strength constructor
		public ActiveDay(int d, Swim s, Strength l) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = s.getMinutes() + l.getMinutes();
			totalCalories = s.getCalories() + l.getCalories();
			swim = s;
			strength = l;
		}
		
		//Run, bike, and swim constructor
		public ActiveDay(int d, Run r, Bike b, Swim s) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = r.getMinutes() + b.getMinutes() + s.getMinutes();
			totalCalories = r.getCalories() + b.getCalories() + s.getCalories();
			run = r;
			bike = b;
			swim = s;
		}
		
		//Run, bike, and strength constructor
		public ActiveDay(int d, Run r, Bike b, Strength l) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = r.getMinutes() + b.getMinutes() + l.getMinutes();
			totalCalories = r.getCalories() + b.getCalories() + l.getCalories();
			run = r;
			bike = b;
			strength = l;
		}
		
		//Run, swim, and strength constructor
		public ActiveDay(int d, Run r, Swim s, Strength l) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = r.getMinutes() + s.getMinutes() + l.getMinutes();
			totalCalories = r.getCalories() + s.getCalories() + l.getCalories();
			run = r;
			swim = s;
			strength = l;
		}
		
		//Bike, swim, strength constructor
		public ActiveDay(int d,Bike b, Swim s, Strength l) {
			setDayOfYear(d);
			dateCheck();
			totalMinutes = b.getMinutes() + s.getMinutes() + l.getMinutes();
			totalCalories = b.getCalories() + s.getCalories() + l.getCalories();
			bike = b;
			swim = s;
			strength = l;
		}
	
	//To string method that returns different strings depending on which, if any, activities are created that day
	@Override
	public String toString() {

		//Creates an output string and strings for each activity.
		//Each activity string combines the returned toString strings for a certain day.
		String str;
		String runString = "";
			for (Run nextRun: runs) {
				run = nextRun;
				runString = runString + run.toString();
			}
		String bikeString = "";
			for (Bike nextBike: bikes) {
				bike = nextBike;
				bikeString = bikeString + bike.toString();
			}
		String swimString = "";
			for (Swim nextSwim: swims) {
				swim = nextSwim;
				swimString = swimString + swim.toString();
			}
		String strengthString = "";
			for (Strength nextStrength: strengths) {
				strength = nextStrength;
				strengthString = strengthString + strength.toString();
			}
		try {
			DayOfWeek dayOfWeek;
			LocalDate today = LocalDate.now().withDayOfYear(dayOfYear);
			dayOfWeek = today.getDayOfWeek();
			
			
			//No activities
			if ((runs.size() == 0) && (bikes.size() == 0) && (swims.size() == 0) && (strengths.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "No Activities Done Today.\n"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps);
			}
			
			//Run only
			else if ((bikes.size() == 0) && (swims.size() == 0) && (strengths.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, runString);
			}
			
			//Bike only
			else if ((runs.size() == 0) && (swims.size() == 0) && (strengths.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, bikeString);
			}
			
			//Swim only
			else if ((runs.size() == 0) && (bikes.size() == 0) && (strengths.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, swimString);
			}
			
			//Strength only
			else if ((runs.size() == 0) && (bikes.size() == 0) && (swims.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, strengthString);
			}
			
			
			//Run and Bike
			else if ((swims.size() == 0) && (strengths.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, runString, bikeString);
			}
			
			//Run and Swim
			else if ((bikes.size() == 0) && (strengths.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, runString, swimString);
			}
			
			//Run and Strength
			else if ((bikes.size() == 0) && (swims.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, runString, strengthString);
			}
			
			//Bike and Swim
			else if ((runs.size() == 0) && (strengths.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, bikeString, swimString);
			}
			
			//Bike and Strength
			else if ((runs.size() == 0) && (swims.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, bikeString, strengthString);
			}
			
			//Swim and Strength
			else if ((runs.size() == 0) && (bikes.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, swimString, strengthString);
			}
			
			//Run, bike, and swim
			else if ((strengths.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						+ "%s"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, runString, bikeString, swimString);
			}
			
			//Run,bike, and strength
			else if ((swims.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						+ "%s"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, runString, bikeString, strengthString);
			}
			
			//Run, Swim, and Strength
			else if ((bikes.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						+ "%s"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, runString, swimString, strengthString);
			}
			
			//Bike, Swim, and Strength
			else if ((runs.size() == 0)) {
				str = String.format("\n=======================================\n"
						+ "Summary of Day %s: %s\n"
						+ "=======================================\n"
						+ "Total Instensity Minutes: %d\n"
						+ "Total Calories Burned: %d\n"
						+ "Total Steps: %d\n"
						+ "---------------------------------------\n"
						+ "%s"
						+ "%s"
						+ "%s"
						, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, bikeString, swimString, strengthString);
			}
			
			//All Activities
			else {
				str = String.format("\n=======================================\n"
					+ "Summary of Day %s: %s\n"
					+ "=======================================\n"
					+ "Total Instensity Minutes: %d\n"
					+ "Total Calories Burned: %d\n"
					+ "Total Steps: %d\n"
					+ "---------------------------------------\n"
					+ "%s"
					+ "%s"
					+ "%s"
					+ "%s"
					, dayOfYear, dayOfWeek, totalMinutes, totalCalories, totalSteps, runString, bikeString, swimString, strengthString);
			}
			setActiveDayPrint(str);
		}
		//If the date is invalid and the user tries to print that Active Day, it will tell the user that the date is invalid. 	
		catch (java.time.DateTimeException e) {
			str = "Could not print Active Day because the date is invalid.";
			setActiveDayPrint(str);
		}
		
		
		
		return str;
	}
	
	//Makes sure the user enters a number between 1 and 365 for the day
	public boolean dateCheck () {
		if (dayOfYear >= 1 && dayOfYear <= 365) {
			return dateIsValid = true;
		} else {
			System.out.println("Invalid date: Must enter a whole number between 1 and 365.");
			return dateIsValid = false;
		}
	}
	
	//Getters and Setters
	public int getDayOfYear() {
		return dayOfYear;
	}

	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}

	public int getTotalMinutes() {
		return totalMinutes;
	}

	public void setTotalMinutes(int totalMinutes) {
		this.totalMinutes = totalMinutes;
	}

	public int getTotalCalories() {
		return totalCalories;
	}

	public void setTotalCalories(int totalCalories) {
		this.totalCalories = totalCalories;
	}

	public boolean getDateIsValid() {
		return dateIsValid;
	}

	public void setDateIsValid(boolean dateIsValid) {
		this.dateIsValid = dateIsValid;
	}

	public String getActiveDayPrint() {
		return activeDayPrint;
	}

	public void setActiveDayPrint(String activeDayPrint) {
		this.activeDayPrint = activeDayPrint;
	}

	public int getTotalSteps() {
		return totalSteps;
	}

	public void setTotalSteps(int totalSteps) {
		this.totalSteps = totalSteps;
	}

	public String getReadingTitle() {
		return readingTitle;
	}

	public void setReadingTitle(String readingTitle) {
		this.readingTitle = readingTitle;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}



}
