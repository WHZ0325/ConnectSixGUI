import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MainView extends JFrame {
	private static int repeatTimes = 5;
	private static PlayerSelector playerSelectors[];
	MainView() {
		super("ConnectSix");
		setSize(384, 96);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		JLabel label1 = new JLabel("Player 1: ");
		panel.add(label1);
		playerSelectors = new PlayerSelector[2];
		playerSelectors[0] = new PlayerSelector(true);
		panel.add(playerSelectors[0]);
		JLabel label2 = new JLabel("Player 2: ");
		panel.add(label2);
		playerSelectors[1] = new PlayerSelector(false);
		panel.add(playerSelectors[1]);

		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				PlayingView playingView = new PlayingView(playerSelectors[0].player, playerSelectors[1].player);
				playingView.startGame();
			}
		});
		panel.add(confirmButton);

		JButton repeatButton = new JButton("Repeat");
		repeatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(playerSelectors[0].getSelectedIndex() == 1 && playerSelectors[1].getSelectedIndex() == 1) {
					dispose();
					RepeatPlayingView repeatplayingView = new RepeatPlayingView(playerSelectors[0].player, playerSelectors[1].player, repeatTimes);
					repeatplayingView.startGame();
					while(!repeatplayingView.endGame()) {
						repeatplayingView.startGame();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "You have to choose two programs to compete.", "ConnectSix - Warning", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		panel.add(repeatButton);

		add(panel);
		setVisible(true);
	}
	public static void main(String[] args) {
		if(args.length >= 1) {
			repeatTimes = Integer.parseInt(args[0]);
		}
		MainView mainView = new MainView();
	}
}