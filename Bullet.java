package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet {
	//fields
	private Image imgRight, imgLeft; 
	private ImageView iviewBullet; 
	public boolean fired;
	private double xPos, yPos, width, height;
	private int dir; 
	public final static int EAST=0, WEST=1;
	
	//constructor
	public Bullet() {
		//setting default values
		imgRight = new Image("file:images/backgrounds/miniTasks/shootImages/webRight.png");
		imgLeft = new Image("file:images/backgrounds/miniTasks/shootImages/webLeft.png");
		iviewBullet = new ImageView(imgRight);
		fired=false;
		xPos=-100;
		yPos=-100;
		dir=EAST;
		
		//formatting the imageview
		iviewBullet.setPreserveRatio(true);
		iviewBullet.setFitWidth(100);
		
		width = iviewBullet.getFitWidth();
		height = iviewBullet.getFitHeight();
	}
	
	//methods 
	
	//returning the direction
	public int getDirection() {
		return dir;
	}
	
	//returning the width
	public double getWidth() {
		return width;
	}
	
	//returning the height
	public double getHeight() {
		return height;
	}
	
	//returning the x position
	public double getX() {
		return xPos;
	}
	
	//returning the y position
	public double getY() {
		return yPos;
	}
	
	//checking if the bullet was fired 
	public boolean isFired() {
		return fired;
	}
	
	//setting the direction
	public void setDir(int d) {
		dir = d; 
	}
	
	//setting the image based on direction
	public void setImage(int dir) { 
		this.dir = dir;
		
		if (this.dir == EAST) {
			iviewBullet.setImage(imgRight);
		}
		else if (this.dir == WEST) {
			iviewBullet.setImage(imgLeft);
		}
	}
	
	//moving the bullet based on direction
	public void move() {
		if (dir == EAST) {
			xPos += 10; 
		}
		else {
			xPos -= 10; 
		}
		iviewBullet.setX(xPos);
	}
	
	//setting the position of the bullet
	public void setPosition(double playerX, double playerY, int dir) {
		this.dir = dir; 
		if (this.dir == EAST) {
			xPos = playerX + 75; 
		}
		else {
			xPos = playerX - 75; 
		}
		
		yPos = playerY + 40; 
		
		iviewBullet.setX(xPos);
		iviewBullet.setY(yPos);
	}
	
	//setting the x position
	public void setX(double x) {
		xPos = x; 
		iviewBullet.setX(xPos);
	}
	
	//setting the y position
	public void setY(double y) {
		yPos = y; 
		iviewBullet.setY(yPos);
	}
	
	//stopping the bullet
	public void stopBullet() {
		fired = false; 
	}
	
	//checking if the bullet is off the screen
	public boolean isOffScreen(double edge) {
		boolean offScreen = false; 
		if (xPos >= edge || xPos + width <= 0) {
			offScreen = true;
			fired = false;
		}
		else {
			offScreen = false;
		}
		return offScreen; 
	}
	
	//getting the imageview
	public ImageView getNode() {
		iviewBullet.setPreserveRatio(true);
		iviewBullet.setFitHeight(50); 
		return iviewBullet; 
	}
}
