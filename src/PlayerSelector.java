import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PlayerSelector extends JComboBox {
	public Player player;
	private static String s[] = new String[]{ "User", "Program" };
	PlayerSelector(boolean firstPlayer) {
		super(s);
		player = new UserController(firstPlayer);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(getSelectedIndex());
				int selectedIndex = getSelectedIndex();
				if(selectedIndex == 0) {
					player = new UserController(firstPlayer);
				}
				else if(selectedIndex == 1) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.showOpenDialog(new JLabel());

					File file = fileChooser.getSelectedFile();
					player = new ProgramController(file, firstPlayer);
					System.out.println(file);
				}
			}
		});
	}
}
