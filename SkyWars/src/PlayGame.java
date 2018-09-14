import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Font;

/*
 * Jamie Hope
 * Software Development 3
 * Sky Wars
 */

@SuppressWarnings("serial")
public class PlayGame extends JFrame {
	private JPanel gui = new JPanel();
	private JPanel gameBoard;
	private JPanel buttons;
	private JPanel counters;
	private JLabel skyGrid[][];
	private JLabel destroy, moves, title;
	private static int xSize = 4, ySize = 4;
	private static GameControl gameControl;
	private JButton move, undoMove, offensive, defensive;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				PlayGame frame = new PlayGame(xSize, ySize);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("static-access")
	public PlayGame(int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;

		GameControl.setX_SIZE(this.xSize - 1);
		GameControl.setY_SIZE(this.ySize - 1);

		this.gameControl = new GameControl();
		this.skyGrid = new JLabel[this.xSize][this.ySize];

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 549, 441);
		gui.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(gui);

		gameBoard = new JPanel();
		gameBoard.setBounds(26, 68, 299, 205);
		gameBoard.setLayout(new GridLayout(this.ySize, this.xSize, 0, 0));
		gameBoard.setBorder(new LineBorder(Color.BLACK, 5));
		gui.add(gameBoard);
		
		for (int y = 0; y < this.ySize; y++) {
			for (int x = 0; x < this.xSize; x++) {
				JLabel lblGrid = new JLabel();
				ImageIcon icon = new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB));
				lblGrid.setIcon(icon);
				lblGrid.setBorder(new LineBorder(Color.BLACK));
				lblGrid.setHorizontalTextPosition(JLabel.CENTER);
				lblGrid.setVerticalTextPosition(JLabel.CENTER);
				lblGrid.setFont(new Font("Segoe UI", Font.PLAIN, 18));
				skyGrid[x][y] = lblGrid;
				gameBoard.add(skyGrid[x][y]);
			}
		}
		title = new JLabel("Sky Wars!");
		title.setBounds(127, 0, 325, 57);
		title.setFont(new Font("Segoe UI", Font.PLAIN, 42));
		gui.add(title);
		
		counters = new JPanel();
		counters.setBounds(337, 88, 237, 166);
		counters.setLayout(null);

		destroy = new JLabel("Enemies Destroyed: " + gameControl.getCurrentState().getPlayer().getDestroyCounter());
		destroy.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		destroy.setBounds(0, 55, 198, 81);
		counters.add(destroy);
		gui.setLayout(null);
		
		
		gui.add(counters);
		
		moves = new JLabel("Moves: " + gameControl.getMoveCount());
		moves.setBounds(0, 0, 180, 59);
		counters.add(moves);
		moves.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		
		buttons = new JPanel();
		buttons.setBounds(23, 284, 315, 63);
		gui.add(buttons);
		buttons.setLayout(new GridLayout(2, 2));
		
		move = new JButton("Move");
		move.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		buttons.add(move);
		move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameControl.nextTurn();
				updateGrid();
			}
		});
		
		undoMove = new JButton("Undo");
		undoMove.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		buttons.add(undoMove);
		undoMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameControl.undoMove();
				updateGrid();
			}
		});
		
		offensive = new JButton("Offensive Mode");
		offensive.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		buttons.add(offensive);
		offensive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int type = 1;
				gameControl.getCurrentState().getPlayer().updateMode(type);
				JOptionPane.showMessageDialog(null, "Offensive mode enabled!");
			}
		});
		
		defensive = new JButton("Defensive Mode");
		defensive.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		buttons.add(defensive);
		defensive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int type = 0;
				gameControl.getCurrentState().getPlayer().updateMode(type);
				JOptionPane.showMessageDialog(null, "Defensive mode enabled!");
			}
		});
		
		
		
		
		
		updateGrid();
	}

	@SuppressWarnings("static-access")
	private void updateGrid() {
		if (gameControl.isGameOver()) {
			move.setEnabled(false);
			undoMove.setEnabled(false);
			defensive.setEnabled(false);
			offensive.setEnabled(false);
			title.setText("Game Over!");
		}
		destroy.setText("Enemies Destroyed: " + gameControl.getCurrentState().getPlayer().getDestroyCounter());
		moves.setText("Moves: " + gameControl.getMoveCount());
		for (int x = 0; x < this.xSize; x++) {
			for (int y = 0; y < this.ySize; y++) {
				skyGrid[x][y].setText("");
				ImageIcon icon = new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB));
				skyGrid[x][y].setIcon(icon);
			}
		}

		ArrayList<EnemyShip> enemyList = gameControl.getCurrentState().getEnemyList();
		MasterShip masterShip = gameControl.getCurrentState().getPlayer();
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				int count = 0;

				if ((masterShip.getX() == x) && (masterShip.getY() == y)) {
					String fName = "MasterShip.jpg";
					ImageIcon iconMasterShip = new ImageIcon(fName);
					skyGrid[x][y].setIcon(iconMasterShip);
					skyGrid[x][y].setHorizontalTextPosition(JLabel.CENTER);
				}
				for (EnemyShip enemyShip : enemyList) {
					if ((enemyShip.getX() == x && enemyShip.getY() == y)) {
						count++;
						String fName = enemyShip.getName() + ".jpg";
						ImageIcon iconEnemy = new ImageIcon(fName);
						skyGrid[x][y].setIcon(iconEnemy);
						skyGrid[x][y].setText(count + "");
						skyGrid[x][y].setHorizontalTextPosition(JLabel.CENTER);
						skyGrid[x][y].setForeground(Color.BLACK);

					}
				}
			}
		}
	}
}
