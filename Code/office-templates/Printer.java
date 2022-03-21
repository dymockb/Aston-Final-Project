package model;

public class Printer extends OfficeMachine {
		
	/**
	 * Constructor for a Printer
	 */
	public Printer() {
		super();
		machineDesc = "Printer";
		machineType = "PRT";
	}

	public void processJob(){
		if(this.job != null){
			System.out.println("Job " + job.getJobString() + " printing...");
			System.out.println(" - " + job.getJobDescription());
			this.job = null;
		} else {
			System.out.println("Nothing to print.");
		}

	}

	
}
