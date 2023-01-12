package fr.esiea.poo.hearthstonebattleground;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	public List<Minion> hand;
	public List<Minion> board;
	
	public Board(){
		super();
		this.hand = new ArrayList<Minion>();
		this.board = new ArrayList<Minion>();
	}
	
	public List<Minion> getHand() {
		return hand;
	}

	public void setHand(List<Minion> hand) {
		this.hand = hand;
	}

	public List<Minion> getBoard() {
		return board;
	}

	public void setBoard(List<Minion> board) {
		this.board = board;
		this.applyTribeAttributes();
	}
	
	public void displayBoard() {
		System.out.println("================BOARD===============");
		for (int i =0; i < this.board.size(); i++) {
			System.out.println(this.board.get(i).getName() + " D:" + this.board.get(i).getDefense());
		}
		System.out.println("====================================");
	}
	
	private void applyTribeAttributes() {
		for (int i = 0; i < this.board.size(); i++) {
			switch (this.board.get(i).getTribe().getName()) {
				case Beast:
					// add 2 attack points for beast minion in the deck
					for (int j = 0; j < this.board.size(); j++) {
						if (this.board.get(j).getIdMinion() != this.board.get(i).getIdMinion() &&
								this.board.get(j).getTribe().getName() == TribeCollection.Beast) {
							this.board.get(j).setAttack(this.board.get(j).getAttack() + 2);
							System.out.println(this.board.get(j).getName() + "gain 2 attack points !");
						}
					}
					break;
				case Dragon:
					// add 2 defense points for dragon minion in the deck
					for (int j = 0; j < this.board.size(); j++) {
						if (this.board.get(j).getIdMinion() != this.board.get(i).getIdMinion() &&
								this.board.get(j).getTribe().getName() == TribeCollection.Dragon) {
							this.board.get(j).setDefense(this.board.get(j).getDefense() + 2);
							System.out.println(this.board.get(j).getName() + "gain 2 defense points !");
						}
					}
					break;
				default:
					break;
			}
		}
	}
}
