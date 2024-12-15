package application;

import java.util.ArrayList;
import java.util.Optional;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
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
import javafx.util.Duration;

//inheriting MiniTasks
public class ElectroTask extends MiniTasks {
	//fields
 	private Image electro; 
	private Image [] hearts; 
	private ImageView [] ivHearts;
	private ImageView ivElectro; 
	private final int UP=0, LEFT=2, DOWN=1, RIGHT=3; 
	private int xDir, yDir, elecCount=0, countDown=2; 
	private ArrayList <ElectricitySpawn> es; 
	private ElectricitySpawn blue; 
	private Timeline spawn, zap, totalCountDown; 
	private AnimationTimer elecTimer, zapTimer, timer;
	private int countdown=30;
	private Label countShow;
	
	//constructor
	public ElectroTask() {
		//setting default values
		stage = new Stage(); 
		bg = new Image("file:images/backgrounds/miniTasks/electroBg.jpg"); 
		ivBg = new ImageView(bg);
		root = new Pane(); 
		
		blue = new ElectricitySpawn(); 
		
		hearts = new Image [3]; 
		ivHearts = new ImageView [3]; 
		
		//formatting the label for the countdown
		countShow = new Label();
		countShow.setFont(title); 
		countShow.setTextFill(Color.WHITE);
		countShow.setLayoutX(850);
		countShow.setText(""+countdown);

		//formatting the hearts
		for (int i = 0; i < hearts.length; i++) {
			hearts[i] = new Image("file:images/backgrounds/miniTasks/heart.png"); 
			ivHearts[i] = new ImageView(hearts[i]); 
			ivHearts[i].setPreserveRatio(true); 
			ivHearts[i].setFitHeight(30);
		}

		//setting the position of the hearts
		ivHearts[0].setLayoutX(720); 
		ivHearts[1].setLayoutX(760); 
		ivHearts[2].setLayoutX(800); 

		//formatting the background
		ivBg.setPreserveRatio(true); 
		ivBg.setFitHeight(500); 
		ivBg.setFitWidth(893); 
		
		//formatting the blue electricity
		blue.setX(-1000);
		blue.getNode().setFitHeight(70); 
		blue.getNode().setPreserveRatio(true); 
		
		play = new Player(2);
		play.setPlayer(2); 
		
		es = new ArrayList <ElectricitySpawn> (); 
		
		//setting the position of the player
		play.setX(760); 
		play.setY(400); 
		
		electro = new Image("file:images/backgrounds/miniTasks/electro.gif"); 
		ivElectro = new ImageView(electro); 

		scene = new Scene(root, ivBg.getFitWidth(), ivBg.getFitHeight());	
	}
	
	//setting up the stage and scene, and formatting it
	public void setUp() {
		openStage();
		
		//adding the necessary nodes
		root.getChildren().addAll(ivBg, ivElectro, play.getNode(), ivHearts[0], ivHearts[1], ivHearts[2], countShow, blue.getNode()); 
		
		stage.setScene(scene);
		stage.show();
		
		getAlert(); 
		//moving the player
		movement(); 

		//starting the timer and spawning the electricity
		timer.start();
	}
	
	//spawning the spawns
	public void spawn() {
		
		//spawning every 3 seconds
		KeyFrame elec = new KeyFrame(Duration.millis(3000), new EventHandler <ActionEvent>() {
			public void handle(ActionEvent e) {
				elecTimer.start(); 
                ElectricitySpawn newSpawn = new ElectricitySpawn();
                es.add(newSpawn); 
                root.getChildren().addAll(newSpawn.getNode());
			}
		});
		
		//timeline for keyframe
		spawn = new Timeline(elec); 
		spawn.setCycleCount(Timeline.INDEFINITE); 
		spawn.play(); 
		
		//timer for the electricity
		elecTimer = new AnimationTimer() {
			public void handle(long now) {
				//keeping the electricity spawns within the boundary of the screen
				for (int i = 0; i < es.size(); i++) {
					//moving the spawns depending on the direction
					if (es.get(i).getX() < 0) {
						es.get(i).setDirectionX(RIGHT);
					}
					else if (es.get(i).getX()  + es.get(i).getWidth() > ivBg.getFitWidth()) {
						es.get(i).setDirectionX(LEFT); 
					}
					
					if (es.get(i).getY() < 0) {
						es.get(i).setDirectionY(DOWN); 
					}
					else if (es.get(i).getY()  + es.get(i).getHeight() > ivBg.getFitHeight()) {
						es.get(i).setDirectionY(UP);
					}
					//moving the spawns
					es.get(i).move(); 
				}
			}
		};
		//starting the timer
		elecTimer.start(); 
		
		//spawning the blue electricity every 8 seconds
		KeyFrame blueZap = new KeyFrame(Duration.millis(8000), new EventHandler <ActionEvent> () {
			public void handle(ActionEvent e) {
				blue.spawning(); 
				blue.setX((int)ivBg.getFitWidth());
				zapTimer.start(); 
			}
		});
		
		//timeline for the blue electricity
		zap = new Timeline(blueZap); 
		zap.setCycleCount(Timeline.INDEFINITE); 
		zap.play(); 
		
		//countdown for 30 seconds 
		KeyFrame fullTime = new KeyFrame(Duration.millis(1000), new EventHandler <ActionEvent> () {
			public void handle(ActionEvent e) {
				countdown--; 
				countShow.setText(""+countdown);
				//checking if the user makes it to the full 30 seconds indicating they won
				if (countdown <= 0) {
					winner = true;
					winCheck(); 
					
					//stopping all timers
					zap.stop();
					elecTimer.stop();
					spawn.stop(); 
					zapTimer.stop();
					timer.stop();
					totalCountDown.stop();
				}
			}
		}); 
		
		//timeline for keyframe
		totalCountDown = new Timeline(fullTime); 
		totalCountDown.setCycleCount(Timeline.INDEFINITE);
		totalCountDown.play(); 
		
		//timer for the blue electricity 
		zapTimer = new AnimationTimer() {
			public void handle(long val) {
				//moving the blue electricity
				blue.moveBlue(); 
			}
		};
		
	}
	
