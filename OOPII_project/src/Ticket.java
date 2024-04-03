import java.util.ArrayList;

public class Ticket {

	public Traveller freeTicket(City bestCity, ArrayList<Traveller> users) {
		double max = 0;
		double tmp = -1;
		Traveller t = users.get(0);
		for(Traveller pr1: users) {
			tmp = pr1.calculateSimilarity(bestCity);
			if(tmp >= max) {
				max = tmp;
				t = pr1;
			}
		}
		return t;
		
		//System.out.println("\n\nFree ticket winner to traveller in " + bestCity.getName() + " is: " + t);
	}
	
}