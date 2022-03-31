package model;

public class Listener  {
	
	private OfficeManager manager;
	/**
	 * Constructor for a Listener
	 */
	public Listener(OfficeManager manager) {
		this.manager = manager;
	}

	public void receiveNotification(String[] msgs){
		manager.showStatus(msgs);
	}
	
}
