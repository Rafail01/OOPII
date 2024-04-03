import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.apache.http.client.ClientProtocolException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import exception.WikipediaNoArcticleException;
import exception.WikipediaNoCityException;
import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

public class City implements Comparable<City> {

	private String name;
	public String[] cityCriterions = {"museum", "theatre", "sea", "cafe", "mountain", "bar", "cinema", "stadium", "park", "supermarket"};
	private int[] cityTermsVector = new int[10];
	private double[] cityGeodesicVector = new double[2];
	private double similarity;
	
    //constructors
	public City(String name, int[] cityTermsVector, double[] cityGeodesicVector) {
		this.name  = name;
		this.cityTermsVector = cityTermsVector;
		this.cityGeodesicVector = cityGeodesicVector;
	}
	
	public City() {}

	//setters
	public void setCityTermsVector(int[] cityTermsVector) {
		this.cityTermsVector = cityTermsVector;
	}

	public void setCityGeodesicVector(double[] cityGeodesicVector) {
		this.cityGeodesicVector = cityGeodesicVector;
	}
	
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//getters
	public int[] getCityTermsVector() {
		return cityTermsVector;
	}
	
	public double[] getCityGeodesicVector() {
		return cityGeodesicVector;
	}
	
    public double getSimilarity() {
		return similarity;
	}
    
    public String getName() {
		return name;
	}
    
    
	public void setCityValues(String city, String country, String appid) throws JsonParseException, JsonMappingException, IOException, WikipediaNoArcticleException {
	    
		ObjectMapper mapper = new ObjectMapper();
		
		//fills City's Terms Vector with data from Wikipedia
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles="+city+"&format=json&formatversion=2").build());       
		String json= service.accept(MediaType.APPLICATION_JSON).get(String.class); 
		if (json.contains("pageid")) {
			MediaWiki mediaWiki_obj =  mapper.readValue(json, MediaWiki.class);
			String cityArticle= mediaWiki_obj.getQuery().getPages().get(0).getExtract();
			int[] term = new int[10];
			for(int i = 0; i < 10 ; i++) {
				term[i] = countCriterionfCity(cityArticle, cityCriterions[i]);
			}
			setCityTermsVector(term);		
		} else throw new WikipediaNoArcticleException(city);
		
		//fills City's Geodesic Vector with data from OpenWeather
	    double[] tmpCityGeodesicVector = new double[2];
		 
		OpenWeatherMap weather_obj = mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q="+city+","+country+"&APPID="+appid+""), OpenWeatherMap.class);
		
	    tmpCityGeodesicVector[0] = weather_obj.getCoord().getLat();
	    tmpCityGeodesicVector[1] = weather_obj.getCoord().getLon();
		
		setCityGeodesicVector(tmpCityGeodesicVector);
		setName(city);
		
	}
	
	public static int countCriterionfCity(String cityArticle, String criterion) {
		cityArticle=cityArticle.toLowerCase();
		int index = cityArticle.indexOf(criterion);
		int count = 0;
		while (index != -1) {
		    count++;
		    cityArticle = cityArticle.substring(index + 1);
		    index = cityArticle.indexOf(criterion);
		}
		return count;
	}
	
	public boolean searchCity(String cityName , HashMap<String,City> cityHashMap) {
		return cityHashMap.containsKey(cityName);
	}
	
	@Override
	public int compareTo(City arg0) {
		if(this.similarity > arg0.similarity) {
			return -1;
		} else if(this.similarity < arg0.similarity) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "City [name: " + getName() + ",  similarity: " + getSimilarity() + "]";
	}
	
}