import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import exception.WikipediaNoArcticleException;
import exception.WikipediaNoCityException;

public class GUIStartPage {

	public void startGUI(ArrayList<Traveller> travellerList, HashMap<String, City> cityHashMap)
			throws SQLException, JsonParseException, JsonMappingException, IOException, WikipediaNoCityException {
		// start JFrame
		JFrame startFrame = new JFrame();
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.setTitle("Travel App");
		startFrame.setLayout(new BorderLayout());
		startFrame.getContentPane().setBackground(new Color(34, 139, 220));
		startFrame.setVisible(true);

		// JMenuBar
		JMenuBar menuBar = new JMenuBar();

		JMenu freeTicket = new JMenu("Free Ticket");
		menuBar.add(freeTicket);

//		JFrame ticketFrame = new JFrame();
//		ticketFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		ticketFrame.setTitle("Free ticket winner");

		freeTicket.addMenuListener(new MenuListener() {
//		  public void actionPerformed(ActionEvent ae) {
//			  
//		  }

			@Override
			public void menuSelected(MenuEvent e) {
				Ticket ticket = new Ticket();
				Traveller winner = ticket.freeTicket(cityHashMap.get("Moscow"), travellerList);

				JFrame ticketFrame = new JFrame();
				ticketFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				ticketFrame.setTitle("Free Ticket Winner");
				ticketFrame.setLayout(new BorderLayout());
				ticketFrame.setBackground(new Color(3, 39, 100));
//			  ticketFrame.setSize(500, 200);

				ImageIcon image = new ImageIcon("winner.png");
				JLabel imageLabel = new JLabel();
				imageLabel.setIcon(image);
				ticketFrame.add(imageLabel, BorderLayout.NORTH);

				ticketFrame.setVisible(true);

				JPanel winnerPanel = new JPanel(new BorderLayout());
//				winnerPanel.setBackground(new Color(255, 107, 12));
				JLabel winnerMessageLabel = new JLabel();
				winnerMessageLabel.setText("Free ticket winner to traveller in Moscow is: ");
				winnerMessageLabel.setHorizontalAlignment(JLabel.CENTER);
				winnerMessageLabel.setFont(new Font("Gills San", Font.BOLD, 16));
				winnerMessageLabel.setForeground(new Color(255, 107, 17));
				JLabel winnerLabel = new JLabel();
				winnerLabel.setText("Name : " + winner.getName() + ", age: " + winner.getAge());
				winnerLabel.setHorizontalAlignment(JLabel.CENTER);
				winnerLabel.setFont(new Font("Gills San", Font.BOLD, 17));
				winnerLabel.setForeground(new Color(255, 107, 12));
				winnerPanel.add(winnerMessageLabel, BorderLayout.NORTH);
				winnerPanel.add(winnerLabel, BorderLayout.SOUTH);
				ticketFrame.add(winnerPanel, BorderLayout.SOUTH);
				
				ticketFrame.setResizable(false);
				ticketFrame.pack();
			}

			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub

			}
		});

		JMenu printTrav = new JMenu("Show Travellers");
		menuBar.add(printTrav);
		
		printTrav.addMenuListener(new MenuListener() {


				@Override
				public void menuSelected(MenuEvent e2) {
					YoungTraveller trav = new YoungTraveller();
					LinkedHashMap<String, Traveller> travellerLinkedHashMap = new LinkedHashMap<>();
					travellerLinkedHashMap = trav.sortTravellers(travellerList);

					JFrame printTravFrame = new JFrame();
					printTravFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					printTravFrame.setTitle("Clients");
					printTravFrame.setLayout(new BorderLayout());
//					printTravFrame.setBackground(new Color(3, 39, 100));
					printTravFrame.setVisible(true);
					JPanel printTravPanel = new JPanel();
					printTravPanel.setLayout(new BoxLayout(printTravPanel, BoxLayout.Y_AXIS));
					JLabel TravMessageLabel = new JLabel();					
					TravMessageLabel.setText("TRAVELLERS WITHOUT DUPLICATES ");
					printTravPanel.add(TravMessageLabel);
					for (String key : travellerLinkedHashMap.keySet()) {
						JLabel TravMessageLabel2 = new JLabel();					
						TravMessageLabel2.setText("Traveller [name: " + key + ", age: " + travellerLinkedHashMap.get(key).getAge() + ", timestamp: " + travellerLinkedHashMap.get(key).getTimestamp() + "]");
						printTravPanel.add(TravMessageLabel2);
					}										
					TravMessageLabel.setHorizontalAlignment(JLabel.CENTER);
					TravMessageLabel.setFont(new Font("Gills San", Font.BOLD, 16));
					TravMessageLabel.setForeground(new Color(255, 107, 17));
					
					printTravFrame.add(printTravPanel);
//					JLabel winnerLabel = new JLabel();
//					winnerLabel.setText("Name : " + winner.getName() + ", age: " + winner.getAge());
//					winnerLabel.setHorizontalAlignment(JLabel.CENTER);
//					winnerLabel.setFont(new Font("Gills San", Font.BOLD, 17));
//					winnerLabel.setForeground(new Color(255, 107, 12));
//					winnerPanel.add(winnerMessageLabel, BorderLayout.NORTH);
//					winnerPanel.add(winnerLabel, BorderLayout.SOUTH);
//					ticketFrame.add(winnerPanel, BorderLayout.SOUTH);
					
//					printTravFrame.setResizable(false);
					printTravFrame.pack();
				}

				@Override
				public void menuDeselected(MenuEvent e2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void menuCanceled(MenuEvent e2) {
					// TODO Auto-generated method stub

				}
			});
	

		startFrame.setJMenuBar(menuBar);

		// start Frame Image
		ImageIcon image = new ImageIcon("plane.png");
		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(image);
		startFrame.add(imageLabel, BorderLayout.NORTH);

		// start Frame Text
		JPanel startMessagePanel = new JPanel();
		startMessagePanel.setBackground(new Color(34, 139, 220));
		JLabel startMessageLabel = new JLabel();
		startMessageLabel.setText("<HTML><br/><br/>Find your next travel destination!</HTML>");
		startMessageLabel.setFont(new Font("HelveticaNeue", Font.BOLD, 17));
		startMessageLabel.setForeground(Color.white);
		startMessageLabel.setHorizontalTextPosition(JLabel.CENTER);
		startMessageLabel.setVerticalTextPosition(JLabel.CENTER);
		startMessagePanel.add(startMessageLabel);
		startFrame.add(startMessagePanel, BorderLayout.CENTER);

		// start Frame Button
		JPanel getStartedButtonPanel = new JPanel();
		getStartedButtonPanel.setBackground(new Color(34, 139, 220));
		JButton getStartedButton = new JButton();
		getStartedButton.setText("Find out");
		getStartedButton.setPreferredSize(new Dimension(100, 30));
		getStartedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUICityPicker cityPickerFrame = new GUICityPicker();
				try {
					cityPickerFrame.cityPickerWindow(travellerList, cityHashMap);
				} catch (SQLException | IOException | WikipediaNoCityException e1) {
					e1.printStackTrace();
				}
			}
		});
		getStartedButtonPanel.add(getStartedButton);
		startFrame.add(getStartedButtonPanel, BorderLayout.SOUTH);

		startFrame.setResizable(false);
		startFrame.pack();
	}

}