	public void timerMovement() { 
		//initializing the timer
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	int valX = 0, valY=0; 
            	//moving the player left, right, up and down within the boundary 
            	if (left == true && play.getX() >= 0) {
            		valX -= 5; 
					play.setPlayerImage(play.LEFT);
				}
				else if (right == true && play.getX() + play.getNode().getFitWidth() < ivBg.getFitWidth()) {
            		valX += 5; 
					play.setPlayerImage(play.RIGHT);
				}
				else if (up == true && play.getY() >= 0) {
					valY -= 5;
				}
				else if (down == true  && play.getY() + play.getNode().getFitHeight() <= ivBg.getFitHeight()) {
					valY += 5; 
				}
            	//changing the player image if they are attacking
				else if (play.attacking == true) {
					play.setPlayerImage(); 
					
					for (int i = 0; i < es.size(); i++) {
						if (play.getNode().getBoundsInParent().intersects(es.get(i).getNode().getBoundsInParent())) {
							es.get(i).setX(-1000);
							
							root.getChildren().remove(es.get(i).getNode());
							es.remove(i); 
						}
					}	
				}
				else {
					play.setPlayerImage(); 
				}
            	//moving the player
				play.move(valX, valY); 
				
				if(play.attacking == false) {
					for (int i = 0; i < es.size(); i++) {
						//checking if the player gets hit by the electricity spawn
						if (play.mask().getBoundsInParent().intersects(es.get(i).getNode().getBoundsInParent())) {
							es.get(i).setX(-1000);
							root.getChildren().remove(es.get(i).getNode());
													
						
							es.remove(i); 
							//checking if the user lost all their hearts meaning they lost 
							if (countDown <= 0) {
								root.getChildren().clear(); 		
								//stopping all timers
								zap.stop();
								elecTimer.stop();
								spawn.stop(); 
								zapTimer.stop();
								timer.stop();
								
								lose = true;
								winCheck(); 
								
								//stopping the timers
								zap.stop();
								elecTimer.stop();
								spawn.stop(); 
									
								//setting the size of the stage to the lose screen
								stage.setWidth(ivWin.getFitWidth()); 
								stage.setHeight(ivWin.getFitHeight()); 
							}
							
							//removing a heart of the player gets hit
							if (countDown >= 0) {
								root.getChildren().remove(ivHearts[countDown]); 
							}
							countDown--;
						}

					}
					
					//checking if the player gets hit by the blue electricity indicating they lost
					if (play.getNode().getBoundsInParent().intersects(blue.getNode().getBoundsInParent())) {
						if (countDown <= 0) {
							//stopping all timers
							zap.stop();
							elecTimer.stop();
							spawn.stop(); 
							zapTimer.stop();
							timer.stop();
							
							lose = true;
							winCheck(); 
							
							//setting the size of the stage based on the lose screen
							stage.setWidth(ivWin.getFitWidth()); 
							stage.setHeight(ivWin.getFitHeight()); 
						}
						
						if (countDown >= 0) {
							root.getChildren().remove(ivHearts[countDown]); 
						}
						
						countDown--;
					}
				}
				
            }
        };

	}
	
	public void getAlert() {
	Optional <ButtonType> result = al.showAndWait(); 
		
		//introductory alert
		while (true) {
            result = al.showAndWait();
            if (result.isPresent()) {
                ButtonType buttonType = result.get();
                
                //checking if the user clicks the control button
                if (buttonType == control) {
                    al.setContentText("Use WASD to move up, left, down, and right. Use J to attack.");
                }
                
                //checking if the user clicks the instructions button
                else if (buttonType == instructions) {
                    al.setContentText("Destroy the electricity bolts and don't lose all three lives before the 30 second timer runs out! AVOID the blue electricity at all costs as it cannot be destroyed.");
                }
                //checking if the user clicks the control button
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
}
