import apple.laf.JRSUIConstants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PlayingView extends JFrame {
	private Player players[];
	private JLabel roundLabel = new JLabel("", JLabel.CENTER);
	private JButton buttons[][];
	private int winner;
	public void disableAllButtons() {
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
	private void place(Point p, int player) {
		GameData.place(p, player);
		buttons[p.x][p.y].setText(player == 0 ? "B" : "W");
	}
	private DataType sharedData;
	PlayingView(Player player1, Player player2) {
		super("ConnectSix");

		System.out.println(player1.getClass().getSimpleName());
		System.out.println(player2.getClass().getSimpleName());

		players = new Player[2];
		players[0] = player1; players[1] = player2;

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
					label.setBounds(i * 48, j * 48, 48, 48);

					panel.add(label);
				}
				else {
					--i; --j;
					buttons[i][j] = new JButton();
					buttons[i][j].setBounds((i + 1) * 48, (j + 1) * 48, 48, 48);
					buttons[i][j].setName(String.format("%d", i * 15 + j));
					buttons[i][j].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int name = Integer.parseInt(((JButton)e.getSource()).getName());
							System.out.printf("Button %d %d was pressed.\n", name / 15, name % 15);
							if(GameData.getRound() == 1) {
								sharedData.p0 = new Point(name / 15, name % 15);
								place(sharedData.p0, GameData.getCurrentTurn());
								GameData.insert(sharedData);
								sharedData.p0.clear(); sharedData.p1.clear();
								nextRound();
							}
							else if(sharedData.p0.isNull()) {
								sharedData.p0 = new Point(name / 15, name % 15);
								place(sharedData.p0, GameData.getCurrentTurn());
								winner = GameData.check();
								if(winner != -1) {
									endGame();
								}
							}
							else {
								sharedData.p1 = new Point(name / 15, name % 15);
								place(sharedData.p1, GameData.getCurrentTurn());
								GameData.insert(sharedData);
								sharedData.p0.clear(); sharedData.p1.clear();
								nextRound();
							}
							enableAllButtons();
						}
					});
					panel.add(buttons[i][j]);
					++i; ++j;
				}
			}
		}
		add(panel);

		setSize(buttons[14][14].getX() + buttons[14][14].getWidth() + 2, buttons[14][14].getY() + buttons[14][14].getHeight());
	}
	public void startGame() {
		System.out.println("---------- Start Game ----------");
		GameData.init();
		winner = -1;
		for(int i = 0; i < 15; ++i) {
			for(int j = 0; j < 15; ++j) {
				buttons[i][j].setText("");
			}
		}
		sharedData = new DataType(-1, -1,-1, -1);
		setVisible(true);
		nextRound();
	}
	public int getWinner() { return this.winner; }
	private void nextRound() {
		winner = GameData.check();
		if(winner != -1) {
			endGame();
		}
		GameData.nextRound();
		int round = GameData.getRound(), turn = GameData.getCurrentTurn();
		roundLabel.setText(String.format("R%d", round));
		try {
			DataType data;
			if(players[turn].getClass().getSimpleName() == "ProgramController") {
				disableAllButtons();
				data = players[turn].getOperation();
				place(data.p0, turn);
				if(round != 1) place(data.p1, turn);
				GameData.insert(data);
				nextRound();
			}
			else {
				enableAllButtons();
			}
		} catch(Exception e) {
			System.out.printf("[Player%d] Round %d: Error\n", turn, round);
			winner = -2; endGame();
		}
	}
	private void endGame() {
		disableAllButtons();

		int winner = getWinner();
		if(winner < 0 || winner > 2) {
			JOptionPane.showMessageDialog(null, "There is something wrong with your program.", "ConnectSix - Error", JOptionPane.PLAIN_MESSAGE, null);
			dispose();
			System.exit(0);
		}

		int result = JOptionPane.showConfirmDialog(null, winner > 0 ? String.format("The winner is %s %d\n", players[winner - 1].getClass().getSimpleName() == "ProgramController" ? "Program" : "User", winner) : "The game has drawn.", "ConnectSix - Finished", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION) {
			/* 懒。。。 */
			dispose();
			System.exit(0);
		}
		else if(result == JOptionPane.CANCEL_OPTION) {
			dispose();
			System.exit(0);
		}
	}
}
