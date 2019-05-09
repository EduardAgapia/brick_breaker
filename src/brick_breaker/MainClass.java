package brick_breaker;

import javax.swing.JFrame;

public class MainClass {
	
	public static void main(String[] args) {
		
		JFrame obj = new JFrame();
		GameManager game = new GameManager();
		
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Brick Breaker");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(game);
		
	}

}
