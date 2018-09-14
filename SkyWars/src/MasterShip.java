import java.util.Random;


@SuppressWarnings("serial")
public class MasterShip extends GameActors {
	private CombatMode combatMode;
	public int destroyCounter;

	// constructor
	public MasterShip() {
		// new random and temporary ints
		Random rand = new Random();
		int startX, startY;

		// set number of enemies killed to 0
		this.destroyCounter = 0;

		// roll starting locations
		startX = rand.nextInt(GameControl.getX_SIZE() + 1);
		startY = rand.nextInt(GameControl.getY_SIZE() + 1);

		// check if start is top left
		if (startX == 0 && startY == 0) {
			// roll random
			int select = rand.nextInt(0);
			if (select == 0) {
				// increase x by one
				startX++;
			}
			if (select == 1) {
				// increase y by one
				startY++;
			}
		}
		// define start location
		this.setX(startX);
		this.setY(startY);

		// set starting diet
		updateMode(0);
	}

	// change the ogre's current diet
	public void updateMode(int type) {
		if (type == 0) {
			this.setMode(new DefensiveMode());
		} else if (type == 1) {
			this.setMode(new OffensiveMode());
		}
	}

	// setters/getters
	public CombatMode getCombatMode() {
		return combatMode;
	}

	public void setMode(CombatMode combatMode) {
		this.combatMode = combatMode;
	}

	public int getDestroyCounter() {
		return destroyCounter;
	}

	public void addDestroy() {
		this.destroyCounter++;
	}
}
