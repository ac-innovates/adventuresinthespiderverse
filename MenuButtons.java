package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuButtons {
	//fields
	private int xPos, yPos, rotates;
	private Image img; 
	private ImageView ivImg; 
	private double width, height;
	
	//constructor
	public MenuButtons(Image img) {
		//initializing fields
		xPos = 0; 
		yPos = 0; 
		rotates =0;
		this.img = img;
		ivImg = new ImageView(this.img); 	
		width = this.img.getWidth();
		height = this.img.getHeight(); 
	}
	
	//returning the x position
	public int getX() {
		return xPos;
	}
	
	//returning the y position
	public int getY() {
		return yPos; 
	}
	
	//returning the imageview
	public ImageView getNode() {
		return ivImg; 
	}
	
	//rotating the image
	public void move() {
		rotates += 10;
		ivImg.setRotate(rotates);
	}
	
	//setting the x position
	public void setX(int x) {
		xPos = x;
		ivImg.setX(xPos);
	}
	
	//setting the y position
	public void  setY(int y) {
		yPos = y; 
		ivImg.setY(yPos);
	}
	
	//returning the width
	public double getWidth() {
		return width;
	}
	
	//returning the height
	public double getHeight() {
		return height;
	}
	
	//setting the rotation
	public void setRotation (int r) {
		ivImg.setRotate(r);
	}
}
