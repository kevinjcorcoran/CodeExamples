/*
 * This class creates an Strength training activity.  It give the activity a title, location, notes, calories burned, and minutes that the workout took.
 */
public class Strength extends Activity {
	
	
	
	//Constructor for a strength training activity, does not include distance because it is not a distance activity
	public Strength(String t, String l, String n, int c, int m, int day) {
		
		super(t, l, n, c, m, day);
		
	}

	//Creates a toString format for the activity that matches the rest of the activities
	@Override
	public String toString() {
		getTitle();
		getLocation();
		getNotes();
		getCalories();
		getMinutes();
		String str = String.format("STRENGTH: %s\n"
				+ "DATE: %s\n"
				+ "LOCATION: %s\n"
				+ "NOTES: %s\n"
				+ "CALORIES: %d calories\n"
				+ "MINUTES: %d min\n"
				+ "---------------------------------------\n",
				title, dateString, location, notes, calories, minutes);
		return str;
		
	}

}
