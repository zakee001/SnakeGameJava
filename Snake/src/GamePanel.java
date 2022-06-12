//importing java libraries
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.random.*;

//class for game panel
public class GamePanel extends JPanel implements ActionListener {
	
	//declaring the variables for the game.
	
	static final int SCREEN_WIDTH =625;
	static final int SCREEN_HEIGHT =625;
	static final int UNIT_SIZE =20;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY =75;
	final int X[]= new int [GAME_UNITS];
	final int Y[]= new int [GAME_UNITS];
	int BODY_PARTS = 5;
	int FOOD_EATEN;
	int score;
	int foodX;
	int foodY;
	char direction ='R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame() {
		NewFood();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		draw(g);
		
	}
	//making grid for convenience 
	public void draw(Graphics g) {
		if(running) {
		for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE,SCREEN_HEIGHT);
			g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
		} 	
		//LEVEL 1 BOARD

			g.setColor(Color.orange);
			g.setFont(new Font("Dialog",Font.BOLD,15));
			g.drawString("LEVEL 1", 285, 81);
			
		//making LEVEL-2 
			if (score>=10) {
				g.setColor(Color.BLACK);
				for(int i = 280;i<330;i++) {
					g.fillRect(i,67,25,25);	
					}
				g.setColor(Color.orange);
				g.setFont(new Font("Dialog",Font.BOLD,15));
				g.drawString("LEVEL 2", 285, 81);
				
			for(int i = 400;i<500;i++) {
				g.fillRect(i,300,20,20);	
				}
			}
		
		//snake title 
			g.setColor(Color.orange);
			g.setFont(new Font("Dialog",Font.BOLD,45));
			g.drawString("SNAKE GAME", 160, 40);
			
		//making left border
			g.setColor(Color.darkGray);
			//g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
			for(int i = 0;i<620;i++) {
			g.fillRect(0,i,UNIT_SIZE,UNIT_SIZE);	
			}
		
			
			
		//making top border
			for(int i = 0;i<100;i++) {
				g.fillRect(i,0,40,40);	
				}
			for(int i = 485;i<630;i++) {
				g.fillRect(i,0,40,40);	
				}
			
		//making right border
			for(int i = 620;i>0;i--) {
				g.fillRect(605,i,UNIT_SIZE,UNIT_SIZE);	
				}
			
		//making the bottom border 		
			for(int i = 20;i<625;i++) {
				g.fillRect(i,605,20,20);	
				}
			
		//making food
		g.setColor(Color.RED);
		g.fillOval(foodX, foodY, 20, 20);
		
		//making snake 
		for (int i=0; i<BODY_PARTS;i++) {
			if(i==0) {
				g.setColor(Color.blue);
				g.fillRect(X[i], Y[i],UNIT_SIZE, UNIT_SIZE);
			}
			else {
				g.setColor(new Color(45,180,0));
				g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
				g.fillRect(X[i],Y[i],UNIT_SIZE,UNIT_SIZE);
			     }
		    }
		//code for the score thing.
		g.setColor(Color.gray);
		g.setFont(new Font("Dialog",Font.BOLD,20));
		FontMetrics metrics =getFontMetrics(g.getFont());
		g.drawString("SCORE: " + score,260,65);
		}
		//(SCREEN_WIDTH -metrics.stringWidth("SCORE: "+ score))/2 , g.getFont().getSize()
		else {
			GameOver(g);
		}
	}
	public void move() {
		for(int i=BODY_PARTS;i>0;i--) {
			X[i] =X[i-1];
			Y[i]=Y[i-1];
		}
		switch(direction) {
		case 'U':
			Y[0]=Y[0] - UNIT_SIZE;
			break;
		case 'D':
			Y[0]=Y[0] + UNIT_SIZE;
			break;
		case 'L':
			X[0]=X[0] - UNIT_SIZE;
			break;
		case 'R':
			X[0]=X[0] + UNIT_SIZE;
			break;
		}
		
	}
	public void NewFood() {
		foodX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		foodY = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		
	}
	public void CheckFood() {
		if((X[0] == foodX) && (Y[0] == foodY)) {
			BODY_PARTS++;
			FOOD_EATEN++;
			if (score >=200) {
				score+=20;
			}
			else if (score >=300) {
				score+=30;
			}
			else 
				score +=10;
			NewFood();
		}
		
	}
	public void CheckCollision() {
		//checks if the head collides with the body.
		for(int i = BODY_PARTS;i>0;i--) {
			if((X[0] == X[i] && Y[0]==Y[i])) {
				running = false;
			}
		}
		//checking if the head collides with the left border
		if (X[0]<0) {
			running = false;
		}
		//checking if the head collides with the right border
		if (X[0]> 600) {
			running = false;
		}	
		//checking if the head collides with the top border
		if (Y[0]<0) {
			running = false;
		}
		//checking if the head collides with the bottom border	
		if (Y[0]>600) {
			running = false;
		}
		if (!running) {
			timer.stop();
		}
	}
	public void GameOver(Graphics g) {
		//making left border
		g.setColor(Color.darkGray);
		//g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
		for(int i = 0;i<620;i++) {
		g.fillRect(0,i,UNIT_SIZE,UNIT_SIZE);	
		}
	//making top border
		for(int i = 0;i<395;i++) {
			g.fillRect(i,0,20,20);	
			}
		for(int i = 395;i<630;i++) {
			g.fillRect(i,0,20,20);	
			}
	//making right border
		for(int i = 620;i>0;i--) {
			g.fillRect(605,i,UNIT_SIZE,UNIT_SIZE);	
			}
	//making the bottom border 		
		for(int i = 20;i<625;i++) {
			g.fillRect(i,605,20,20);	
			}
		
	//displaying lines on the gameover screen 
		g.setColor(Color.gray);
		for (int i = 22; i <= 600; i+= 22)
            g.drawLine (22, i, 600, i);
	//displaying box on the gameover screen
		g.setColor(Color.black);
		for(int i = 80;i<360;i++) {
			g.fillRect(i,210,200,200);	
			}
		
		
		//displaying score on the gameover screen
		g.setColor(Color.gray);
		g.setFont(new Font("Dialog",Font.BOLD,75));
		FontMetrics metrics1 =getFontMetrics(g.getFont());
		g.drawString("SCORE: "+ score,90,370);
		
		
		//text of game over 
		g.setColor(Color.red);
		g.setFont(new Font("Dialog",Font.BOLD,75));
		FontMetrics metrics2 =getFontMetrics(g.getFont());
		g.drawString("GAME OVER",90,300);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			CheckFood();
			CheckCollision();
		}
		repaint();
		
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
							
			}
			
		}
	}

}
