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
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

//extending MiniTasks
public class ShootTask extends MiniTasks{
	//fields
	Bullet bul; 
	AnimationTimer timer;
	ArrayList <Enemy >en; 
	int count=0, countDown = 2, dead=0;
	Timeline ens;
	Image [] hearts;
	ImageView [] ivHearts;
	AudioClip shoot;
	
	//constructor
	public ShootTask() {
		//initializing fields
		play = new Player(3); 
		bul = new Bullet(); 
		
		hearts = new Image [3]; 
		ivHearts = new ImageView [3]; 
		
		bg = new Image("file:images/backgrounds/miniTasks/shootImages/shootBg.jpg");
		ivBg = new ImageView(bg);
		
		shoot = new AudioClip("file:sounds/webShoot.mp3"); 
		
		stage = new Stage();
		root = new Pane();
		scene = new Scene(root, bg.getWidth(), bg.getHeight());
		
		//formatting and initializing the hearts
		for (int i = 0; i < hearts.length; i++) {
			hearts[i] = new Image("file:images/backgrounds/miniTasks/heart.png"); 
			ivHearts[i] = new ImageView(hearts[i]); 
			ivHearts[i].setPreserveRatio(true); 
			ivHearts[i].setFitHeight(30);
		}
			
		lbl = new Label(); 
		
		en = new ArrayList <Enemy> (); 
		
	}
	
	//setting up the task
	public void setUp() {
		//opening the stage
		openStage(); 
		
		//setting scene to stage
		stage.setScene(scene);
		
		//setting the position of the hearts
		ivHearts[0].setLayoutX(840); 
		ivHearts[1].setLayoutX(880); 
		ivHearts[2].setLayoutX(920); 

		//setting player and its position
		play.setPlayer(3);
		play.setX((int)((bg.getWidth()/2) - (play.getWidth()/2)));
		play.setY((int)((bg.getHeight()/2) - (play.getHeight()/2))); 
		
		//adding nodes to pane
		root.getChildren().addAll(getNode(), getInstructions(), play.getNode(), bul.getNode()); 
		
		//addings hearts to pane
		for (int i = 0; i < ivHearts.length; i++) { 
			root.getChildren().add(ivHearts[i]); 
		}
		
		//showing the stage
		stage.show(); 	
		
		//moving the player
		movement(); 
		
		//starting the timer
		timer.start();
		
		//spawning the enemies
        spawning(); 
	}
	
	//label with instructions of task
	public Label getInstructions() {
		lbl.setFont(title);
		lbl.setTextFill(Color.WHITE);
		lbl.setText("Multiple Doctor Octopus' have entered from the wrong universes! Kill them before they destroy the universe."); 

		
		return lbl; 
	}
	
	//spawning the enemy
	public void spawning() {
		KeyFrame kf = new KeyFrame(Duration.millis(1700), new EventHandler <ActionEvent> () {
			public void handle(ActionEvent e) {			
				//every 1.5 seconds the enemy is spawned
				en.add(count, new Enemy((int)bg.getWidth(), (int)bg.getHeight())); 
				root.getChildren().addAll(en.get(count).getImage()); 
				timer.start(); 
				count++; 
			}
		});
		
		//timeline for keyframe
		ens = new Timeline(kf); 
		ens.setCycleCount(30); 
		ens.play(); 
	}
	
