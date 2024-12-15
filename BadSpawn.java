package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//extending the class Spawn to use its variables and methods
public class BadSpawn extends Spawn {
	
	//passing in the width and height
	public BadSpawn(int width, int height) {
		
		//initializing the values 
		img = new Image("file:images/backgrounds/miniTasks/catchImages/badSpider.png"); 
		ivImg = new ImageView(img); 
		
		xPos = 0;
		yPos = 0;
		
		rnd = new Random(); 
		
		bgH = height;
		bgW = width;
		
		width = (int)img.getWidth();
		height = (int)img.getHeight(); 
		
		ivImg.setPreserveRatio(true); 
		ivImg.setFitHeight(40);
	}

	@Override
	//spawning at a random x position
	public void spawning() {
		xPos = rnd.nextInt((bgW-20))+ 10; 
		yPos = -10; 
		
		ivImg.setX(xPos);
		ivImg.setY(yPos); 
		
	}

	@Override
	//returning the imageview of the spawn
	public ImageView getNode() {
		return ivImg;
	}
	
	//moving the spawn in a downward direction
	public void move() {
		yPos += 6; 
		
		ivImg.setY(yPos);
	}
}
