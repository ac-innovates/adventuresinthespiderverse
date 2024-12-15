package application;

import java.util.Optional;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//inheriting MiniTasks
public class CollectTask extends MiniTasks {
	//fields
	AnimationTimer timer; 
	Enemy [] avoid; 
	GoodSpawn sp; 
	Image spImg, enImg; 
	Label countLeft;
	int pickUp=20; 
	
	//constructor
 	public CollectTask() {	
 		//setting values for the variables and objects
		bg = new Image("file:images/backgrounds/miniTasks/collectBg.jpg"); 
		ivBg = new ImageView(bg);
		
		spImg = new Image("file:images/backgrounds/miniTasks/spot.png"); 
		enImg = new Image("file:images/backgrounds/miniTasks/vulture.gif"); 
		
		stage = new Stage();
		root = new Pane();
		scene = new Scene(root, bg.getWidth(), bg.getHeight()); 
		
		lbl = new Label(); 
		
		
		avoid = new Enemy[3]; 
		play = new Player(4);
		play.setPlayer(4); 
		
		countLeft = new Label();
		
		sp = new GoodSpawn((int)bg.getWidth(), (int)bg.getHeight()); 		
		sp.changeImg(spImg);
        
	}
	
 	//setting up the stage and the scene, and formatting it
	public void setUp() {
		openStage(); 
		stage.setScene(scene);		
		
		//spawning the spawn
		sp.spawning((int)bg.getWidth(), (int)bg.getHeight());
		
		//formatting the counter label
		countLeft.setFont(bigtitle);
		countLeft.setText("Left to Collect: "+pickUp);
		countLeft.setTextFill(Color.WHITE);
		countLeft.setLayoutX(440);
		
		//setting the position of the player
		play.setX((int)(bg.getWidth()/2 - play.getWidth()/2));
		play.setY((int)(bg.getHeight()/2 - play.getHeight()/2));		
		
		//adding nodes to the pane
		root.getChildren().addAll(getNode(), getInstructions(), sp.getNode(), play.getNode(), countLeft); 
				
		stage.show(); 
		
		Optional <ButtonType> result = al.showAndWait(); 
		
		//introductory alert to familiarize the user
		while (true) {
            result = al.showAndWait();
            if (result.isPresent()) {
                ButtonType buttonType = result.get();
                
                //checking if the user clicked control
                if (buttonType == control) {
                    al.setContentText("Use WASD to walk up, left, down, and right.");
                }
                //checking if the user clicked instructions 
                else if (buttonType == instructions) {
                    al.setContentText("Spot's portals have been left everywhere! Collect them all while avoiding Vulture! If Vulture gets you, it is game over. There is no way to defend yourself other than avoiding contact.");
                }
                //checking if the user clicked close
                else if (buttonType == close) {
                    al.close();
                    break;
                }
            }   
            else {
                al.close();
                break;
            }
        }
		
		//formatting the enemies and adding them to the pane
		for (int i = 0; i < avoid.length; i++) {
			avoid[i] = new Enemy((int)bg.getWidth(), (int)bg.getHeight());
			avoid[i].setImage(enImg);
			avoid[i].getImage().setFitHeight(130);
			root.getChildren().add(avoid[i].getImage()); 
			avoid[i].spawnRight();
		}
		//moving the player
		movement(); 

		//spawning the enemies
		avoid[0].setX(-100);
		avoid[0].spawnLeft(); 

		//starting the timer
        timer.start();
	}
	
	//returning the Label with the instructions for the task
	public Label getInstructions() { 
		lbl.setFont(title);
		lbl.setTextFill(Color.WHITE);
		
		return lbl; 
	}
	
	public void timerMovement() { 
		//initializing the timer which moves the player
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	int valX = 0, valY=0; 
            	//boundary checking so that the player stays within the screen
            	if (left == true && play.getX() >= 0) {
            		valX -= 5; 
					play.setPlayerImage(play.LEFT);
				}
				else if (right == true && play.getX() + play.getNode().getFitWidth() < bg.getWidth()) {
            		valX += 5; 
					play.setPlayerImage(play.RIGHT);
				}
				else if (up == true && play.getY() >= 0) {
					valY -= 5;
				}
				else if (down == true  && play.getY() + play.getNode().getFitHeight() <= bg.getHeight()) {
					valY += 5; 
				}
				else {
					play.setPlayerImage(); 
				}
            	
            	//moving the player
				play.move(valX, valY);
				
				//boundary checking the enemies so they stay within the screen
				for (int i = 0; i < avoid.length; i++) {
					if (avoid[i].getX() < 0) {
						avoid[i].setDirectionX(avoid[i].EAST); 
					}
					else if (avoid[i].getX() + avoid[i].getWidth() > bg.getWidth()) {
						avoid[i].setDirectionX(avoid[i].WEST); 
					}
					
					if (avoid[i].getY() < 0) {
						avoid[i].setDirectionY(avoid[i].SOUTH); 
					}
					else if (avoid[i].getY() + avoid[i].getHeight() > bg.getHeight()) {
						avoid[i].setDirectionY(avoid[i].NORTH); 
					}
					avoid[i].move(); 
					
					if (play.getNode().getBoundsInParent().intersects(avoid[i].getImage().getBoundsInParent())) {
						timer.stop(); 
						lose = true;
						winCheck(); 
						
						stage.setWidth(ivWin.getFitWidth()); 
						stage.setHeight(ivWin.getFitHeight()); 
					}
				}
				
				//checking if the player collides with the spawn it needs to collect
				if (play.getNode().getBoundsInParent().intersects(sp.getNode().getBoundsInParent())) {
					sp.spawning((int)bg.getWidth(), (int)bg.getHeight());
					pickUp--;
					countLeft.setText("Left to Collect: "+pickUp);
					
					//checking if the user picked up all the spawns
					if (pickUp <= 0) {
						timer.stop();
						winner = true;
						winCheck(); 
					}
				}
            }
        };
	}
}
