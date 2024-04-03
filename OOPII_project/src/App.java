import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.Random;
import exception.WikipediaNoArcticleException;
import exception.WikipediaNoCityException;

public class App extends Thread{
	
	public static ArrayList<Traveller> travellerList = new ArrayList<Traveller>();
	
	public static void main(String[] args) throws SQLException, JsonParseException, JsonMappingException, IOException, WikipediaNoCityException, InterruptedException {
		
		HashMap<String, City> cityHashMap = new HashMap<String, City>();
		
		(new App()).start();
		
		OracleDBService dbObject = new OracleDBService();
		dbObject.makeJDBCConnection();
		cityHashMap = dbObject.ReadData();
		
		
		GUIStartPage gui = new GUIStartPage();
		gui.startGUI(travellerList, cityHashMap);
	}

	public void run(){
		JacksonFile json = new JacksonFile();
		try {
	
			travellerList = json.readJSON();
	
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	public static void main2(String[] args) throws IOException, WikipediaNoArcticleException, WikipediaNoCityException, InterruptedException, SQLException {
//		ArrayList<Traveller> travellerList = new ArrayList<Traveller>();
//		HashMap<String, City> cityHashMap = new HashMap<String, City>();
//		App runApp = new App();
//
//		String appid = "116427f6e7a5e1872aa0d6ac10c3e2d8";
//
//		JacksonFile json = new JacksonFile();
//
//		//Create Travellers examples
//		try {
//
//			travellerList = json.readJSON();
//
//			runApp.createTravellers(travellerList);
//
//			json.writeJSON(travellerList);
//
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		//Print Travellers
//		System.out.println("\n\nTRAVELLERS");
//		for(Traveller traveller: travellerList) {
//			System.out.print(traveller.toString());
//			for(int i = 0; i < 10; i++) {
//				System.out.print(", term[" + i + "]: " + traveller.getTravellerTermsVector()[i]);
//			}
//			System.out.print("\n");
//		}
//		System.out.print("\n\n");
//		
//		//Create Cities examples
//		cityHashMap = runApp.createCities(cityHashMap);
//		
//		//Print Cities
//		System.out.println("\n\nCITIES");
//		for (City ct: cityHashMap.values()) {
//			System.out.print("Name:" + ct.getName() + ", Similarity:" + ct.getSimilarity());
//			for(int i = 0; i < 10; i++) {
//				System.out.print(", term[" + i + "]: " + ct.getCityTermsVector()[i]);
//			}
//			System.out.print("\n");
//		}
//		
//		//Calculate similarity and set Traveller's visit
//		ArrayList<City> citiesToCompare = new ArrayList<City>(cityHashMap.values());
//		
//		City maxSimilarityCity = new City();
//		for(Traveller traveller: travellerList) {
//			maxSimilarityCity = traveller.compareCities(citiesToCompare);
//			traveller.setVisit(maxSimilarityCity.getName());
//		}
//		
//		//Print every Traveller once(first time entered)
//		YoungTraveller trav = new YoungTraveller();
//		trav.sortTravellers(travellerList);
//				
//		System.out.println("\n\nCITIES WITH SIMILARITY");
//		for (City ct: cityHashMap.values()) {
//			System.out.print("Name:" + ct.getName() + ", Similarity:" + ct.getSimilarity());
//			for(int i = 0; i < 10; i++) {
//				System.out.print(", term[" + i + "]: " + ct.getCityTermsVector()[i]);
//			}
//			System.out.print("\n");
//		}
//		
//		//Print Travellers after visit
//		System.out.println("\n\nTRAVELLERS WITH VISIT");
//		for(Traveller traveller: travellerList) {
//			System.out.print(traveller.toString());
//			System.out.print(", visit: " + traveller.getVisit());
//			System.out.print("\n");
//		}
//		
//		//Free Ticket
//		Ticket ticket = new Ticket();
//		ticket.freeTicket(cityHashMap.get("Moscow"), travellerList);
//		
//	}
//	
//	public void createTravellers(ArrayList<Traveller> travellerList) throws InterruptedException, JsonGenerationException, JsonMappingException, IOException {
//		String[] travellersNamesArray = new String[] { "George", "Nikos", "Petros", "Christos", "Aris", "Panos",
//				"Maria", "Dimitra", "Sofia", "Andreas", "Pavlos", "Katerina", "Tasos", "Eleni", "Anna" }; // 15 names
//		double maxLatitude = 90.00;
//		double minLatitude = -90.00;
//		double maxLongitude = 180.00;
//		double minLongitude = -180.00;
//		int namesArrayPointer = 0;
//		for (int i = 20; i < 25; i++) {
//			int[] tmpTermsVector = new int[10];
//			double[] tmpGeodesicVector = new double[2];
//			YoungTraveller traveller = new YoungTraveller();
//			traveller.setAge(i - 5);
//			traveller.setName(travellersNamesArray[namesArrayPointer]);
//			Date date = new Date();
//			traveller.setTimestamp(date.getTime());
//			for (int k = 0; k < 10; k++) {
//				Random rand = new Random();
//				tmpTermsVector[k] = rand.nextInt(11);
//			}
//			traveller.setTravellerTermsVector(tmpTermsVector);
//			tmpGeodesicVector[0] = Math.random() * (maxLatitude - minLatitude) + minLatitude;
//			tmpGeodesicVector[1] = Math.random() * (maxLongitude - minLongitude) + minLongitude;
//			traveller.setTravellerGeodesicVector(tmpGeodesicVector);
//			travellerList.add(traveller);
//			Thread.sleep(i);
//
//			int[] tmpTermsVector2 = new int[10];
//			double[] tmpGeodesicVector2 = new double[2];
//			MiddleTraveller traveller2 = new MiddleTraveller();
//			traveller2.setAge(i + 20);
//			traveller2.setName(travellersNamesArray[namesArrayPointer + 1]);
//			Date date2 = new Date();
//			traveller2.setTimestamp(date2.getTime());
//			for (int k = 0; k < 10; k++) {
//				Random rand = new Random();
//				tmpTermsVector2[k] = rand.nextInt(11);
//			}
//			traveller2.setTravellerTermsVector(tmpTermsVector2);
//			tmpGeodesicVector2[0] = Math.random() * (maxLatitude - minLatitude) + minLatitude;
//			tmpGeodesicVector2[1] = Math.random() * (maxLongitude - minLongitude) + minLongitude;
//			traveller2.setTravellerGeodesicVector(tmpGeodesicVector2);
//			travellerList.add(traveller2);
//			Thread.sleep(i);
//
//			int[] tmpTermsVector3 = new int[10];
//			double[] tmpGeodesicVector3 = new double[2];
//			ElderTraveller traveller3 = new ElderTraveller();
//			traveller3.setAge(i + 40);
//			traveller3.setName(travellersNamesArray[namesArrayPointer + 2]);
//			Date date3 = new Date();
//			traveller3.setTimestamp(date3.getTime());
//			for (int k = 0; k < 10; k++) {
//				Random rand = new Random();
//				tmpTermsVector3[k] = rand.nextInt(11);
//			}
//			traveller3.setTravellerTermsVector(tmpTermsVector3);
//			tmpGeodesicVector3[0] = Math.random() * (maxLatitude - minLatitude) + minLatitude;
//			tmpGeodesicVector3[1] = Math.random() * (maxLongitude - minLongitude) + minLongitude;
//			traveller3.setTravellerGeodesicVector(tmpGeodesicVector3);
//			travellerList.add(traveller3);
//			Thread.sleep(i);
//
//			namesArrayPointer += 3;
//		}
//
//		//Same Traveller enters 2nd time
//		int[] tmpTermsVector4 = new int[10];
//		double[] tmpGeodesicVector4 = new double[2];
//		MiddleTraveller traveller4 = new MiddleTraveller();
//		traveller4.setName("Nikos");
//		traveller4.setAge(40);
//		Date date4 = new Date();
//		traveller4.setTimestamp(date4.getTime());
//		for (int k = 0; k < 10; k++) {
//			Random rand = new Random();
//			tmpTermsVector4[k] = rand.nextInt(11);
//		}
//		traveller4.setTravellerTermsVector(tmpTermsVector4);
//		tmpGeodesicVector4[0] = Math.random() * (maxLatitude - minLatitude) + minLatitude;
//		tmpGeodesicVector4[1] = Math.random() * (maxLongitude - minLongitude) + minLongitude;
//		traveller4.setTravellerGeodesicVector(tmpGeodesicVector4);
//		travellerList.add(traveller4);
//
//	}
//	
//	public HashMap<String, City> createCities(HashMap<String, City> cityHashMap) throws SQLException, JsonParseException, JsonMappingException, IOException, WikipediaNoCityException {
//		// oracleDB connection
//		OracleDBService dbObject = new OracleDBService();
//		dbObject.makeJDBCConnection();
//		
//		cityHashMap = dbObject.ReadData();
//		
//		String[] citiesNamesArray = new String[] {"Paris", "London", "Athens", "Moscow", "Corfu", "Berlin", "Naples"};
//		String[] countryNamesArray = new String[] {"fr", "uk", "gr", "ru", "gr", "de", "it"};
//		String appid = "116427f6e7a5e1872aa0d6ac10c3e2d8";
//		for(int i = 0; i < citiesNamesArray.length; i++) {
//			try {
//				City city = new City();
//				if (city.searchCity(citiesNamesArray[i], cityHashMap) == false) {
//					city.setCityValues(citiesNamesArray[i], countryNamesArray[i], appid);
//					cityHashMap.put(city.getName(), city);
//					dbObject.addDataToDB(city.getName(), city.getCityTermsVector(), city.getCityGeodesicVector());
//				}
//
//			} catch (WikipediaNoArcticleException e) {
//				System.out.println(e.getMessage());
//			}
//		}
//		
//		return cityHashMap;
//	}

}
