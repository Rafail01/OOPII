import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import exception.WikipediaNoArcticleException;
import exception.WikipediaNoCityException;

public class GUICityPicker {

	private String appid = "116427f6e7a5e1872aa0d6ac10c3e2d8";

	public void cityPickerWindow(ArrayList<Traveller> travellerList, HashMap<String, City> cityHashMap) throws SQLException, JsonParseException, JsonMappingException, IOException, WikipediaNoCityException  {
		JFrame mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setTitle("Find your Next Destination");
		mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
		mainFrame.setVisible(true);

		// Name - Age
		JPanel nameAgePanel = new JPanel(new GridLayout(2, 3));
		nameAgePanel.add(new JLabel("Name: "));
		JTextField nameTextField = new JTextField("", 15);
		nameAgePanel.add(nameTextField);
		nameAgePanel.add(new JLabel("Age: "));
		JTextField ageTextField = new JTextField("", 15);
		nameAgePanel.add(ageTextField);
		mainFrame.add(nameAgePanel);

		// Age Group
		JPanel ageGroupPanel = new JPanel(new GridLayout(1, 4));
		ageGroupPanel.add(new JLabel("Age Group: "));
		ButtonGroup options = new ButtonGroup();
		JRadioButton rb1 = new JRadioButton("Young(<30)");
		JRadioButton rb2 = new JRadioButton("Middle(30-60)");
		JRadioButton rb3 = new JRadioButton("Elder(>60)");
		options.add(rb1);
		options.add(rb2);
		options.add(rb3);
		ageGroupPanel.add(rb1);
		ageGroupPanel.add(rb2);
		ageGroupPanel.add(rb3);
		mainFrame.add(ageGroupPanel);

		// spacing
		mainFrame.add(new GUICityPicker().spacing());

		// Latitude - Longitude
		JPanel latLonPanel = new JPanel(new GridLayout(1, 4));
		latLonPanel.add(new JLabel("Latitude: "));
		JTextField latitudeTextField = new JTextField("", 5);
		latLonPanel.add(latitudeTextField);
		latLonPanel.add(new JLabel("longitude: "));
		JTextField longitudeTextField = new JTextField("", 5);
		latLonPanel.add(longitudeTextField);
		mainFrame.add(latLonPanel);

		JPanel p = new JPanel();
		JLabel l = new JLabel(
				"<HTML>You don't know your location? find it here <U>https://www.latlong.net/</U></HTML>\"");
		p.add(l);
		mainFrame.add(p);

		// spacing
		mainFrame.add(new GUICityPicker().spacing());

		// Simple Message
		JPanel simpleMessagePanel = new JPanel();
		JLabel simpleMessageLabel = new JLabel("Help us find out your preferences!");
		simpleMessageLabel.setFont(new Font("HelveticaNeue", Font.BOLD, 14));
//		simpleMessageLabel.setForeground(Color.white);
//		simpleMessageLabel.setHorizontalTextPosition(JLabel.CENTER);
//		simpleMessageLabel.setVerticalTextPosition(JLabel.CENTER);
		simpleMessagePanel.add(simpleMessageLabel);
		mainFrame.add(simpleMessagePanel);
		
		// Country Preference Message
		JPanel countryPreferMessagePanel = new JPanel();
		JLabel countryPreferMessageLabel = new JLabel(" Give us 3 Cities you like(and their 2-letter Country code):");
		countryPreferMessageLabel.setFont(new Font("Lore", Font.BOLD, 12));
		countryPreferMessagePanel.add(countryPreferMessageLabel);
		mainFrame.add(countryPreferMessagePanel);

		// Preferenced Cities
		JPanel cityPanel = new JPanel(new GridLayout(1, 4));
		cityPanel.add(new JLabel("1. City: "));
		JTextField cityNameTextField = new JTextField("", 15);
		cityPanel.add(cityNameTextField);
		cityPanel.add(new JLabel("Country code: "));
		JTextField countryCodeTextField = new JTextField("", 5);
		cityPanel.add(countryCodeTextField);
		mainFrame.add(cityPanel);

		JPanel city2Panel = new JPanel(new GridLayout(1, 4));
		city2Panel.add(new JLabel("2. City: "));
		JTextField city2NameTextField = new JTextField("", 15);
		city2Panel.add(city2NameTextField);
		city2Panel.add(new JLabel("Country code: "));
		JTextField country2CodeTextField = new JTextField("", 5);
		city2Panel.add(country2CodeTextField);
		mainFrame.add(city2Panel);

		JPanel city3Panel = new JPanel(new GridLayout(1, 4));
		city3Panel.add(new JLabel("3. City: "));
		JTextField city3NameTextField = new JTextField("", 15);
		city3Panel.add(city3NameTextField);
		city3Panel.add(new JLabel("Country code: "));
		JTextField country3CodeTextField = new JTextField("", 5);
		city3Panel.add(country3CodeTextField);
		mainFrame.add(city3Panel);

		// Terms Vectors Sliders
		JPanel slidersMessagePanel = new JPanel();
		JLabel slidersMessageLabel = new JLabel(" How much you'd like the city to have:");
		slidersMessageLabel.setFont(new Font("Lore", Font.BOLD, 12));
		slidersMessagePanel.add(slidersMessageLabel);
		mainFrame.add(slidersMessagePanel);

		JPanel allSlidersPanel = new JPanel();
		allSlidersPanel.setLayout(new BoxLayout(allSlidersPanel, BoxLayout.Y_AXIS));

		JPanel sliderPanel1 = new JPanel();
		sliderPanel1.add(new JLabel("Museum"));
		JSlider slider1 = new JSlider(0, 10, 0);
		slider1.setMajorTickSpacing(1);
		slider1.setMinorTickSpacing(0);
		slider1.setPaintTrack(true);
		slider1.setPaintTicks(true);
		slider1.setPaintLabels(true);
		sliderPanel1.add(slider1);
		allSlidersPanel.add(sliderPanel1);

		JPanel sliderPanel2 = new JPanel();
		sliderPanel2.add(new JLabel("Theatre"));
		JSlider slider2 = new JSlider(0, 10, 0);
		slider2.setMajorTickSpacing(1);
		slider2.setMinorTickSpacing(0);
		slider2.setPaintTrack(true);
		slider2.setPaintTicks(true);
		slider2.setPaintLabels(true);
		sliderPanel2.add(slider2);
		allSlidersPanel.add(sliderPanel2);

		JPanel sliderPanel3 = new JPanel();
		sliderPanel3.add(new JLabel("Sea"));
		JSlider slider3 = new JSlider(0, 10, 0);
		slider3.setMajorTickSpacing(1);
		slider3.setMinorTickSpacing(0);
		slider3.setPaintTrack(true);
		slider3.setPaintTicks(true);
		slider3.setPaintLabels(true);
		sliderPanel3.add(slider3);
		allSlidersPanel.add(sliderPanel3);

		JPanel sliderPanel4 = new JPanel();
		sliderPanel4.add(new JLabel("Cafe"));
		JSlider slider4 = new JSlider(0, 10, 0);
		slider4.setMajorTickSpacing(1);
		slider4.setMinorTickSpacing(0);
		slider4.setPaintTrack(true);
		slider4.setPaintTicks(true);
		slider4.setPaintLabels(true);
		sliderPanel4.add(slider4);
		allSlidersPanel.add(sliderPanel4);

		JPanel sliderPanel5 = new JPanel();
		sliderPanel5.add(new JLabel("Mountain"));
		JSlider slider5 = new JSlider(0, 10, 0);
		slider5.setMajorTickSpacing(1);
		slider5.setMinorTickSpacing(0);
		slider5.setPaintTrack(true);
		slider5.setPaintTicks(true);
		slider5.setPaintLabels(true);
		sliderPanel5.add(slider5);
		allSlidersPanel.add(sliderPanel5);

		JPanel sliderPanel6 = new JPanel();
		sliderPanel6.add(new JLabel("Bar"));
		JSlider slider6 = new JSlider(0, 10, 0);
		slider6.setMajorTickSpacing(1);
		slider6.setMinorTickSpacing(0);
		slider6.setPaintTrack(true);
		slider6.setPaintTicks(true);
		slider6.setPaintLabels(true);
		sliderPanel6.add(slider6);
		allSlidersPanel.add(sliderPanel6);

		JPanel sliderPanel7 = new JPanel();
		sliderPanel7.add(new JLabel("Cinema"));
		JSlider slider7 = new JSlider(0, 10, 0);
		slider7.setMajorTickSpacing(1);
		slider7.setMinorTickSpacing(0);
		slider7.setPaintTrack(true);
		slider7.setPaintTicks(true);
		slider7.setPaintLabels(true);
		sliderPanel7.add(slider7);
		allSlidersPanel.add(sliderPanel7);

		JPanel sliderPanel8 = new JPanel();
		sliderPanel8.add(new JLabel("Stadium"));
		JSlider slider8 = new JSlider(0, 10, 0);
		slider8.setMajorTickSpacing(1);
		slider8.setMinorTickSpacing(0);
		slider8.setPaintTrack(true);
		slider8.setPaintTicks(true);
		slider8.setPaintLabels(true);
		sliderPanel8.add(slider8);
		allSlidersPanel.add(sliderPanel8);

		JPanel sliderPanel9 = new JPanel();
		sliderPanel9.add(new JLabel("Park"));
		JSlider slider9 = new JSlider(0, 10, 0);
		slider9.setMajorTickSpacing(1);
		slider9.setMinorTickSpacing(0);
		slider9.setPaintTrack(true);
		slider9.setPaintTicks(true);
		slider9.setPaintLabels(true);
		sliderPanel9.add(slider9);
		allSlidersPanel.add(sliderPanel9);

		JPanel sliderPanel10 = new JPanel();
		sliderPanel10.add(new JLabel("Supermarket"));
		JSlider slider10 = new JSlider(0, 10, 0);
		slider10.setMajorTickSpacing(1);
		slider10.setMinorTickSpacing(0);
		slider10.setPaintTrack(true);
		slider10.setPaintTicks(true);
		slider10.setPaintLabels(true);
		sliderPanel10.add(slider10);
		allSlidersPanel.add(sliderPanel10);

		// Scroll Pane on Sliders Panel
		mainFrame.add(allSlidersPanel);
		JScrollPane scrollableTextArea = new JScrollPane(allSlidersPanel);
		scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mainFrame.getContentPane().add(scrollableTextArea);

		// Recommended City text field
		JPanel statusPanel = new JPanel();
		JLabel statusLabel = new JLabel();
		statusLabel.setFont(new Font("Lore", Font.BOLD, 15));
		statusLabel.setForeground(Color.red);
		//textField.setPreferredSize(new Dimension(1000, 80));
		
		// Recommend Button with Action Listener	
		JPanel recommendButtonPanel = new JPanel();
		JButton recommendButton = new JButton();
		recommendButton.setPreferredSize(new Dimension(120, 40));
		recommendButton.setText("recommend");
		recommendButtonPanel.add(recommendButton);
		recommendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				Optional<RecommendedCity> recommendedCity = null;
				
				rb1.setActionCommand("Young");
				rb2.setActionCommand("Middle");
				rb3.setActionCommand("Elder");
				String travellerAge = options.getSelection().getActionCommand();
				double[] tmpGeodesicVector = new double[2];
				int[] tmpTermsVector = new int[10];
				if (travellerAge == "Young") {
					YoungTraveller traveller = new YoungTraveller();
					Date date = new Date();
					traveller.setTimestamp(date.getTime());
					traveller.setName(nameTextField.getText());
					traveller.setAge(Integer.parseInt(ageTextField.getText()));
					tmpGeodesicVector[0] = Double.parseDouble(latitudeTextField.getText());
					tmpGeodesicVector[1] = Double.parseDouble(longitudeTextField.getText());
					traveller.setTravellerGeodesicVector(tmpGeodesicVector);
					tmpTermsVector[0] = slider1.getValue();
					tmpTermsVector[1] = slider2.getValue();
					tmpTermsVector[2] = slider3.getValue();
					tmpTermsVector[3] = slider4.getValue();
					tmpTermsVector[4] = slider5.getValue();
					tmpTermsVector[5] = slider6.getValue();
					tmpTermsVector[6] = slider7.getValue();
					tmpTermsVector[7] = slider8.getValue();
					tmpTermsVector[8] = slider9.getValue();
					tmpTermsVector[9] = slider10.getValue();
					traveller.setTravellerTermsVector(tmpTermsVector);

					travellerList.add(traveller);
					if(travellerList.size() > 5) {
						 recommendedCity = collaborativeFiltering(travellerList, traveller);
					}
				} else if (travellerAge == "Middle") {
					MiddleTraveller traveller = new MiddleTraveller();
					Date date = new Date();
					traveller.setTimestamp(date.getTime());
					traveller.setName(nameTextField.getText());
					traveller.setAge(Integer.parseInt(ageTextField.getText()));
					tmpGeodesicVector[0] = Double.parseDouble(latitudeTextField.getText());
					tmpGeodesicVector[1] = Double.parseDouble(longitudeTextField.getText());
					traveller.setTravellerGeodesicVector(tmpGeodesicVector);
					tmpTermsVector[0] = slider1.getValue();
					tmpTermsVector[1] = slider2.getValue();
					tmpTermsVector[2] = slider3.getValue();
					tmpTermsVector[3] = slider4.getValue();
					tmpTermsVector[4] = slider5.getValue();
					tmpTermsVector[5] = slider6.getValue();
					tmpTermsVector[6] = slider7.getValue();
					tmpTermsVector[7] = slider8.getValue();
					tmpTermsVector[8] = slider9.getValue();
					tmpTermsVector[9] = slider10.getValue();
					traveller.setTravellerTermsVector(tmpTermsVector);

					travellerList.add(traveller);
					
					if(travellerList.size() > 5) {
						recommendedCity = collaborativeFiltering(travellerList, traveller);
					}
					
				} else {
					ElderTraveller traveller = new ElderTraveller();
					Date date = new Date();
					traveller.setTimestamp(date.getTime());
					traveller.setName(nameTextField.getText());
					traveller.setAge(Integer.parseInt(ageTextField.getText()));
					tmpGeodesicVector[0] = Double.parseDouble(latitudeTextField.getText());
					tmpGeodesicVector[1] = Double.parseDouble(longitudeTextField.getText());
					traveller.setTravellerGeodesicVector(tmpGeodesicVector);
					tmpTermsVector[0] = slider1.getValue();
					tmpTermsVector[1] = slider2.getValue();
					tmpTermsVector[2] = slider3.getValue();
					tmpTermsVector[3] = slider4.getValue();
					tmpTermsVector[4] = slider5.getValue();
					tmpTermsVector[5] = slider6.getValue();
					tmpTermsVector[6] = slider7.getValue();
					tmpTermsVector[7] = slider8.getValue();
					tmpTermsVector[8] = slider9.getValue();
					tmpTermsVector[9] = slider10.getValue();
					traveller.setTravellerTermsVector(tmpTermsVector);
					
					travellerList.add(traveller);
					
					if(travellerList.size() > 5) {
						 recommendedCity = collaborativeFiltering(travellerList, traveller);
					}
					
				}
				
				JacksonFile json = new JacksonFile();

				//Create Travellers examples
				try {

					json.writeJSON(travellerList);

				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				// oracleDB connection
				OracleDBService dbObject = new OracleDBService();
				dbObject.makeJDBCConnection();
				
				try {
					City city1 = new City();
					if (city1.searchCity(cityNameTextField.getText(), cityHashMap) == false) {
						city1.setCityValues(cityNameTextField.getText(), countryCodeTextField.getText(), appid);
						cityHashMap.put(city1.getName(), city1);
						dbObject.addDataToDB(city1.getName(), city1.getCityTermsVector(), city1.getCityGeodesicVector());
					}

				} catch (WikipediaNoArcticleException | IOException e) {
					System.out.println(e.getMessage());
				}
				
				try {
					City city2 = new City();
					if (city2.searchCity(cityNameTextField.getText(), cityHashMap) == false) {
						city2.setCityValues(cityNameTextField.getText(), countryCodeTextField.getText(), appid);
						cityHashMap.put(city2.getName(), city2);
						dbObject.addDataToDB(city2.getName(), city2.getCityTermsVector(), city2.getCityGeodesicVector());
					}

				} catch (WikipediaNoArcticleException | IOException e) {
					System.out.println(e.getMessage());
				}
				
				try {
					City city3 = new City();
					if (city3.searchCity(cityNameTextField.getText(), cityHashMap) == false) {
						city3.setCityValues(cityNameTextField.getText(), countryCodeTextField.getText(), appid);
						cityHashMap.put(city3.getName(), city3);
						dbObject.addDataToDB(city3.getName(), city3.getCityTermsVector(), city3.getCityGeodesicVector());
					}

				} catch (WikipediaNoArcticleException | IOException e) {
					System.out.println(e.getMessage());
				}
								
				//Calculate similarity and set Traveller's visit
				ArrayList<City> citiesToCompare = new ArrayList<City>(cityHashMap.values());
				
				City maxSimilarityCity = new City();
				for(Traveller traveller: travellerList) {
					maxSimilarityCity = traveller.compareCities(citiesToCompare);
					traveller.setVisit(maxSimilarityCity.getName());
				}
				
				statusLabel.setText("Here is your next destination: " + maxSimilarityCity.getName().toUpperCase() +"      With collaborative:" + recommendedCity.get().getCity().toUpperCase());
				//statusLabel2.setText("Here is your next destination: " + recommendedCity.get().getCity());
			}
		});
		
		mainFrame.add(recommendButtonPanel);
		statusPanel.add(statusLabel);
		mainFrame.add(statusPanel);
		
