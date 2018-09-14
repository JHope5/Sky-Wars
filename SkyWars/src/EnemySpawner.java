import java.util.Random;

/*
 * Jamie Hope
 * Software Development 3
 * Sky Wars
 */

public final class EnemySpawner {
	
	private EnemySpawner() {
	}

	// Using the random number found to choose a type of enemy
	private static EnemyShip spawnEnemy(int type) {
		// Declaring a new enemy
		EnemyShip enemyShip = null;

		if (type == 0) {
			enemyShip = new BattleCruiser();
		} else if (type == 1) {
			enemyShip = new BattleShooter();
		} else if (type == 2) {
			enemyShip = new BattleStar();
		}
		return enemyShip;
	}

	// Generating a random enemy
	public static EnemyShip randomEnemyShip() {
		
		Random rand = new Random();
		// Get a number between 0 and 2 and get the enemy
		return EnemySpawner.spawnEnemy(rand.nextInt(3));
	}

}
