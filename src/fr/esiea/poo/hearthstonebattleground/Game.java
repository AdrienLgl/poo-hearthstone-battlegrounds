package fr.esiea.poo.hearthstonebattleground;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Game {
	
	private Set<Player> players;
	private List<Minion> cards;
	private Gameplay gameplay;
	
	public Game(){
		super();
		this.cards = this.getMinions();
		this.players = new HashSet<>();
		Player player1 = new Player(this.players.size(), "Player1", this.cards);
		this.addPlayer(player1);
		Player player2 = new Player(this.players.size(), "Player2", this.cards);
		this.addPlayer(player2);
		System.out.println(this.players.toString());
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	public void reset() {
		this.gameplay.finish();
	}

	public void startGame() {
		// TODO implement me
	}
	
	public List<Minion> getMinions() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("assets/battlegrounds_cards.xml"));
			document.getDocumentElement().normalize();
			NodeList cardsList = document.getElementsByTagName("cards");
			for(int i = 0; i < cardsList.getLength(); i++) {
				Node card = cardsList.item(i);
				System.out.println(card.getNodeName());
				if (card.getNodeType() == Node.ELEMENT_NODE) {
					Element cardElement = (Element) card;
					NodeList cardDetails = card.getChildNodes();
					for (int j = 0; j < cardDetails.getLength(); j++) {
						Node details = cardDetails.item(j);
						if (details.getNodeType() == Node.ELEMENT_NODE) {
							Element detailsElement = (Element) details;
						}
					}
					
				}
			}
			
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
		} catch(SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
