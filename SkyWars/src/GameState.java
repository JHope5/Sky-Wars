import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class GameState implements Serializable {
	// current player
	private MasterShip player;
	// list of currently alive enemies
	private ArrayList<EnemyShip> enemyList;

	// constructor
	public GameState() {
		// define enemy list
		this.enemyList = new ArrayList<EnemyShip>();
		}

		// add enemy to current state
		public void addEnemyShip(EnemyShip enemyShip) {
			this.getEnemyList().add(enemyShip);
		}

		// remove enemy from list
		public void removeEnemyShip(EnemyShip enemyShip) {
			this.getEnemyList().remove(enemyShip);
		}

		// getters/setters
		public MasterShip getPlayer() {
			return player;
		}

		public void setPlayer(MasterShip p1) {
			this.player = p1;
		}

		public ArrayList<EnemyShip> getEnemyList() {
			return enemyList;
		}

		public void setEnemyList(ArrayList<EnemyShip> enemyList) {
			this.enemyList = enemyList;
		}
}
