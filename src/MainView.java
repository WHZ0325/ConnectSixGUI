import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class MainView extends JFrame {
	public static PlayerSelector playerSelector1, playerSelector2;
	MainView() {
		super("ConnectSix");
		setLayout(new BorderLayout());
		setSize(384, 96);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		panel.add(BorderLayout.CENTER, new JLabel("Player 1: "));
		playerSelector1 = new PlayerSelector();
		panel.add(BorderLayout.CENTER, playerSelector1);
		panel.add(BorderLayout.CENTER, new JLabel("Player 2: "));
		playerSelector2 = new PlayerSelector();
		panel.add(BorderLayout.CENTER, playerSelector2);
		JButton player2 = new JButton("User");
		add(panel);

		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				PlayingView playingView = new PlayingView(playerSelector1.player, playerSelector2.player);
			}
		});
		add(BorderLayout.SOUTH, confirmButton);

		setVisible(true);
	}
	public static void main(String[] args) {
		MainView mainView = new MainView();
	}
}