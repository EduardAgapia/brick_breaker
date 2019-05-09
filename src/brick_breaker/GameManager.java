package brick_breaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class GameManager extends JPanel implements KeyListener, ActionListener{

	private boolean play = false;
	private int score = 0;
	
	private Timer timer;
	
	private static int delay = 7;
	private int playerX = 310;
	
	private int ballPosX = 120;
	private int ballPosY = 350;
	private int ballXDir = -1;
	private int ballYDir = -2;
	
	private static int row = 1;
	private static int col = 13;
	private static int totalBricks = 13;
	
	private static int speed = 30;
	
	private BrickGenerator brickGenerator;
	
	public GameManager() {
		brickGenerator = new BrickGenerator(row, col);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		
		//background
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 692, 592);
		
		//drowing map
		brickGenerator.drowBriks((Graphics2D)g);
		
		//borders
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//score
		g.setColor(Color.WHITE);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		
		//the padle
		g.setColor(Color.GREEN);
		g.fillRect(playerX, 550, 100, 8);
		
		//the ball
		g.setColor(Color.YELLOW);
		g.fillOval(ballPosX, ballPosY, 20, 20);
	
		if(totalBricks * row <= 0) {
			wonMethod(g);
			row++;
		}
		
		if(ballPosY > 570)
			loseMethod(g);
		
		g.dispose();

	}
	
	public void wonMethod(Graphics g) {
			
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			totalBricks += col;
			delay--;
			speed += 10;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won!", 190, 300);
			g.drawString("Your score: "+score, 200, 330);
			

			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press ENTER to RESTART", 230, 200);
			
	}
	
	public void loseMethod(Graphics g) {
		
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over!", 190, 300);
			g.drawString("Your score: "+score, 200, 330);

			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press ENTER to RESTART", 230, 400);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			
			if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYDir = -ballYDir;
			}
			
			A: for(int i = 0; i < brickGenerator.map.length; i++) {
				for(int j = 0; j<brickGenerator.map[0].length; j++) {
					if(brickGenerator.map[i][j] > 0) {
						int brickX = j*brickGenerator.brickWidth + 80;
						int brickY = i*brickGenerator.brickWeight + 50;
						int brickWidth = brickGenerator.brickWidth;
						int brickWeight = brickGenerator.brickWeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickWeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)){
							brickGenerator.setBrickValue(0, i, j);
							totalBricks--;
							score++;
							
							if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
								ballXDir = -ballXDir;
							}else {
								ballYDir = -ballYDir;
							}
							break A;
						}
					}
				}
			}
			
			ballPosX += ballXDir;
			ballPosY += ballYDir;
			
			if(ballPosX < 0) {
				ballXDir = -ballXDir;
			}
			if(ballPosY < 0) {
				ballYDir = -ballYDir;
			}
			if(ballPosX > 670) {
				ballXDir = -ballXDir;
			}
		}
		
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600) {
				playerX = 600;
			}else {
				moveRight(speed);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX <= 10) {
				playerX = 10;
			}else {
				moveLeft(speed);
			}
		}
		
		if(!play && e.getKeyCode() == KeyEvent.VK_SPACE) 
			restart();
		
		
		if(!play && e.getKeyCode() == KeyEvent.VK_ENTER)
				nextLevel();
	}
	
	public void restart() {
		play = true;
		ballPosX = 120;
		ballPosY = 350;
		ballXDir = -1;
		ballYDir = -2;
		playerX = 310;
		score = 0;
		totalBricks = row * col;
		brickGenerator = new BrickGenerator(row, col);
		repaint();

	}
	
	public void nextLevel() {
			play = true;
			ballPosX = 120;
			ballPosX = 350;
			ballXDir = -1;
			ballYDir = -2;
			playerX = 310;
			score = 0;
			brickGenerator = new BrickGenerator(row, col);
			repaint();

	}
	public void moveRight(int speed) {
		play = true;
		playerX += speed;
	}
	public void moveLeft(int speed) {
		play = true;
		playerX -= speed;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	
	

}
