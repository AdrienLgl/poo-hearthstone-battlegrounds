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
	}
	
	public void displayBoard() {
		System.out.println("================BOARD===============");
		for (int i =0; i < this.board.size(); i++) {
			System.out.println(this.board.get(i).getName() + " D:" + this.board.get(i).getDefense());
		}
		System.out.println("====================================");
	}
}
