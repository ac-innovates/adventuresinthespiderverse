package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Enemy {

	// Creating fields
	private Random rnd;
	private int dirX, dirY, rand;
	private int index, xPos, yPos, speed, yspeed;
	private Image imgEnemy;
	private ImageView ivEnemy;
	public final int NORTH = 1, EAST = 3, SOUTH = 2, WEST = 4;
	public boolean isAlive;
	public Rectangle theMask;

	// Creating constructor
	public Enemy(int bgWidth, int bgHeight) {

		// Giving default values to variables
		rnd = new Random();
		
		//sending random values 
		dirX = rnd.nextInt(2) + 3;
		dirY = rnd.nextInt(2) + 1;
		index = rnd.nextInt(4);
		xPos = rnd.nextInt(bgWidth-10) + 20;
		yPos = rnd.nextInt(bgHeight-10) + 20;
		speed = 2;
		yspeed = 2;
		
		rand = rnd.nextInt(3) + 1;
		
		//initializing and formatting the image
		imgEnemy = new Image("file:images/backgrounds/miniTasks/shootImages/doc " + rand + ".png");
		ivEnemy = new ImageView(imgEnemy);		
		ivEnemy.setPreserveRatio(true);	
		ivEnemy.setFitWidth(120);
		ivEnemy.setFitHeight(157);
		
		isAlive = true;

		theMask = new Rectangle(xPos + 15, yPos + 15, 70, 120);

	}

	// Creating methods
	public void setX(int x) {
		xPos = x;
		ivEnemy.setX(xPos);
	}

	//setting the y position 
	public void setY(int y) {
		yPos = y;
		ivEnemy.setY(yPos);
	}

	//returning the x position
	public int getX() {
		return xPos;
	}

	//returning the y position
	public int getY() {
		return yPos;
	}

	//returning the width of the enemy 
	public double getWidth() {
		return ivEnemy.getFitWidth();
	}

	//returning the height of the enemy 
	public double getHeight() {
		return ivEnemy.getFitHeight();
	}

	//setting the location of the enemy 
	public void setLocation(int x, int y) {
		xPos = x;
		yPos = y;

		ivEnemy.setX(xPos);
		ivEnemy.setY(yPos);

	}

	//setting the direction randomly 
	public void setDirRandom() {
		int num = rnd.nextInt(2);
		
		if (dirY == 0) {
			dirY = NORTH;
		}
		else if (dirY == 1) {
			dirY = SOUTH;
		}

		if (dirX == 0) {
			dirX = EAST;
		}
		else {
			dirX = WEST;
		}

	}

	//returning the x direction
	public int getDirectionX() {
		return dirX;
	}
	
	//returning the y direction
	public int getDirectionY() {
		return dirY;
	}

	//setting the direction in the x
	public void setDirectionX(int dir) {
		dirX = dir;
	}

	//setting the direction in the y
	public void setDirectionY(int dir) {
		dirY = dir;
	}

	//moving the enemy
	public void move() {

		if (isAlive == true) {
			//changing the movement based on the direction
			if (dirX == WEST) {
				xPos = (xPos - speed);
			}
			else if (dirX == EAST) {
				xPos = (xPos + speed);
			}

			if (dirY == NORTH) {
				yPos -= yspeed;
			}
			else if (dirY == SOUTH) {
				yPos += yspeed;
			}
		}
		
		//setting the position of the enemy
		ivEnemy.setX(xPos);
		ivEnemy.setY(yPos);
		
		//updating the position of the mask
		updateMaskPosition(); 

	}

	//returning the imageview
	public ImageView getImage() {
		return ivEnemy;

	}
	
	//setting the image of the enemy
	public void setImage(Image img) {
		imgEnemy = img; 
		ivEnemy.setImage(imgEnemy);
	}

	//checking if the enemy has been killed
	public void killed() {
		isAlive = false;
	}

	//checking if the enemy is alive
	public boolean isAlive() {
		return isAlive;
	}
	
	//upating the position of the mask
	private void updateMaskPosition() {
		theMask.setX(xPos + 15);
		theMask.setY(yPos + 15);
	}

	//mask of the enemy 
	public Rectangle mask() {
		updateMaskPosition(); 
		return theMask; 
	}
	
	//spawning the enemy from the right
	public void spawnRight() {
		xPos = 1100; 
		yPos = rnd.nextInt(350) + 50; 
		
		dirX = WEST;
		dirY = SOUTH; 
	}
	
	//spawning the enemy from the left
	public void spawnLeft() {
		xPos = -200; 
		yPos = rnd.nextInt(350) + 50; 
		
		dirX = EAST;
		dirY = SOUTH; 
	}
	//setting the speed of the enemy 
	public void setSpeed(int sp) {
		speed += sp;
		yspeed += (sp - 0.25); 
	}
}
