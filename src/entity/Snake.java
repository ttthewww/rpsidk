package entity;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import main.Game;

public class Snake implements Boss {
    Game game;
    EntityWindow snakeHeadFrame;

    public CopyOnWriteArrayList<EntityWindow> snakeFrames = new CopyOnWriteArrayList<>();
    int count = 0;
    
    public int frameWidth = 150;
    public int frameHeight = 150;    

    private int snakeSpeed = 400;
    private long lastMoveTime = System.currentTimeMillis(); 

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    public final int SCREEN_WIDTH = dim.width;
	public final int SCREEN_HEIGHT = dim.height;
	final int UNIT_SIZE = 150;

	final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);

	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];

	int applesEaten;
	int appleX;
	int appleY;
    Random random;
    AppleFrame apple;

	char direction = 'R';

    public Snake(Game game){
        this.game = game;
		random = new Random();

        snakeHeadFrame = new SnakeHeadFrame(game, UNIT_SIZE);
        snakeFrames.add(snakeHeadFrame);

		this.game.addKeyListener(new MyKeyAdapter());

        newApple();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMoveTime >= snakeSpeed) {
            moveTowardsApple();
            move ();
            checkApple();
            lastMoveTime = currentTime;
        }

        for(int i = 0; i < snakeFrames.size() ;i++) {
            if(i == 0) {
                snakeFrames.get(i).window.setLocation(x[i], y[i]);
                ((SnakeHeadFrame)snakeFrames.get(i)).updateDirection(direction);
            }
            else {
                snakeFrames.get(i).window.setLocation(x[i], y[i]);
            }			
        }
    }

    public void move(){
        for(int i = snakeFrames.size(); i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}

        switch(direction) {
		    case 'U':
		    	y[0] = y[0] - UNIT_SIZE;
		    	break;
		    case 'D':
		    	y[0] = y[0] + UNIT_SIZE;
		    	break;
		    case 'L':
		    	x[0] = x[0] - UNIT_SIZE;
		    	break;
		    case 'R':
		    	x[0] = x[0] + UNIT_SIZE;
		    	break;
		}
    }
  
    
    public boolean checkCollision(char direction){
        for(int i = snakeFrames.size(); i > 0; i--) {
            switch (direction) {
                case 'R':
                    if((x[0] + 1 == x[i])&& (y[0] == y[i])) {
                        return true;
                    }
                    break;
                case 'L':
                    if((x[0] - 1 == x[i])&& (y[0] == y[i])) {
                        return true;
                    }
                    break;
                case 'U':
                    if((x[0] == x[i]) && (y[0] - 1 == y[i])) {
                        return true;
                    }
                    break;
                case 'D':
                    if((x[0] == x[i]) && (y[0] + 1 == y[i])) {
                        return true;
                    }
                    break;
            }
		}
        return false;
    }

    private void moveTowardsApple() {
        int appleGridX = appleX / UNIT_SIZE;
        int appleGridY = appleY / UNIT_SIZE;

        int headGridX = x[0] / UNIT_SIZE;
        int headGridY = y[0] / UNIT_SIZE;

        if (headGridX < appleGridX && direction != 'L') {
            direction = 'R';
        } else if (headGridX > appleGridX && direction != 'R') {
            direction = 'L';
        } else if (headGridY < appleGridY && direction != 'U') {
            direction = 'D';
        } else if (headGridY > appleGridY && direction != 'D') {
            direction = 'U';
        }

        if(direction == 'U' && headGridX == appleGridX && headGridY < appleGridY){
            int choice = random.nextInt(2);
            direction = (choice == 1) ? 'L' : 'R';
        }

        if(direction == 'D' && headGridX == appleGridX && headGridY > appleGridY){
            int choice = random.nextInt(2);
            direction = (choice == 1) ? 'L' : 'R';
        }

        if(direction == 'R' && headGridY == appleGridY && headGridX > appleGridX){
            int choice = random.nextInt(2);
            direction = (choice == 1) ? 'D' : 'U';
        }

        if(direction == 'L' && headGridY == appleGridY && headGridX < appleGridX){
            int choice = random.nextInt(2);
            direction = (choice == 1) ? 'D' : 'U';
        }
    }

	public void newApple(){
		this.appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		this.appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        if(this.apple != null){
            this.apple.window.dispose();
        }

        this.apple = new AppleFrame(game,UNIT_SIZE, UNIT_SIZE);
        apple.window.setLocation(appleX, appleY);
        apple.window.setVisible(true);

        snakeHeadFrame.window.toFront();
        snakeHeadFrame.window.requestFocus();
	}

    public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
            SnakeBodyFrame newBody = new SnakeBodyFrame(game, UNIT_SIZE, snakeFrames.size() + 1);
            snakeFrames.add(newBody);

            snakeFrames.get(snakeFrames.size() - 1).window.setLocation(x[snakeFrames.size() - 1], y[snakeFrames.size() - 1]);
            snakeFrames.get(snakeFrames.size() - 1).window.setVisible(true);
			newApple();
		}
	}

    @Override
    public void draw(Graphics2D g2) {
        for(EntityWindow f : snakeFrames){
            f.repaint();
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void takeDamage() {
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
