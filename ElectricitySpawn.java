package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//inheriting spawn
public class ElectricitySpawn extends Spawn{
	//fields
	private int dirY, dirX, speed; 
	private final int UP=0, LEFT=2, DOWN=1, RIGHT=3; 
	private boolean isAlive;
	private Image imgBlue;

	//constructor
	public ElectricitySpawn() {
		//initializing variables
		img = new Image("file:images/backgrounds/miniTasks/electricity.png"); 
		ivImg = new ImageView(img); 
		
		imgBlue = new Image("file:images/backgrounds/miniTasks/blueElec.png");
		
		rnd = new Random(); 
		width = (int) img.getWidth();
		height = (int) img.getHeight(); 
		isAlive = true;
		
		yPos = rnd.nextInt(300); 
		
		speed = rnd.nextInt(8) + 3;
		dirX = rnd.nextInt(2) + 2;
		dirY = rnd.nextInt(2);
		
	}
	
	//moving the spawn based on the direction
	public void move() {
		if (isAlive == true) {
			if (dirX == LEFT) {
				xPos -= speed;
			}
			else if (dirX == RIGHT) {
				xPos += speed;
			}

			if (dirY == UP) {
				yPos -= speed;
			}
			else if (dirY == DOWN) {
				yPos += speed;
			}
		}
		
		//setting the x and y position of the imageview
		ivImg.setX(xPos);
		ivImg.setY(yPos);

	}

	@Override
	//spawning randomly on the y axis
	public void spawning() {
		yPos = rnd.nextInt(600) + 10; 
		
		ivImg.setY(yPos);
	}
	
	//returning the x position
	public int getX() {
		return xPos;
	}
	
	//returning the y position
	public int getY() {
		return yPos;
	}

	@Override
	//returning the imageview
	public ImageView getNode() {
		return ivImg;
	}

	//setting the direction in the x
	public void setDirectionX(int dir) {
		dirX = dir; 
	}
	
	//setting the direction in the y
	public void setDirectionY(int dir) {
		dirY = dir; 
	}
	
	//returning the width
	public int getWidth() {
		return width;
	}
	
	//returning the height
	public int getHeight() {
		return height; 
	}
	
	//setting the x position
	public void setX(int x) {
		xPos = x;
		ivImg.setX(xPos); 
	}
	
	//setting the y position
	public void setY(int y) {
		yPos = y;
		ivImg.setY(yPos); 
	}
	
	//moving the blue electricity
	public void moveBlue() {
		ivImg.setImage(imgBlue);
		
		xPos -= 5;
		ivImg.setX(xPos);
	}
	

}
