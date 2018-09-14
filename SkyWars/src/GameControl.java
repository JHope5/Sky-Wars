import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

/*
 * Jamie Hope
 * Software Development 3
 * Sky Wars
 */

public class GameControl {
	// default grid size
	private static int X_SIZE = 3;
	private static int Y_SIZE = 3;
	// turn counter
	private int moveCount;
	// game over bool
	private boolean gameOver;
	// stacks to hold previous/next moves
	private Stack<GameState> undoMove;
	// current state of game board
	private GameState currentState;
	// invoker to execute move commands
	private Invoker invoker;
	// constructor
	public GameControl() {
		MasterShip masterShip = new MasterShip();
		// Setting the moves counter to 1
		this.moveCount = 1;
		// Setting the game to not be over
		this.gameOver = false;
		// Defining stacks
		this.undoMove = new Stack<GameState>();
		// Declaring a new swamp state for game to start on
		this.setCurrentState(new GameState());
		// Setting the MasterShip as current player
		this.getCurrentState().setPlayer(masterShip);
	}

	// Moving all living actors
	public void nextTurn() {
		// Checking if the MasterShip is still in one piece
		if (!this.gameOver) {
			// Increment turn counter
			this.moveCount++;
			// Deep copy and push current swampState into undo stack
			GameState gameCopy = (GameState) GameSerializer.copy(this.currentState);
			this.undoMove.push(gameCopy);
			// Clearing invoker of all commands
			this.invoker = new Invoker();
			// Creating move command for player
			Command move = new MoveCommand(this.currentState.getPlayer());
			// Adding to invoker
			this.invoker.addCommand(move);
			// Declaring a new array for enemy move commands generators
			GenerateCommand[] genCom = new GenerateCommand[this.currentState.getEnemyList().size()];
			// Looping through all the enemies
			for (int i = 0; i < this.currentState.getEnemyList().size(); i++) {
				// Declaring a new move command generator for current enemy
				genCom[i] = new GenerateCommand(this.currentState.getEnemyList().get(i), invoker);
				// Starting thread to generate move command
				genCom[i].start();
			}

			// Looping through every command generator
			for (int i = 0; i < this.currentState.getEnemyList().size(); i++) {
				try {
					// Making sure the command generator is finished
					genCom[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// Executing all move commands
			invoker.execute();
			
			this.rollEnemy();
			
			// Checking for any conflicts
			this.checkForConflict();
			System.out.println();

		} else {
		}
	}
	
	/* ***************
	 * *** FACTORY ***
	 * ***************/
	
	// 1 in 3 chance of spawning a new enemy
	public void rollEnemy() {
		// Getting a random number
		Random rand = new Random();
		int rn = rand.nextInt(3);
		// If the number is 0
		if (rn == 0) {
			// Creating a new enemy
			EnemyShip en = EnemySpawner.randomEnemyShip();

			JOptionPane.showMessageDialog(null, "A " + en.getName() + " has joined the fight!");
			// Adding a new enemy ship to the current state
			this.currentState.addEnemyShip(en);
		}
	}


	public void undoMove() {
		// Checking if an undo is possible
		if (this.undoMove.size() >= 1) {
			// Decrement moves counter
			this.moveCount--;
			// Popping the previous state off of the stack
			GameState prev = this.undoMove.pop();

			// Setting the current state as the previous state
			this.currentState = prev;
		} else {
			// error message if no moves to undo
			JOptionPane.showMessageDialog(null, "There are no moves to undo!");
		}
	}

	// Checking if any conflicts have occurred in the grid
	public void checkForConflict() {
		// If any enemies are in the game
		if (this.currentState.getEnemyList().size() > 0) {
			// Getting the current location of player
			int[] location = this.currentState.getPlayer().getLocation();
			// Declaring a new array list for all enemies in the same space as the player
			ArrayList<EnemyShip> enemies = new ArrayList<EnemyShip>();

			// Checking the enemies
			for (EnemyShip enemy : this.currentState.getEnemyList()) {
				// If an enemy's location is the same as the player's
				if (Arrays.equals(location, enemy.getLocation())) {
					enemies.add(enemy);
				}
			}
			// If a conflict is found
			if (enemies.size() > 0) {;

				// If there is only one enemy involved in the conflict
				if (enemies.size() == 1) {
					// Message to say player has won the fight 
					JOptionPane.showMessageDialog(null, "A " + ((EnemyShip) enemies.get(0)).getName() + " has been destroyed by MasterShip");
					// Increment player destroy count
					this.currentState.getPlayer().addDestroy();
					// Removing the enemy from the game
					this.currentState.removeEnemyShip((EnemyShip) enemies.get(0));

					// If the conflict involves 2 enemy ships
				} else if (enemies.size() == 2) {
					// Getting the current combat mode
					CombatMode combatMode = this.currentState.getPlayer().getCombatMode();

					// If player is in defensive mode
					if (combatMode instanceof DefensiveMode) {
						// Message to say the player has lost the fight
						JOptionPane.showMessageDialog(null, "MasterShip has been destroyed by a " + enemies.get(0).getName() + " and a " + enemies.get(1).getName() + ".");
						
						this.gameOver = true;

					} else {
						// Message to say the player has won the fight
						JOptionPane.showMessageDialog(null, "A " + enemies.get(0).getName() + " and " + enemies.get(1).getName() + " have been destroyed by MasterShip.");
						
						// loop through enemies
						for (EnemyShip enemy : enemies) {
							// Remove the enemy from the grid
							this.currentState.removeEnemyShip(enemy);
							// Increment destroy count
							this.currentState.getPlayer().addDestroy();
						}
					}

				// If there are more than 2 enemies
				} else {
					// Message to say the player has lost the fight
					JOptionPane.showMessageDialog(null, "MasterShip has been destroyed by 3 or more enemy ships!");
					
					this.gameOver = true;
				}
			}

			// if no enemies
		} else {
			// end conflict check
			return;
		}
	}

	
	public void gameOver() {
		// Setting bool to true
		if (this.gameOver) {
			this.gameOver = (!gameOver);
		}
		
		// Showing 'Game Over' message
		JOptionPane.showMessageDialog(null, "Game over! \n\nYou made " + this.moveCount + " moves, and destroyed " + this.getCurrentState().getPlayer().getDestroyCounter() + " enemy ships!");
	}

	// getters/setters
	public Stack<GameState> getUndo() {
		return undoMove;
	}

	public void setUndo(Stack<GameState> undoMove) {
		this.undoMove = undoMove;
	}

	public GameState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(GameState currentState) {
		this.currentState = currentState;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public static int getX_SIZE() {
		return X_SIZE;
	}

	public static void setX_SIZE(int x_SIZE) {
		X_SIZE = x_SIZE;
	}

	public static int getY_SIZE() {
		return Y_SIZE;
	}

	public static void setY_SIZE(int y_SIZE) {
		Y_SIZE = y_SIZE;
	}

	public int getMoveCount() {
		return moveCount;
	}
}
