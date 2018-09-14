import java.io.Serializable;

import javax.swing.JOptionPane;

/*
 * Jamie Hope
 * Software Development 3
 * Sky Wars
 */

/* **************
 * ** STRATEGY **
 * **************/
@SuppressWarnings("serial")
public class OffensiveMode implements CombatMode, Serializable {

	public void combatMode() {
		JOptionPane.showMessageDialog(null, "Offensive mode enabled!");
	}

}
