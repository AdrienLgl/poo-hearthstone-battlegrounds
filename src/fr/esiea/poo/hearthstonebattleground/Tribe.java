package fr.esiea.poo.hearthstonebattleground;

enum TribeCollection {
	Elemental,
	Beast,
	Demon,
	Dragon,
	Mech,
	Murloc,
	Naga,
	Pirate,
	Quilboar,
	Warrior
}

enum TribeActions {
	upDefense, // add defense
	upAttack, // add attack
	upLevel, // level up
	reinforcer, // add one minion
	miner, // add gold
	levelCost, // level free
	healer, // add hp
	stingy, // minion is less expensive
	fight // no actions
}

public class Tribe
{	
	private int idTribe;	
	private TribeCollection name;
	private String description;
	private TribeActions action;
	
	public Tribe(TribeCollection name, String description, int idTribe, TribeActions action){
		super();
		this.name = name;
		this.description = description;
		this.idTribe = idTribe;
		this.action = action;
	}
	
	public int getIdTribe() {
		return idTribe;
	}

	public void setIdTribe(int idTribe) {
		this.idTribe = idTribe;
	}

	public TribeCollection getName() {
		return name;
	}

	public void setName(TribeCollection name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TribeActions getAction() {
		return action;
	}

	public void setAction(TribeActions action) {
		this.action = action;
	}
	
}

