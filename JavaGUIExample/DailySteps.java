public class DailySteps {

	private int dayOfYear;
	private int numberOfSteps;

	public DailySteps(int d, int s) {
		dayOfYear = d;
		numberOfSteps = s;
	}
	
	//Getters and Setters
	public int getDayOfYear() {
		return dayOfYear;
	}
	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}
	public int getNumberOfSteps() {
		return numberOfSteps;
	}
	public void setNumberOfSteps(int numberOfSteps) {
		this.numberOfSteps = numberOfSteps;
	}
	
	
}
