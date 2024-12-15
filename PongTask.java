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

//extending MiniTasks
public class PongTask extends MiniTasks {
	//fields
	private Enemy en;
	private AnimationTimer timer; 
	private Image pad, gob;
	private ImageView ivPad;
	private int counter;
	
	//constructor
	public PongTask() {
		//initializing the fields
		bg = new Image("file:images/backgrounds/miniTasks/pongBg.jpg"); 
		ivBg = new ImageView (bg); 
		
		gob = new Image("file:images/backgrounds/miniTasks/goblin.png");
		
		//formatting the background
		ivBg.setPreserveRatio(true);
		ivBg.setFitWidth(960); 
		ivBg.setFitHeight(409);
		
		pad = new Image("file:images/backgrounds/miniTasks/red_paddle.png"); 
		ivPad = new ImageView(pad); 
		
		//preparing the stage
		stage = new Stage(); 
		root = new Pane();
		scene = new Scene(root, ivBg.getFitWidth(), ivBg.getFitHeight()); 
		
		play = new Player(4); 
		play.setX(0); 
		
		en = new Enemy((int)ivBg.getFitWidth(), (int)ivBg.getFitHeight()); 
		en.setX(-100); 
		
		lbl = new Label(); 
		
	}
	
	//setting up the task
	public void setUp() {
		//opening the stage
		openStage(); 
		
		//setting the scene to the stage
		stage.setScene(scene);
		
		//setting the player
		play.setPlayer(4);
		play.setX(0); 
		
		//spawning the enemy
		en.spawnRight(); 
		en.setImage(gob); 
		
		//adding the nodes to the pane
		root.getChildren().addAll(getNode(), getInstructions(), play.getNode(), ivPad); 
		
		//showing the stage
		stage.show(); 
				
		root.getChildren().add(en.getImage()); 
		
		//showing the alert
		getAlert();
		
		//moving the player
		movement();
		
		//starting the timer
		timer.start();
	}
	
	//instructions for the level
	public Label getInstructions() {		
		lbl.setFont(title);
		lbl.setTextFill(Color.WHITE);
		lbl.setText("Green Goblin is trying to enter another universe! Keep him away by blocking him with the shield."); 

		return lbl; 
	}
	
	//movement of the player
	public void movement() {
		scene.setOnKeyPressed(new EventHandler <KeyEvent> () { 
			public void handle(KeyEvent e) {
				//checking which key the user has pressed
				if (e.getCode() == KeyCode.W) {
		            up = true;
		        }
				else if (e.getCode() == KeyCode.S) {
		            down = true;
		        } 
			}
		}); 
		
		scene.setOnKeyReleased(new EventHandler <KeyEvent> () { 
			public void handle(KeyEvent e) {
				//checking which key the user has released
				if (e.getCode() == KeyCode.W) {
		            up = false;
		        } else if (e.getCode() == KeyCode.S) {
		            down = false;
		        }
			}
		});
	}
	
	//introductory alert
	public void getAlert() {
		Optional <ButtonType> result = al.showAndWait(); 
		
		//showing alert with different options
		while (true) {
            result = al.showAndWait();
            if (result.isPresent()) {
                ButtonType buttonType = result.get();
                //checking if the user clicked the control button
                if (buttonType == control) {
                    al.setContentText("Use W and S to go up and down.");
                }
                //checking if the user clicked the instruction button
                else if (buttonType == instructions) {
                    al.setContentText("Green Goblin is trying to go into a different universe! Stop him by using the shield to block him. Be careful! The more he is hit, the faster he goes.");
                }
                //checking if the user clicked the close button
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
	}
	
	//moving with the timer
	public void timerMovement() { 
		timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	int valX = 0, valY=0; 
            	//boundary checking so the player can only move within the screen
            	if (up == true && play.getY() >= 10) {
					valY -= 5;
				}
            	else if (down == true  && play.getY() + play.getNode().getFitHeight() <= ivBg.getFitHeight()) {
					valY += 5; 
				}
            	//setting the position of the paddle
            	ivPad.setY(play.getY()); 
            	ivPad.setX(play.getX() + 60);
            	
            	//moving the player
				play.move(valX, valY);	
				
				//moving the enemy
				en.move(); 
				
				//checking if the player missed the enemy and it has hit the wall
				if (en.getX() < 0) {
					//showing the lose screen
					lose = true;
					winCheck(); 
					en.setX(2000);
					timer.stop(); 
					
					//setting the size of the stage to the lose screen
					stage.setWidth(ivWin.getFitWidth()); 
					stage.setHeight(ivWin.getFitHeight()); 
				}
				//changing the direction of the enemy if it hits the wall
				else if (en.getX() + en.getWidth() > ivBg.getFitWidth()) {
					en.setDirectionX(en.WEST); 
				}
				
				//changing the direction of the enemy if it hits the top of screen
				if (en.getY() < 0) {
					en.setDirectionY(en.SOUTH); 
				}
				//changing the direction of the enemy if it hits the bottom of screen
				else if (en.getY() + en.getHeight() > ivBg.getFitHeight()) {
					en.setDirectionY(en.NORTH); 
				}
				
				//checking if the paddle hits the enemy
				if (ivPad.getBoundsInParent().intersects(en.getImage().getBoundsInParent())) {
					//increasing counter, increasing the speed of the enemy, and changing the direction of the enemy
					counter++; 
					en.setSpeed(1);
					en.setDirectionX(en.EAST);
					
					//checking if the enemy has been hit 10 times indicating the user has won
					if (counter >= 10) {
						timer.stop();
						winner = true;
						winCheck();
					}
				}
				
            }
        };
	}
}
