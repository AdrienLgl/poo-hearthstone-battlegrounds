package fr.esiea.poo.hearthstonebattleground;

import java.util.ArrayList;
import java.util.List;

enum Phase {
	RECRUITMENT,
	FIGHT
}

public class Gameplay extends Game {

	private int turn;
	private int playerTurn;
	private Phase phase;
	private Player attacker;
	private Player defender;
	private Tavern tavern; // main game tavern
	
	public Gameplay(){
		super();
		this.attacker = super.getPlayers().get(0);
		this.defender = super.getPlayers().get(1);
		this.tavern = new Tavern(super.getCards()); // create tavern
		this.phase = Phase.RECRUITMENT;
		this.nextTurn();
	}

	public void nextTurn() {
		while (this.attacker.getHp() > 0 && this.defender.getHp() > 0) {
			System.out.println("==============" + this.phase + "==============");
			this.turn++;
			this.playerTurn++;
			if (this.phase == Phase.FIGHT) {
				this.fight();
			} else {
				this.recruit();
			}		
		}		
	}
	
	private void recruit() {
		this.tavern.open(this.attacker);
		this.tavern.open(this.defender);
		this.tavern.displayShop();
		int playerId01 = this.attacker.getBuyerId();
		int playerId02 = this.defender.getBuyerId();
		this.tavern.recruit(this.tavern.getShops().get(playerId01).get(1), playerId01);
		this.tavern.recruit(this.tavern.getShops().get(playerId02).get(2), playerId02);
		
		// choose fight cards
		List<Minion> minion01A = new ArrayList<Minion>();
		List<Minion> minion01B = new ArrayList<Minion>();
		minion01A.add(this.attacker.getBoard().getHand().get(0));
		minion01B.add(this.defender.getBoard().getHand().get(0));
		this.attacker.getBoard().setBoard(minion01A);
		this.defender.getBoard().setBoard(minion01B);
		this.attacker.getBoard().displayBoard();
		this.defender.getBoard().displayBoard();
		this.tavern.close();
		this.phase = Phase.FIGHT;
	}
	
	private void fight() {
		int currentCardP1 = 0;
		int currentCardP2 = 0;
		while(this.attacker.getBoard().getBoard().size() > 0 && this.defender.getBoard().getBoard().size() > 0) {
			if (this.attacker.getBoard().getBoard().size() <= currentCardP1) {
				currentCardP1--;
			}
			if (currentCardP1 < 0 || this.attacker.getBoard().getBoard().size() <= 0) {
				this.checkEndGame();
				return;
			}
			System.out.println(currentCardP1);
			this.attacker.attack(this.defender, this.attacker.getBoard().board.get(currentCardP1));
			this.applyRules();
			currentCardP1++;
			if (this.defender.getBoard().getBoard().size() <= currentCardP2) {
				currentCardP2--;
			}
			if (currentCardP2 < 0 || this.defender.getBoard().getBoard().size() <= 0) {
				this.checkEndGame();
				return;
			}
			this.defender.attack(this.attacker, this.defender.getBoard().board.get(currentCardP2));
			this.applyRules();
			currentCardP2++;
		}		
	}
	
	private void applyRules() {
		// card died for players
		for (int i = 0; i < this.attacker.getBoard().getBoard().size(); i++) {
			Minion minion = this.attacker.getBoard().getBoard().get(i);
			if (minion.getDefense() <= 0) {
				this.attacker.getBoard().getBoard().remove(minion);
			}
		}
		for (int i = 0; i < this.defender.getBoard().getBoard().size(); i++) {
			Minion minion = this.defender.getBoard().getBoard().get(i);
			if (minion.getDefense() <= 0) {
				this.defender.getBoard().getBoard().remove(minion);
			}
		}		
		// players lost hp, check if players boards are empty
		if (this.attacker.getBoard().getBoard().size() <= 0) {
			this.attacker.setHp(this.attacker.getHp() - this.defender.getLevel());
			this.phase = Phase.RECRUITMENT;
			System.out.println("\n ========== " + this.defender.getName() + " WIN ============= ");
			System.out.println(this.attacker.getName() + " :" + this.attacker.getHp() + "hp");
			System.out.println(this.defender.getName() + " :" + this.defender.getHp() + "hp");
			return;
		}
		if (this.defender.getBoard().getBoard().size() <= 0) {
			this.defender.setHp(this.defender.getHp() - this.attacker.getLevel());
			this.phase = Phase.RECRUITMENT;
			System.out.println("\n ========== " + this.attacker.getName() + " WIN ============= \n");
			System.out.println(this.attacker.getName() + " :" + this.attacker.getHp() + "hp");
			System.out.println(this.defender.getName() + " :" + this.defender.getHp() + "hp");
			return;
		}
	}
	
	public void checkEndGame() {
		// check if player is dead
		if (this.attacker.getHp() <= 0) {
			System.out.println("\n ========== " + this.defender.getName() + " WIN THE GAME ============= \n");
		}
		
		if (this.defender.getHp() <= 0) {
			System.out.println("\n ========== " + this.attacker.getName() + " WIN THE GAME ============= \n");
		}
	}
	
	public void finish() {
		this.reset();
	}
}