	//movement of player
	public void movement() {
		scene.setOnKeyPressed(new EventHandler <KeyEvent> () { 
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.W) {
		            up = true;
		        } else if (e.getCode() == KeyCode.S) {
		            down = true;
		        } else if (e.getCode() == KeyCode.A) {
		            left = true;
		        } else if (e.getCode() == KeyCode.D) {
		            right = true;
		        }
		        else if (e.getCode() == KeyCode.J) {
		        	play.attacking = true;
		        	//projectile of spiderweb being shot only if it has left the screen
		        	if (!bul.fired) {
		        		shoot.play();
			        	bul.fired = true; 
			        	
			        	//setting position of bullet
			        	bul.setX(play.getX());
			        	bul.setY(play.getY()); 
			        	
			        	//checking direction of player
			        	if (play.xDir == play.RIGHT) {
			        		bul.setDir(bul.EAST);
			        		bul.setImage(bul.EAST); 
			        	}
			        	else if (play.xDir == play.LEFT) {
			        		bul.setDir(bul.WEST); 
			        		bul.setImage(bul.WEST); 
			        	}
		        	
		        	}
				        	
	
		        }
			}
		}); 
		
		//checking which keys have been released
		scene.setOnKeyReleased(new EventHandler <KeyEvent> () { 
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.W) {
		            up = false;
		        } else if (e.getCode() == KeyCode.S) {
		            down = false;
		        } else if (e.getCode() == KeyCode.A) {
		            left = false;
		        } else if (e.getCode() == KeyCode.D) {
		            right = false;
		        }
		        else if (e.getCode() == KeyCode.J) {
		        	play.attacking = false; 
		        }
			}
		});
	}
	
	public void timerMovement() {

		  timer = new AnimationTimer() {
	            @Override
	            public void handle(long now) {
	            	int valX = 0, valY=0; 
	            	//ensuring player is moving within the screen
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
	            	//changing the player's image if they are attacking
	            	if (play.attacking == true) {
						play.setPlayerImage(); 
					}
	            	else {
	            		play.setPlayerImage(); 
	            	}

	            	//moving the player
					play.move(valX, valY);	
					
					//moving the bullet if it has been fired
					if (bul.fired) {
						bul.move();
						
					}
					
					//checking if the bullet has left the screen
					if (bul.getX() + bul.getWidth() <= 0 || bul.getX() > bg.getWidth()) { 
						bul.stopBullet();
						bul.setX(-1000); 
						bul.fired = false;
					}	
					
					//moving the enemies
					for (int i = 0; i < en.size(); i++) {
						en.get(i).move(); 
						
						//changing direction of enemy if they hit any of th walls
						if (en.get(i).getX() < 0) {
							en.get(i).setDirectionX(en.get(i).EAST); 
						}
						else if (en.get(i).getX() + en.get(i).getWidth() > bg.getWidth()) {
							en.get(i).setDirectionX(en.get(i).WEST); 
						}
						
						if (en.get(i).getY() < 0) {
							en.get(i).setDirectionY(en.get(i).SOUTH); 
						}
						else if (en.get(i).getY() + en.get(i).getHeight() > bg.getHeight()) {
							en.get(i).setDirectionY(en.get(i).NORTH); 
						}
						
						//checking if bullet hits the enemy to kill it
						if (bul.getNode().getBoundsInParent().intersects(en.get(i).getImage().getBoundsInParent())) {
							en.get(i).setX(-10000);
							en.get(i).isAlive = false; 
							bul.setX(-1000); 
							dead++; 
						}
						//checking if enemy's mask hits the player
						else if (en.get(i).mask().getBoundsInParent().intersects(play.getNode().getBoundsInParent())) {
							dead++; 
							en.get(i).setX(-10000);
							en.get(i).isAlive = false; 
							
							//checking if all lives have been lost
							if (countDown <= 0) {
								root.getChildren().clear(); 
								//displaying the lose screen
								lose = true;
								winCheck(); 
								//stopping the timers
								timer.stop();
								ens.stop();
								
								//setting size of stage to size of lose screen
								stage.setWidth(ivWin.getFitWidth()); 
								stage.setHeight(ivWin.getFitHeight()); 
							}
							
							//removing hearts with enemy hits player
							if (countDown >= 0) {
								root.getChildren().remove(ivHearts[countDown]); 
							}
							countDown--;
						}
						
						//checking if all enemies have been killed
						if (dead == 30) {
							//displaying win screen
							winner = true;
							winCheck();
							timer.stop();
							ens.stop(); 
							dead = 31;
						}

					}
	            }
	        };
	}
	//introductory alert
	public void getAlert() {
		Optional <ButtonType> result = al.showAndWait(); 
		
		//showing alert with options
		while (true) {
            result = al.showAndWait();
            if (result.isPresent()) {
                ButtonType buttonType = result.get();
                //checking if the control button was clicked
                if (buttonType == control) {
                    al.setContentText("Use WASD to walk around the map. Use J to attack.");
                }
                //checking if the instructions button was clicked
                else if (buttonType == instructions) {
                    al.setContentText("Doctor Octopus' have come together from different universes to attack! Destroy all of them to complete the level.");
                }
                //checking if the close button was clicked
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
