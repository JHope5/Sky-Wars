import java.io.Serializable;
import java.util.Random;

/*
 * Jamie Hope
 * Software Development
 * Sky Wars
 */

@SuppressWarnings("serial")
public abstract class GameActors implements Serializable {
	private int x, y;

	// Changing the current coordinates to a new location
	public void move() {
		
		Random rn = new Random();
		int newX = this.x;
		int newY = this.y;
		int rand = 0;

		// Checking if the position hasn't changed or if it has moved off of the grid
		while ((newX == this.x && newY == this.y) || (newX > GameControl.getX_SIZE() || newY > GameControl.getY_SIZE()) || (newX < 0 || newY < 0)) {
			// Resetting the new position to be the current position
			newX = this.x;
			newY = this.y;
			// Generating a number between -1 and 1
			rand = rn.nextInt(3) - 1;
			// Adding to the current position
			newX += rand;
			rand = rn.nextInt(3) - 1;
			newY += rand;
		}

		// Updating the coordinates
		this.x = newX;
		this.y = newY;
	}

	// Returning an array containing the 'x' and 'y' location of an actor
	public int[] getLocation() {
		int[] location = new int[] { this.getX(), this.getY() };
		return location;
	}

	// getters/setters
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
