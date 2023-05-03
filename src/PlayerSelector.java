import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PlayerSelector extends JComboBox {
	public PlayerData player;
	private static String s[] = new String[]{ "User", "Program" };
	PlayerSelector() {
		super(s);
		player = new PlayerData();
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(getSelectedIndex());
				player.type = (getSelectedIndex() == 0);
				if(player.type == false) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.showOpenDialog(new JLabel());

					File file = fileChooser.getSelectedFile();
					System.out.println(file);
					if(!player.setFile(file)) {
						setSelectedIndex(0);
						player.type = false;
					}
				}
			}
		});
	}
}
