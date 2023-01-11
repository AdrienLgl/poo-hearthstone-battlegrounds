package fr.esiea.poo.hearthstonebattleground;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Tavern
{	
	private static final int AVAILABLECARDS = 3;
	private static final int MAXHANDCARDS = 3;
	private List<Minion> deck;
	private List<Player> buyers;
	private List<List<Minion>> shops;

	public Tavern(List<Minion> deck){
		super();
		this.deck = deck;
		this.buyers = new ArrayList<Player>();
		this.shops = new ArrayList<List<Minion>>();
	}
	
	public void open(Player player) {
		// open tavern and gain 1 gold
		player.gainGold(1);
		// add player to buyers list
		this.buyers.add(player);
		int id = this.buyers.size() - 1;
		player.setBuyerId(id);
		// generate his shop
		List<Minion> playerShop = new ArrayList<Minion>();
		this.shops.add(playerShop);
		this.setPlayerShop(id);
		// reset previous cards attributes
		player.resetCardsAttributes();
		// reduce level up cost
		if (player.getCostLevelUp() > 1) {
			player.setCostLevelUp(player.getCostLevelUp()-1);
		}
	}
	
	// refresh shop player offer for one gold
	public void refresh(int playerId) {
		Player player = this.buyers.get(playerId);
		if (player.getGold() > 1) {
			player.setGold(player.getGold() - 1);
			this.setPlayerShop(playerId);
			this.displayShop();
		} else {
			System.out.println(player.getName() + "has insufficient gold to refresh tavern offer !");
		}
		
	}

	// declare player shop
	public void setPlayerShop(int playerId) {
		// remove all values in shop
		this.shops.get(playerId).clear();
		// 3 cards to draw
		Random rand = new Random();
		for (int i = 0; i < AVAILABLECARDS; i++) {
            Minion minion = this.drawCard(rand, playerId);
            this.shops.get(playerId).add(minion);
		}
	}
	
	// draw one card
	private Minion drawCard(Random rand, int playerId) {
        Minion minion;
        do {
            int randomIndex = rand.nextInt(this.deck.size());
            minion = this.deck.get(randomIndex);
        } while (minion.getRank() > this.buyers.get(playerId).getLevel() || uniqueCard(minion));
        return minion;
	}
	
    // IMPORTANT: check if card isn't in other players hands (conflict)
	private boolean uniqueCard(Minion minion) {
		for(int i = 0; i < this.buyers.size(); i++) {
			for (int j = 0; j < this.shops.size(); j++) {
				for (int card = 0; card < this.shops.get(this.buyers.get(i).getBuyerId()).size(); card++) {
					if (this.shops.get(this.buyers.get(i).getBuyerId()).get(card) == minion) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean sell(Minion minion, int playerId) {
		// get player from buyers list
		Player player = this.buyers.get(playerId);
		try {
			// remove minion from his hand
			player.getBoard().getHand().remove(minion);
			// add one gold
			player.gainGold(1);
			System.out.println("\n" + minion.getName() + " selled by " + player.getName() + "\n");
			return true;	
		} catch (Exception error) {
			return false;
		}
	}
	
	public boolean recruit(Minion minion, int playerId) {
		// get player from buyers list
		Player player = this.buyers.get(playerId);
		if (player.getBoard().getHand().size() >= MAXHANDCARDS) {
			System.out.println(player.getName() + " should sell minions before buy others");
			return false;
		} else {
			// check cost before recruit
			if (player.getGold() >= minion.getCost()) {
				try {
					// add minion to player hand
					player.getBoard().getHand().add(minion);
					// pay minion
					player.lostGold(minion.getCost());
					// remove minion from player shop
					this.shops.get(playerId).remove(minion);
					System.out.println("\n" + minion.getName() + " is now in " + player.getName() + " hand !" + "\n");
					return true;
				} catch (Exception error) {
					return false;
				}
			} else {
				System.out.println("\n" + player.getName() + " has insuficient gold to recruit " + minion.getName() + "\n");
				return false;
			}
		}
	}
	
	public void levelUp(int playerId) {
		// check if player has 5 gold
		if (this.buyers.get(playerId).getGold() >= 5) {
			// maximum level for tavern
			if (this.buyers.get(playerId).getLevel() >= 4) {
				System.out.println("Your tavern level is maximise");
			} else {
				// level up
				this.buyers.get(playerId).setLevel(this.buyers.get(playerId).getLevel() + 1);
				this.buyers.get(playerId).setCostLevelUp(5);
				this.buyers.get(playerId).lostGold(5);
			}
		} else {
			System.out.println(this.buyers.get(playerId).getName() + " has insuficient gold to level up !");
		}
	}
	

	public void close() {
		// quit buyers list and remove player shop
		this.buyers.clear();
		this.shops.clear();
	}
	
	public void displayShop() {
		for (int j = 0; j < this.buyers.size(); j++) {
			System.out.println("========== Tavern shop "+ this.buyers.get(j).getName() + " (" + this.buyers.get(j).getGold() + " gold) ============");
			for (int i = 0; i < this.shops.get(j).size(); i++) {
				Minion minion = this.shops.get(j).get(i);
				System.out.println(minion.getName() + " A:" + minion.getAttack()
					+ " D:" + minion.getDefense() + " Cost:" + minion.getCost() + " Level:" + minion.getRank());
			}
			System.out.println("====================================================");
		}
		
	}
	
	public List<List<Minion>> getShops() {
		return shops;
	}

	public void setShops(List<List<Minion>> shops) {
		this.shops = shops;
	}
	
	public boolean moveMinion(Minion minion, int position, int playerId) {
		try {
			List<Minion> playerHand = this.buyers.get(playerId).getBoard().getHand();
			int oldPosition = playerHand.indexOf(minion);
			// swap minion items
			Collections.swap(playerHand, position, oldPosition);
			return true;
		} catch (Exception error) {
			System.out.println("Impossible to change " + minion.getName() + " position...");
			return false;
		}
	}
}

