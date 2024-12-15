package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Background {
	//fields
	protected int xPos, yPos, width, height, levelBg=1;
	protected Image imgOne, imgTwo, imgThree, imgFour, imgFive, imgSix, imgSeven, imgEight; 
	protected ImageView ivOne, ivTwo;
	protected int yDir, xDir;
	public final int UP=0, LEFT=1, DOWN=2, RIGHT=3;
	
	//constructor
	public Background() {
		//initializing background image variables
		imgOne = new Image("file:images/backgrounds/levelOneBg.jpg"); 
		imgTwo = new Image("file:images/backgrounds/levelOneBg2.jpg"); 
		imgThree = new Image("file:images/backgrounds/levelTwoBg.jpg"); 
		imgFour = new Image("file:images/backgrounds/levelTwoBg2.png"); 
		imgFive = new Image("file:images/backgrounds/levelThreeBG.png"); 
		imgSix = new Image("file:images/backgrounds/levelThreeBG2.png"); 
		imgSeven = new Image("file:images/backgrounds/levelFourBg.jpg");
		imgEight = new Image("file:images/backgrounds/levelFourBg2.png"); 
		
		//setting default values
		xPos = 0;
		yPos = 0;
		
		width = (int) imgOne.getWidth(); 
		height = (int) imgOne.getHeight(); 
		
		ivOne = new ImageView(imgThree); 
		ivTwo = new ImageView(imgFour); 
		
		yDir = UP; 
		xDir = RIGHT;
	}
	
	//setting the x position
	public void setX(int x) {
		xPos = x; 
		ivOne.setX(xPos);
		ivTwo.setX(xPos + ivOne.getFitWidth());
		
	}
	
	//setting the y position
	public void setY(int y) {
		yPos = y;
		ivOne.setY(yPos);
		ivTwo.setY(yPos);
	}
	
	//returning the x position
	public int getX() {
		return xPos;
	}
	
	//returning the y position
	public int getY() {
		return yPos; 
	}
	
	//returning the width of the background
	public int getWidth() {
		changeImage(); 
		
		width = (int) (ivOne.getFitWidth() + ivOne.getFitWidth()); 
		return width;
	}
	
	//returning the width of just one of the images in the background 
	public int oneWidth() {
		changeImage(); 
		
		width = (int) (ivOne.getFitWidth()); 
		return width;
	}
	
	//returning the height of the background
	public int getHeight() {
		changeImage(); 

		height = (int)(ivOne.getFitHeight()); 
		return height; 
	}
	
	//moving the background (scrolling background)
	public void move() {
		if (yDir == UP) { 
			yPos -= 5; 
			ivOne.setY(yPos);
			ivTwo.setY(yPos);

		}
		else if (yDir == DOWN) {
			yPos += 5; 
			ivOne.setY(yPos);
			ivTwo.setY(yPos);
		}
		
		if (xDir == LEFT) {
			xPos -= 5;
			ivOne.setX(xPos);
			ivTwo.setX(xPos + ivOne.getFitWidth());
		}
		else if (xDir == RIGHT) {
			xPos += 5; 
			ivOne.setX(xPos);
			ivTwo.setX(xPos + ivOne.getFitWidth());
		}
	}
	
	//setting the direction in the y
	public void setYDirection(int y) {
		yDir = y; 
	}
	
	//setting the direction in the x
	public void setXDirection(int x) {
		xDir = x; 
	}

	//returning the imageview of the first image of the background
	public ImageView getNodeOne() {
		ivOne.setPreserveRatio(true);
		
		return ivOne;
	}
	
	//returning the imageview of the second image of the background
	public ImageView getNodeTwo() {
		ivTwo.setPreserveRatio(true);

		return ivTwo;
	}
	
	//changing the background image based on the level 
	public void changeImage(int level) {
		levelBg = level; 
		
		if (levelBg == 2) {
			ivOne.setFitHeight(400); 
			ivOne.setFitWidth(800);
			
			ivTwo.setFitHeight(400); 
			ivTwo.setFitWidth(800);
		}
		if (levelBg == 1) {
			ivOne.setImage(imgThree);
			ivTwo.setImage(imgFour); 
			
			ivOne.setFitHeight(422); 
			ivOne.setFitWidth(960);
			
			ivTwo.setFitHeight(422); 
			ivTwo.setFitWidth(960);
		}
		else if (levelBg == 3) {
			ivOne.setImage(imgFive);
			ivTwo.setImage(imgSix);
			
			ivOne.setFitHeight(408); 
			ivOne.setFitWidth(728);
			
			ivTwo.setFitHeight(408); 
			ivTwo.setFitWidth(728);
		}
		else if (levelBg == 4) {
			ivOne.setImage(imgSeven);
			ivTwo.setImage(imgEight); 
			
			ivOne.setFitHeight(500); 
			ivOne.setFitWidth(800);
			
			ivTwo.setFitHeight(500); 
			ivTwo.setFitWidth(800);
		}
	}
	
	//changing image of level 2
	public void changeImage() {
		if (levelBg == 2) {
			ivOne.setImage(imgOne);
			ivTwo.setImage(imgTwo); 
			
			ivOne.setFitHeight(351); 
			ivOne.setFitWidth(626);
			
			ivTwo.setFitHeight(351); 
			ivTwo.setFitWidth(626);
		}
	}	
}
