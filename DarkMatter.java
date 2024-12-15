package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//inheriting the background because the darkmatter needs to move when the background moves
public class DarkMatter extends Background{
	
	//fields
	public int val;
	private int xPos, yPos, bgH, bgW, level, location; 
	private Image img, enter;
	private ImageView ivImg; 
	private Random rnd;
	
	//constructor
	public DarkMatter(int bgWidth, int bgHeight, int val, int lev) {
		
		//setting default values 
		this.val += val; 
		xPos = this.val; 
		yPos = 0;
		
		location = 290; 
		
		level = lev; 
		
		enter = new Image("file:images/darkMatter/enter.png"); 
		
		if (level == 4) {
			location = 340; 
		}

		ivImg = new ImageView(img);
		ivImg.setImage(img); 

		rnd = new Random();
		bgH = bgHeight;
		bgW = bgWidth;
	}

	//spawning the dark matter in their correct spot
	public void spawning(int level) {	
		this.level = level;
		
		ivImg.setPreserveRatio(true);
		ivImg.setFitHeight(100);
		ivImg.setFitWidth(100);
				
		ivImg.setX(val);
		ivImg.setY(location);
		
		if (this.level == 1) {
			location = 290;
		}
		else if (this.level == 2) {
			location = 210; 
		}
	}

	//returning the image of the dark matter
	public ImageView getNode() {				
		return ivImg;
	}
	
	//moving the dark matter
	public void move() {
		if (xDir == RIGHT) {
			this.xPos += 5; 
			this.ivImg.setX(this.xPos);
		}
		else if (xDir == LEFT) {
			this.xPos -= 5;
			this.ivImg.setX(this.xPos);
		}

	}
	
	//switching the dark matter image to enter when the player collides with it
	public void imgSwitch(boolean collides) {
		if (collides) {
			ivImg.setImage(enter);
			ivImg.setY(location + 20);
		}
		else {
			ivImg.setImage(img);
			ivImg.setY(location);
		}
	}
	
	//setting the x position
	public void setX(int x) {
		this.xPos = x;
		ivImg.setX(this.xPos);
	}
	
	//setting the y position
	public void setY(int y) {
		this.yPos = y; 
		ivImg.setY(this.yPos);
	}
	
	//setting the image based on the level
	public void setImage(int lev) {
		level = lev; 
		
		//changing the value of img based on the level
		if (level == 1) {
			img = new Image("file:images/darkMatter/dmMiles.png"); 
		}
		else if (level == 2) {
			img = new Image("file:images/darkMatter/dmPeter.png"); 
		}
		else if (level == 3) {
			img = new Image("file:images/darkMatter/dmGwen.png"); 
		}
		else if (level == 4) {
			img = new Image("file:images/darkMatter/dmMiguel.png"); 
		}
		
		//setting the imageview to the changed image
		ivImg.setImage(img);
	}

}
