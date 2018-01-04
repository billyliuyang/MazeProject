import java.io.File;

import javax.swing.JFrame;


public class Main {
	public static void main(String[] args) {
		File file = new File("csv/map1.csv");
		JFrame frm = new MazeFrame(file);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setVisible(true);
	}
}
