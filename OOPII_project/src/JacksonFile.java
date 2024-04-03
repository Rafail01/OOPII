import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonFile {

	public void writeJSON(ArrayList<Traveller> inTravellerList)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enableDefaultTyping();
		AllTravellers travellersData = new AllTravellers();
		travellersData.setCollectionAllTravellers(inTravellerList);
		mapper.writeValue(new File("travellerList.json"), travellersData);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Traveller> readJSON() throws JsonParseException, JsonMappingException, IOException {
		File f = new File("travellerList.json");
		if (f.exists()) {
			System.out.println("File:\"travellerList.json\" Exists");
			ObjectMapper mapper = new ObjectMapper();
			mapper.enableDefaultTyping();
			AllTravellers travellersData = mapper.readValue(new File("travellerList.json"), AllTravellers.class);
			return travellersData.getCollectionAllTravellers();
		} else {
			System.out.println("File:\"travellerList.json\" Does not Exists");
			ArrayList<Traveller> emptyTravellersArrayList = new ArrayList<Traveller>();
			return emptyTravellersArrayList;
		}
	}
	
}
