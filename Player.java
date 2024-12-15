package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Player {
	//fields
	private int xPos, yPos,level;
	private double width, height; 
	private Image walkLeft, walkRight, attackLeft, attackRight; 
	private ImageView ivImg; 
	public int xDir, yDir;
	public final int UP=0, LEFT=1, DOWN=2, RIGHT=3; 
	public boolean attacking;
	private Rectangle theMask; 
	
	//constructor
	public Player(int character) {
		//initializing the position
		xPos = 0; 
		yPos = 0;
		
		//initializing the images of the player
		walkLeft = new Image("file:images/players/milesWalkLeft.gif"); 
		walkRight = new Image("file:images/players/milesWalkRight.gif"); 
		attackLeft = new Image("file:images/players/milesAttackLeft.gif");
		attackRight = new Image("file:images/players/milesAttackRight.gif");
		
		ivImg = new ImageView(walkRight); 
		xDir = RIGHT;
		
		this.level = character; 
		
		//initializing the width and height of the player
		width = walkRight.getWidth();
		height = walkRight.getHeight(); 
		
		//initializing the mask 
		theMask = new Rectangle(xPos + 10, yPos + 10, 50, 50); 
		
		attacking = false;
		
		//setting the x and y position of the image
	    ivImg.setX(xPos);
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
	
	//returning the width
	public double getWidth() {
		return width;
	}
	
	//returning the height
	public double getHeight() {
		return height; 
	}
	
	//returning the image
	public ImageView getNode() {

		ivImg.setPreserveRatio(true);

		//formatting the image based on the level
		if (level == 1){
			ivImg.setFitWidth(108);
			ivImg.setFitHeight(72); 
			
			if (attacking) {
				ivImg.setFitHeight(150);
				ivImg.setFitWidth(149);
			}
		}
		else if (level == 2) {
			ivImg.setFitHeight(80); 
			ivImg.setFitWidth(79.2);
			
			if (attacking) {
				ivImg.setFitHeight(150);
				ivImg.setFitWidth(149);
			}
		}
		else if (level == 3) {
			ivImg.setFitWidth(70);
			ivImg.setFitHeight(91);
			
			if (attacking) {
				ivImg.setFitWidth(87); 
				ivImg.setFitHeight(100);
			}
		}
		
		
		
		return ivImg; 
	}
	
	//setting the player's images based on the level
	public void setPlayer(int player) { 
		level = player;
		
		//setting Miles images for level 1
		if (level == 1) {		
			walkLeft = new Image("file:images/players/milesWalkLeft.gif"); 
			walkRight = new Image("file:images/players/milesWalkRight.gif"); 
			attackLeft = new Image("file:images/players/milesAttackLeft.gif");
			attackRight = new Image("file:images/players/milesAttackRight.gif");
			
			ivImg.setFitWidth(108);
			ivImg.setFitHeight(72); 
		}
		//setting Peter's images for level 2
		else if (level == 2) {
			walkLeft = new Image("file:images/players/peterWalkLeft.gif"); 
			walkRight = new Image("file:images/players/peterWalkRight.gif"); 
			attackLeft = new Image("file:images/players/peterPunchLeft.gif");
			attackRight = new Image("file:images/players/peterPunchRight.gif"); 
			
			ivImg.setFitHeight(80); 
			ivImg.setFitWidth(79.2);		
		}
		//setting Gwen's images for level 3
		else if (level == 3) {
			walkLeft = new Image("file:images/players/gwenWalkLeft.gif");
			walkRight = new Image("file:images/players/gwenWalkRight.gif"); 
			attackLeft = new Image("file:images/players/gwenAttackLeft.gif");
			attackRight = new Image("file:images/players/gwenAttackRight.gif");
			
			ivImg.setFitWidth(70);
			ivImg.setFitHeight(91);

		}
		//setting Miguel's images for level 4
		else if (level == 4) {
			walkLeft = new Image("file:images/players/miguelWalkLeft.gif");
			walkRight = new Image("file:images/players/miguelWalkRight.gif"); 
			attackLeft = new Image("file:images/players/miguelShootLeft.gif");
			attackRight = new Image("file:images/players/miguelShootRight.gif");
			
			ivImg.setFitWidth(70);
			ivImg.setFitHeight(91);
		}
		ivImg.setImage(walkRight);
	}
	
	//returning the direction in the x
	public int getXDirection() {
		return xDir; 
	}
	
	//returning the direction in the y
	public int getYDirection() {
		return yDir; 
	}
	
	//setting the y direction
	public void setYDirection(int y) {
		yDir = y;
	}
	
	//setting the x direction
	public void setXDirection(int x) {
		xDir = x;
	}
	
	//setting the image of the player based on the direction it is going in 
	public void setPlayerImage(int xDirection) {
		xDir = xDirection;
		//setting the images pointing left
		if (xDir == LEFT) {
			//setting the image based on if they are attacking
			if (attacking) {
				ivImg.setImage(attackLeft);
			}
			else {
				ivImg.setImage(walkLeft);
			}
		}
		//setting the images pointing right
		else {
			//setting the image based on if they are attacking
			if (attacking) {
				ivImg.setImage(attackRight);
			}
			else {
				ivImg.setImage(walkRight);
			}
		}
	}
	
	//setting the level
	public void setLevel(int lev) {
		level = lev; 
	}
	//setting the player image without the direction being passed in
	public void setPlayerImage() {
		if (xDir == LEFT) {
			if (attacking) {
				if (level == 1) {
					ivImg.setFitHeight(1000); 
				}

				ivImg.setImage(attackLeft);
			}
			else {
				ivImg.setImage(walkLeft);
			}
		}
		else {
			if (attacking) {
				ivImg.setImage(attackRight);
			}
			else {
				ivImg.setImage(walkRight);
			}
		}

	}
	
	//moving the image
	public void move(int x, int y) {
		//changing the size if the level is 1
		if (level == 1) {
			ivImg.setFitWidth(108);
			ivImg.setFitHeight(72); 

		}
		//changing the size if the level is 2
		if (level == 2) {
			if (attacking) {
				ivImg.setFitHeight(150);
				ivImg.setFitWidth(149);
			}
			else {
				ivImg.setFitHeight(80); 
				ivImg.setFitWidth(79.2);
			}
		}
		//changing the size of the level is 3
		if (level == 3) {
			ivImg.setFitWidth(70);
			ivImg.setFitHeight(91);			
		}
		
		//moving the x and y based on the value passed in 
		xPos += x;
		yPos += y;
		ivImg.setX(xPos);
		ivImg.setY(yPos);
		//updating the mask's position
		updateMaskPosition();
        
    }
	
	//updating the position of the mask of the image
    private void updateMaskPosition() {
        theMask.setX(xPos + 15);
        theMask.setY(yPos + 15);
    }
    
    //returning the mask of the image 
    public Rectangle mask() {
        updateMaskPosition(); 
        return theMask; 
    }

	
}
