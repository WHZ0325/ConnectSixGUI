import apple.laf.JRSUIConstants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class PlayerData {
	boolean type;
	File file;
	PlayerData() {
		type = true;
		file = null;
	}

	public boolean setFile(File file) {
		this.file = file;
		return true;
	}
}

public class PlayingView extends JFrame {
	private boolean playerType[] = new boolean[]{ true, true };
	private File programs[];
	private JLabel roundLabel = new JLabel("", JLabel.CENTER);
	private JButton buttons[][];
	private void disableAllButtons() {
//		System.out.println("disableAllButtons");
		for(int i = 0; i < 15; ++i) {
			for(int j = 0; j < 15; ++j) {
				buttons[i][j].setEnabled(false);
			}
		}
	}
	public void enableAllButtons() {
		for(int i = 0; i < 15; ++i) {
			for(int j = 0; j < 15; ++j) {
				buttons[i][j].setEnabled(GameData.available(new Point(i, j)));
			}
		}
	}
	public void updateButtonStatus() {
		if(playerType[GameData.getCurrentTurn()]) enableAllButtons();
		else disableAllButtons();
	}
	private void endGame(int winner) {
		disableAllButtons();

		if(winner == -1) {
			JOptionPane.showMessageDialog(null, "There is something wrong with your program.", "ConnectSix - Error", JOptionPane.PLAIN_MESSAGE, null);
			dispose();
			System.exit(0);
		}
		else if(winner >= 0) {

			int result = JOptionPane.showConfirmDialog(null, winner > 0 ? String.format("The winner is %s %d\n", playerType[winner - 1] ? "User" : "Program", winner) : "The game has drawn.", "ConnectSix - Finished", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				GameData.init();
				for(int i = 0; i < 15; ++i) {
					for(int j = 0; j < 15; ++j) {
						buttons[i][j].setText("");
					}
				}
				updateButtonStatus();
				nextRound();
			}
			else if(result == JOptionPane.NO_OPTION) {
				dispose();
				System.exit(0);
			}
		}
	}
	private boolean place(Point p, int player) {
		boolean nextRound = GameData.place(p);
		buttons[p.x][p.y].setText(player == 0 ? "B" : "W");
		updateButtonStatus();
		return nextRound;
	}
	PlayingView(PlayerData player1, PlayerData player2) {
		super("ConnectSix");

		playerType[0] = player1.type;
		playerType[1] = player2.type;
		System.out.println(playerType[0] + " " + playerType[1]);

		programs = new File[2];
		if(!playerType[0]) programs[0] = player1.file;
		if(!playerType[1]) programs[1] = player2.file;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(16,16, 0, 0));
		buttons = new JButton[15][15];
		for(int i = 0; i <= 15; ++i) {
			for(int j = 0; j <= 15; ++j) {
				if(i == 0 && j == 0) {
					roundLabel.setBounds(i * 48, j * 48, 48, 48);
					panel.add(roundLabel);
				}
				else if(i == 0 || j == 0) {
					JLabel label = new JLabel(String.format("%d", (i == 0) ? j : i), JLabel.CENTER);
//					label.setSize(48, 48);
					label.setBounds(i * 48, j * 48, 48, 48);

					panel.add(label);
				}
				else {
					--i; --j;
					buttons[i][j] = new JButton();
//					buttons[i][j].setSize(48, 48);
					buttons[i][j].setBounds((i + 1) * 48, (j + 1) * 48, 48, 48);
					buttons[i][j].setName(String.format("%d", i * 15 + j));
					buttons[i][j].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int name = Integer.parseInt(((JButton)e.getSource()).getName());
							if(place(new Point(name / 15, name % 15), GameData.getCurrentTurn())) {
								nextRound();
							}
						}
					});
					panel.add(buttons[i][j]);
					++i; ++j;
				}
			}
		}
		add(panel);

		setSize(buttons[14][14].getX() + buttons[14][14].getWidth() + 2, buttons[14][14].getY() + buttons[14][14].getHeight());

		setVisible(true);

		GameData.init();
		nextRound();
	}
	public void nextRound() {
		int winner = GameData.check();
		if(winner != -1) {
			endGame(winner);
			return;
		}
		GameData.nextRound();
		roundLabel.setText(String.format("R%d", GameData.getRound()));
		int turn = GameData.getCurrentTurn();
		if(!playerType[turn]) {
			disableAllButtons();
			try {
				ProgramController programController = new ProgramController(programs[turn], turn == 0);
				place(programController.getData().p0, turn);
				if(GameData.getRound() != 1) place(programController.getData().p1, turn);
				nextRound();
			} catch(Exception e) {
				endGame(-1);
			}
		}
		else {
			enableAllButtons();
		}
	}
}
