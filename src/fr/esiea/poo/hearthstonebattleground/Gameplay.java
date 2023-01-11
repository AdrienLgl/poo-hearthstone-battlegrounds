package fr.esiea.poo.hearthstonebattleground;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		try {
			Thread.sleep(5000);
			this.displayMainMenu(this.attacker);
			this.displayMainMenu(this.defender);
			List<Minion> hand = new ArrayList<Minion>();
			for (int i = 0; i < this.attacker.getBoard().getHand().size(); i++) {
				hand.add(this.attacker.getBoard().getHand().get(i));
				if (i == 3) {
					break;
				}
			}
			this.attacker.getBoard().setBoard(hand);
			hand.clear();
			for (int i = 0; i < this.defender.getBoard().getHand().size(); i++) {
				hand.add(this.defender.getBoard().getHand().get(i));
				if (i == 3) {
					break;
				}
			}
			this.defender.getBoard().setBoard(hand);
			this.tavern.close();
			this.phase = Phase.FIGHT;
		} catch (Exception error) {
			
		}
		
	}
	
	private void displayMainMenu(Player player) {
		Scanner scanner = new Scanner(System.in);
        boolean continueMenu = true;

        while (continueMenu) {
        	System.out.println("Welcome " + player.getName() + " !");
        	System.out.println("\n======== Tavern =========");
            System.out.println("  ______________________________");
            System.out.println(" |                              |");
            System.out.println(" | Name: " + player.getName());
            System.out.println(" | Gold: " + player.getGold());
            System.out.println(" | Level: " + player.getLevel());
            System.out.println(" | HP: " + player.getHp());
            System.out.println(" |______________________________|\n");
            System.out.println(" --- Menu ---");
            System.out.println(" 1. Buy minion");
            System.out.println(" 2. Sell minion");
            System.out.println(" 3. Refresh shop");
            System.out.println(" 4. Level up");
            System.out.println(" 5. Prepare your board");
            System.out.println(" 6. Exit");
            System.out.print("Enter your choice: ");
            
            String input = scanner.nextLine();
            int choice;
            try{
              choice = Integer.parseInt(input);
            }catch(NumberFormatException e){
              System.out.println("Invalid input, please enter a number:");
              continue;
            }
            switch (choice) {
                case 1:
                    this.buy(this.tavern.getShops().get(player.getBuyerId()), player.getBuyerId());
                    break;
                case 2:
                    this.sell(player);
                    break;
                case 3:
                	this.tavern.refresh(player.getBuyerId());
                    break;
                case 4:
                    this.tavern.levelUp(player.getBuyerId());
                    break;
                case 5:
                	this.prepareBoard(player);
                	break;
                case 6:
                    continueMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
	}
	
	private void prepareBoard(Player player) {
		Scanner boardScanner = new Scanner(System.in);
   	 	Scanner positionScanner = new Scanner(System.in);
		boolean continueSubMenu = true;
		while (continueSubMenu) {
            System.out.println("--- Prepare your board ---");
            for (int i = 0; i < player.getBoard().getHand().size(); i++) {
            	System.out.println(i+1 + ". " + player.getBoard().getHand().get(i).getName() + "[Def:" + player.getBoard().getHand().get(i).getDefense() +
            		"; Att:"+ player.getBoard().getHand().get(i).getAttack() +"]");
            }
            System.out.println("0. Back to main menu");
            System.out.print("Select your minion: ");

            int choice = boardScanner.nextInt();
            
            if (choice == 0) {
                continueSubMenu = false;
            } else if (choice >= 1 && choice < 7) {
	        	if (player.getBoard().getHand().size() < choice) {
	                System.out.println("This card isn't available.");
	                continue;
	        	}
	
	             System.out.print("Enter his new position (betwenn 1 & " + player.getBoard().getHand().size()+ ") :");
	             int position = 0;
	             while(!positionScanner.hasNextInt() && positionScanner.nextInt() > player.getBoard().getHand().size() && positionScanner.nextInt() < 0){
	               System.out.println("Please, enter a correct position:");
	               positionScanner.next();
	             }
	             position = positionScanner.nextInt();
	             if (!this.tavern.moveMinion(player.getBoard().getHand().get(choice - 1), position - 1, player.getBuyerId())) {
	                 continueSubMenu = false;
	             }
            } else {
                System.out.println("Invalid choice. Please choose again.");
            }
        }
	}
	
	private void sell(Player player) {
		Scanner sellScanner = new Scanner(System.in);
		boolean continueSubMenu = true;
		while (continueSubMenu) {
            System.out.println("--- Sell minion (select your minion) ---");
            for (int i = 0; i < player.getBoard().getHand().size(); i++) {
            	System.out.println(i+1 + ". " + player.getBoard().getHand().get(i).getName() + "[Def:" + player.getBoard().getHand().get(i).getDefense() +
            		"; Att:"+ player.getBoard().getHand().get(i).getAttack() +"]");
            }
            System.out.println("0. Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = sellScanner.nextInt();
            
            if (choice == 0) {
                continueSubMenu = false;
            } else if (choice >= 1 && choice < 7) {
            	if (player.getBoard().getHand().size() < choice) {
                    System.out.println("This card isn't available.");
                    continue;
            	}
                if (!this.tavern.sell(player.getBoard().getHand().get(choice - 1), player.getBuyerId())) {
                    continueSubMenu = false;
                }
            } else {
                System.out.println("Invalid choice. Please choose again.");
            }
        }
	}
	
	private void buy(List<Minion> shop, int playerId) {
		Scanner buyScanner = new Scanner(System.in);
		boolean continueSubMenu = true;
		while (continueSubMenu) {
            System.out.println("--- Buy minion (select your minion) ---");
            for (int i = 0; i < shop.size(); i++) {
            	System.out.println(i+1 + ". " + shop.get(i).getName() + "[Def:" + shop.get(i).getDefense() +
            		"; Att:"+ shop.get(i).getAttack() + "; Cost:" + shop.get(i).getCost()+"]");
            }
            System.out.println("4. Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = buyScanner.nextInt();
            switch (choice) {
                case 1:
                    if (!this.tavern.recruit(shop.get(0), playerId)) {
                        continueSubMenu = false;
                    }
                    break;
                case 2:
                	if (!this.tavern.recruit(shop.get(1), playerId)) {
                        continueSubMenu = false;
                    }
                    break;
                case 3:
                    if (!this.tavern.recruit(shop.get(2), playerId)) {
                        continueSubMenu = false;
                    }
                    break;
                case 4:
                    continueSubMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
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
