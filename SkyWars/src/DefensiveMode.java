import java.io.Serializable;
import javax.swing.JOptionPane;

/*
 * Jamie Hope
 * Software Development
 * Sky Wars
 */

/* **************
 * ** STRATEGY **
 * **************/
@SuppressWarnings("serial")
public class DefensiveMode implements Serializable, CombatMode {

	public void combatMode() {
		JOptionPane.showMessageDialog(null, "Defensive mode enabled!");
	}

}
