/*
 * Jamie Hope
 * Software Development
 * Sky Wars
 */

@SuppressWarnings("serial")
public abstract class EnemyShip extends GameActors {
	// constructor
	public EnemyShip() {
		// Starting locations for all enemy ships
		setX(0);
		setY(0);
	}

	public EnemyShip getName() {
		EnemyShip name = EnemySpawner.randomEnemyShip();
		return name;
	}
}
