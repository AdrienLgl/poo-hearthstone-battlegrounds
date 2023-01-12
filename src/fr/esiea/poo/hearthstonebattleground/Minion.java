package fr.esiea.poo.hearthstonebattleground;

public class Minion {

	protected int idMinion;
	protected String name;
	private int rank;
	private int attack;
	private int defense;
	private int cost;
	private String description;
	private Tribe tribe;
	private int position = 0;
	// original values permit to reset values (tribes attributes)
	protected int originalDefense;
	protected int originalAttack;
	protected int originalCost;
	
	public Minion(int id, String name, int rank, int attack, int defense, int cost, String description, Tribe tribe){
		super();
		this.name = name;
		this.rank = rank;
		this.idMinion = id;
		this.defense = defense;
		this.attack = attack;
		this.cost = 3;
		this.description = description;
		this.originalDefense = defense;
		this.originalAttack = attack;
		this.originalCost = 3;
		this.tribe = tribe;
	}
	
	
	public int getIdMinion() {
		return idMinion;
	}

	public void setIdMinion(int idMinion) {
		this.idMinion = idMinion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Tribe getTribe() {
		return tribe;
	}

	public void setTribe(Tribe tribe) {
		this.tribe = tribe;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public void addAttack(int points) {
		this.attack += points;
	}
	
	public void addDefense(int points) {
		this.defense += points;
	}
	
	public void receiveDamage(int points) {
		this.defense -= points;
		System.out.println(this.name + " lost " + points + " defense (" + this.defense + "hp).");
	}
	
	public int getAttack() {
		return this.attack;
	}
	
	public boolean checkDeath() {
		return this.defense <= 0;
	}
}
