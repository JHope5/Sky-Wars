/*
 * Jamie Hope
 * Software Development
 * Sky Wars
 */

/* *************
 * ** THREADS **
 * *************/
public class GenerateCommand extends Thread {
	private EnemyShip enemyShip;
	private Invoker invoker;

	// constructor
	public GenerateCommand(EnemyShip enmy, Invoker invk) {
		this.enemyShip = enmy;
		this.invoker = invk;
	}

	// Create new move command for enemy, and add to invoker
	@Override
	public void run() {
		Command move = new MoveCommand(this.enemyShip);
		this.invoker.addCommand(move);
	}
}