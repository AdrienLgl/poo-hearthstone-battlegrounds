package fr.esiea.poo.hearthstonebattleground;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
	
	private List<Player> players;
	private List<Minion> cards;
	private Gameplay gameplay;
	
	public Game(){
		super();
		this.cards = this.getMinions();
		this.players = new ArrayList<Player>();
		Player player1 = new Player(this.players.size(), "Player1");
		this.addPlayer(player1);
		Player player2 = new Player(this.players.size(), "Player2");
		this.addPlayer(player2);		
		// Gameplay gameplay = new Gameplay();
	}
	
	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Minion> getCards() {
		return cards;
	}

	public void setCards(List<Minion> cards) {
		this.cards = cards;
	}

	public Gameplay getGameplay() {
		return gameplay;
	}

	public void setGameplay(Gameplay gameplay) {
		this.gameplay = gameplay;
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
	
	public List<Minion> getMinions() { // get all minions from XML file
		List<Minion> minions = new ArrayList<Minion>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("assets/battlegrounds_cards.xml"));
			document.getDocumentElement().normalize();
			NodeList cardsList = document.getElementsByTagName("cards");
			for(int i = 0; i < cardsList.getLength(); i++) {
				Node card = cardsList.item(i); // get card item
				if (card.getNodeType() == Node.ELEMENT_NODE) {
					Element cardElement = (Element) card;
					int id = Integer.parseInt(cardElement.getElementsByTagName("id").item(0).getTextContent()); // id
					String name = cardElement.getElementsByTagName("name").item(0).getTextContent(); // name
					int attack = Integer.parseInt(cardElement.getElementsByTagName("attack").item(0).getTextContent()); // attack
					int defense = Integer.parseInt(cardElement.getElementsByTagName("health").item(0).getTextContent()); // health
					int cost = Integer.parseInt(cardElement.getElementsByTagName("manaCost").item(0).getTextContent()); // cost
					int rank = 0;
					String description = cardElement.getElementsByTagName("flavorText").item(0).getTextContent(); // description
					NodeList detailsList = cardElement.getElementsByTagName("battlegrounds"); // get card details
					for(int j = 0; j < detailsList.getLength(); j++) {
						Node details = detailsList.item(j);
						if (details.getNodeType() == Node.ELEMENT_NODE) {
							Element detailsElement = (Element) details;
							rank = Integer.parseInt(detailsElement.getElementsByTagName("tier").item(0).getTextContent());
						}
					}
					// Minion instantiation
					Minion minion = new Minion(id, name, rank, attack, defense, cost, description);
					// Add minion to the list
					minions.add(minion);
				}
			}
			return minions;
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