//		mainFrame.setResizable(false);
		mainFrame.pack();

	}

	public JPanel spacing() {
		JPanel p = new JPanel();
		JLabel l = new JLabel("<HTML><br/><br/></HTML>\"");
		p.add(l);
		return (p);
	}
	
	private static int innerDot(int[] currentTraveller, int[] candidateTraveller) {
		int sum=0;
		for (int i=0; i<currentTraveller.length;i++)
			sum+=currentTraveller[i]*candidateTraveller[i];
		return sum;
			
	}

	private Optional<RecommendedCity> collaborativeFiltering(ArrayList<Traveller> travellerList,Traveller trav ) {	
		LinkedHashMap<String, Traveller> travellerLinkedHashMap = new LinkedHashMap<>();

		Collections.sort(travellerList);

		for (Traveller traveller : travellerList) {
			travellerLinkedHashMap.put(traveller.getVisit(), traveller);
		}

		List<Traveller> travellerList2 = new ArrayList<>(travellerLinkedHashMap.values());
		
		Map<String,Integer> cityToRank=travellerList2.stream().collect(Collectors.toMap(i->i.getVisit(),i->innerDot(i.getTravellerTermsVector(),trav.getTravellerTermsVector())));
		//cityToRank.forEach((k,v)->System.out.println("city:"+k+" rank: "+v));
			
		//We print the Traveller who has the highest Rank (similarity) (dot product).
		Optional<RecommendedCity> recommendedCity=travellerList2.stream().map(i-> new RecommendedCity(i.getVisit(),innerDot(i.getTravellerTermsVector(),trav.getTravellerTermsVector()))).max(Comparator.comparingInt(RecommendedCity::getRank));
			
		return recommendedCity;
		
	}
	
}
