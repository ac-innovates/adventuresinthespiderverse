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

public class CatchTask extends MiniTasks {
	//fields
	private Image bask;
	private ImageView ivBask;
	private Player play; 
	private boolean left, right; 
	private AnimationTimer timer, gTimer, bTimer; 
	private ArrayList <GoodSpawn> good;
	private ArrayList <BadSpawn> bad;
	private Timeline goodSp, badSp;
	private Label countShow; 
	private int goodCount=0, badCount=0, counter=20; 
	private AudioClip catching;
	
	//constructor
	public CatchTask() {
		//setting the default values
		bg  = new Image("file:images/backgrounds/miniTasks/catchImages/catchBg.jpg"); 
		ivBg = new ImageView(bg); 
		
		bask = new Image("file:images/backgrounds/miniTasks/catchImages/basket.png"); 
		ivBask = new ImageView(bask);
		
		ivBask.setPreserveRatio(true); 
		ivBask.setFitWidth(70); 
		
		ivBask.setY((int)(bg.getHeight()-60));
		
		catching = new AudioClip("file:sounds/catchSky.mp3"); 
		
		//formatting the label showing the count down
		countShow = new Label();
		countShow.setFont(title); 
		countShow.setTextFill(Color.WHITE);
		countShow.setLayoutX(700);
		countShow.setLayoutY(50); 
		countShow.setText(""+counter);
		
		good = new ArrayList <GoodSpawn> (); 
		bad = new ArrayList <BadSpawn> (); 
		
		play = new Player(3); 
		play.setY((int)(bg.getHeight() - 90)); 
		
		stage = new Stage(); 
		root = new Pane();
		scene = new Scene(root, bg.getWidth(), bg.getHeight()); 
		
		lbl = new Label(); 
		

		//initializing the timer which will move the player and the basket based on the keys
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				int valX = 0, valY=0; 
				if (left == true && play.getX() >= 0) {
					valX -= 5; 
					play.setPlayerImage(play.LEFT);
				}
				else if (right == true && play.getX() + play.getNode().getFitWidth() < bg.getWidth()) {
					valX += 5; 
					play.setPlayerImage(play.RIGHT);
				}

				play.move(valX, valY); 
				ivBask.setX(play.getX() - ivBask.getFitWidth());
			}
		};
		 
	}
	//methods 
	
	//setting up the new stage with the scene and formatting 
	public void setUp() {
		openStage(); 
		stage.setScene(scene);
		
		//setting the player and its position
		play.setPlayer(3);
		play.setX((int)(bg.getWidth()/2 - play.getWidth()/2 - ivBask.getFitWidth()/2));
		
		root.getChildren().addAll(getNode(), getInstructions(), play.getNode(), ivBask, countShow); 
		
		stage.show(); 
		
		Optional <ButtonType> result = al.showAndWait(); 
		
		//introductory alert for the task
		while (true) {
            result = al.showAndWait();
            if (result.isPresent()) {
                ButtonType buttonType = result.get();
                
                //checking which button the user has pressed and acting accordingly 
                if (buttonType == control) {
                    al.setContentText("Use A and D to move left and right.");
                }
                else if (buttonType == instructions) {
                    al.setContentText("The infected and uninfected spiders have been mixed up! Catch 20 uninfected LIGHT PURPLE spiders so that they are not contaminated by the red spiders as well. AVOID the red spiders or else all will be contaminated and game over.");
                }
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
		
		movement(); 
		timer.start();
		theSpawns();
	}
	
	//returning the instructions added to the pane
	public Label getInstructions() {
		lbl.setFont(title);
		lbl.setTextFill(Color.WHITE);
		lbl.setText("Catch 20 uninfected light purple spiders so that they are not contaminated by the red spiders as\nwell."); ;
		
		return lbl; 
	}
	
	//spawning the spiders
	public void theSpawns() {
		
		//spawning the good spider every 1 second and adding it to the pane
		KeyFrame kf = new KeyFrame(Duration.millis(1000), new EventHandler <ActionEvent> () {
				public void handle (ActionEvent e) {
					good.add(goodCount, new GoodSpawn((int)bg.getWidth(), (int)bg.getHeight())); 
					good.get(goodCount).spawning(); 
					root.getChildren().addAll(good.get(goodCount).getNode()); 
					goodCount++; 
					gTimer.start(); 
				}
		}); 
		
		//initializing the Timeline used for the keyframe
		goodSp = new Timeline(kf); 
		goodSp.setCycleCount(Timeline.INDEFINITE); 
		goodSp.play(); 
		
		//moving the spawns once they are on the screen
		gTimer = new AnimationTimer () {
			public void handle(long val) {
				for(int i = 0; i < good.size(); i++) {
					good.get(i).move(); 
					
					//checking if the player has caught the good spider 
					if(good.get(i).getNode().getBoundsInParent().intersects(ivBask.getBoundsInParent())) {
						catching.play();
						root.getChildren().remove(good.get(i).getNode());
						counter--; 
						good.get(i).getNode().setX(-1000);
						countShow.setText(""+counter);
					}
					
					//checking if the player has caught the full number of good spiders indicating they won
					if (counter <= 0) {
						winner = true; 
						winCheck(); 
						gTimer.stop();
						counter = 100;
						timer.stop(); 
						badSp.stop();
						goodSp.stop();
					}
				}
				
				//moving the bad spiders
				for (int i = 0; i < bad.size(); i++) {
					bad.get(i).move(); 
					
					if(bad.get(i).getNode().getBoundsInParent().intersects(ivBask.getBoundsInParent())) {
						root.getChildren().clear(); 
						lose = true;
						winCheck();
						gTimer.stop(); 
						badSp.stop();
						goodSp.stop();
						
						stage.setWidth(ivWin.getFitWidth()); 
						stage.setHeight(ivWin.getFitHeight()); 
					}
				}			
			}
		};
		
		//spawning the bad spider every 2 seconds and adding it to the pane
		KeyFrame kfOne = new KeyFrame(Duration.millis(2000), new EventHandler <ActionEvent> () {
			public void handle (ActionEvent e) {
				bad.add(badCount, new BadSpawn((int)bg.getWidth(), (int)bg.getHeight())); 
				bad.get(badCount).spawning(); 
				root.getChildren().addAll(bad.get(badCount).getNode()); 
				badCount++; 
				gTimer.start(); 
			}
	}); 
	//initializing the Timeline used for the keyframe
	badSp = new Timeline(kfOne); 
	badSp.setCycleCount(Timeline.INDEFINITE); 
	badSp.play(); 
		
		
	}
	
	public void movement() { 
		//checking if certain keys are pressed
		scene.setOnKeyPressed(new EventHandler <KeyEvent> () { 
			public void handle(KeyEvent e) {
		        if (e.getCode() == KeyCode.A) {
		            left = true;
		        } else if (e.getCode() == KeyCode.D) {
		            right = true;
		        }
			}
		}); 
		
		//checking if certain keys are released
		scene.setOnKeyReleased(new EventHandler <KeyEvent> () { 
			public void handle(KeyEvent e) {
		        if (e.getCode() == KeyCode.A) {
		            left = false;
		        } else if (e.getCode() == KeyCode.D) {
		            right = false;
		        }
			}
		});	
	}
}
