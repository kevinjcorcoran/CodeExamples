import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Activity {
	
	String title;
	String location;
	String notes;
	int calories;
	int minutes;
	double distance;
	int dayOfYear;
	String dateString = "N/A";
	
	//Constructor for a Run, bike, and Swim activities
	public Activity(String t, String l, String n, int c, int m, double d, int day) {
		setTitle(t);
		setLocation(l);
		setNotes(n);
		setCalories(c);
		setMinutes(m);
		setDistance(d);
		setDayOfYear(day);
		
		if(dayOfYear > 0) {
			LocalDate date = LocalDate.now().withDayOfYear(dayOfYear);
			dateString = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
		}
	}
	
	//Constructor for a Strength activities
	public Activity(String t, String l, String n, int c, int m, int day) {
		setTitle(t);
		setLocation(l);
		setNotes(n);
		setCalories(c);
		setMinutes(m);
		setDayOfYear(day);
		
		if(dayOfYear > 0) {
			LocalDate date = LocalDate.now().withDayOfYear(dayOfYear);
			dateString = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
		}
	}


	//Getters and Setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}


	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getDayOfYear() {
		return dayOfYear;
	}

	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}
}
