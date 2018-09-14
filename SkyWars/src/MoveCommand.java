/*
 * Jamie Hope
 * Software Development 3
 * Sky Wars
 */

/* ***************
 * *** COMMAND ***
 * ***************/
public class MoveCommand implements Command {
	private GameActors theActor;
	// constructor
	public MoveCommand(GameActors actor) {
		this.theActor = actor;
	}
	public void execute() {
		this.theActor.move();
	}
}
