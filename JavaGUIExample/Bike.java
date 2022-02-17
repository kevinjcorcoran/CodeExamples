/*
 * This class creates a bike activity.  It give the activity a title, location, notes, calories burned, 
 * the minutes the workout took, and the distance traveled.
 */

public class Bike extends Activity {


	
	//Constructor for a bike activity
	public Bike(String t, String l, String n, int c, int m, double d, int day) {
		
		super(t, l, n, c, m, d, day);
		
	}

	//Creates a toString format for the activity that matches the rest of the activities
	@Override
	public String toString() {
		getTitle();
		getLocation();
		getNotes();
		getCalories();
		getMinutes();
		getDistance();
		String str = String.format("BIKE: %s\n"
				+ "DATE: %s\n"
				+ "LOCATION: %s\n"
				+ "NOTES: %s\n"
				+ "CALORIES: %d calories\n"
				+ "MINUTES: %d min\n"
				+ "DISTANCE: %.2f miles\n"
				+ "---------------------------------------\n", 
				title, dateString, location, notes, calories, minutes, distance);
		return str;
	}

}
