package AOFiles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import util.DBConnector;
import util.Parser;


public class TicketManager {
		private ArrayList<Ticket> basket;
		private Ticket ticket;
		private double total;
		private int numOfTickets;
		private Parser reader;
		private DBConnector db;
		
		public TicketManager() {
			basket = new ArrayList<Ticket>();
			ticket = new Ticket();
			reader = new Parser();
		}
		
		
	
		public void addToBasket() {
			Ticket ticket = new Ticket();
			addToBasket(ticket);
		}
		
		public void checkoutBasket() {
			for (int i = 0; i < basket.size(); i++) {
				Ticket ticket = basket.get(i);
				double price = ticket.getTicketPrice();
				this.total += price;
			}
		}
		
		public void addToBasket(Ticket ticket) {
			boolean isBuying = false;
				while (!isBuying) {
					basket.add(ticket);				  
					//String input = reader.getText("Buy ticket: Y/N");
					String input = reader.getInputForMenu();
					if (!input.equals("y") ) {	
						isBuying = false;
					}
			}
		}
		
		
		public void displayTickets() {
			 System.out.println("Your ordered: "+ basket.size() + " tickets \nTotal cost: "+ total);	 
		}
		
		
		public ArrayList<Ticket> getTickets(){
			return basket;
		}
		
		public double getTotal(){
			return total;
		}
		
	
}
