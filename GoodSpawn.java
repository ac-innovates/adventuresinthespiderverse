package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//extending Spawn
public class GoodSpawn extends Spawn{
	public GoodSpawn(int width, int height) {
		//initializing the variables from Spawn
		img = new Image("file:images/backgrounds/miniTasks/catchImages/goodSpider.png"); 
		ivImg = new ImageView(img); 
		
		xPos = 0;
		yPos = 0;
		
		rnd = new Random(); 
		
		bgH = height;
		bgW = width;
		
		width = (int)img.getWidth();
		height = (int)img.getHeight(); 
		
		//formatting the image
		ivImg.setPreserveRatio(true); 
		ivImg.setFitHeight(40);
	}

	@Override
	//spawning at a random x position
	public void spawning() {
		xPos = rnd.nextInt((bgW-20))+ 10; 
		yPos = -10; 
		
		//setting the image's x and y position to their new value
		ivImg.setX(xPos);
		ivImg.setY(yPos); 		
	}
	
	//spawning based on the width and height of the background
	public void spawning(int width, int height) {
		bgW = width;
		bgH = height;
		
		//randomly setting the x and y position
		xPos = rnd.nextInt((bgW-40))+ 30; 
		yPos = rnd.nextInt((bgH-40)) + 30; 
		
		ivImg.setX(xPos);
		ivImg.setY(yPos); 		
	}

	@Override
	//returning the imageview 
	public ImageView getNode() {
		return ivImg;
	}
	
	//moving the image in a downward direction
	public void move() {
		yPos += 4; 
		
		ivImg.setY(yPos);
	}
	
	//changing the image of the spawn
	public void changeImg (Image im) { 
		img = im; 
		ivImg.setImage(img);
	}

}
