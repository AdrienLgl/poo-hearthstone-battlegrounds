package fr.esiea.poo.hearthstonebattleground;

import java.util.List;
import java.util.Random;

public class Player {

	protected int idPlayer;
	protected String name;
	private int buyerId;
	private int level;
	private int hp;	
	private int gold;
	private Board board;	
	
	public Player(int id, String name){
		super();
		this.idPlayer = id;
		this.name = name;
		this.board = new Board();
		this.gold = 3;
		this.hp = 20;
		this.level = 1;
	}
	
	public int getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public List<Minion> getDeck() {
		return this.board.getBoard();
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public void attack(Player player, Minion minion) {
		System.out.println(this.name + " is attacking " + player.getName() + "...");
		List<Minion> opponent = player.getDeck();
		Random rand = new Random();
		Minion victim = opponent.get(rand.nextInt(opponent.size()));
		System.out.println(minion.getName() + " attacked " + victim.getName() + " !");
		victim.receiveDamage(minion.getAttack());
	}
	
	public void receiveDamage(int damage) {
		System.out.println(this.name + " take " + damage + " damage.");
		this.hp -= damage;
	}
	
	public void gainGold(int gold) {
		this.gold += gold;
	}
	
	public void lostGold(int gold) {
		this.gold -= gold;
	}
	
	public void resetCardsAttributes() {
		// reset all cards attributes before fighting
		for (int i =0; i < this.board.getHand().size(); i++) {
			this.board.getHand().get(i).setDefense(this.board.getHand().get(i).originalDefense);
		}
	}
}
