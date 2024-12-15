/* ICS4U1 CPT: Spiderman: Adventures in the Spider-Verse
 * Areen Charania
 * Mr. Conway
 * Program Description: The game begins with the title screen displaying the characters that are used within the game. 
 * There is Miles Morales, Peter Parker, Gwen Stacy, and Miguel O’Hara from Spiderman. This game has four levels where 
 * each level takes place in a different multiverse and has a different character associated with it. Click the level button 
 * to enter it. The character will enter through the portal and the character being used in the level will be displayed. 
 * Within the level, the characters must complete tasks in order to complete the level. Each level uses a scrolling 
 * background. The characters move with W, A, S, and D and they can also attack with J. Level 1 has 2 tasks while the 
 * rest of the levels have 3. The levels can vary where some have the player, some require using the mouse, the keys, 
 * entering information into a textfield, shooting projectiles, catching spawns from the sky, etc. Each task has its own 
 * alert which explains the instructions and reminds the users of the controls that would be used within the task. 
 * Each level also has its own song associated with it along with some sound effects throughout. In the end, the user can 
 * add their name to the list of names and their score. They can also find out what place they are on the leaderboard 
 * through binary search. Overall, the aim of the game is to successfully complete all four levels in as little time as 
 * possible. Some tasks may provide the user with three hearts, while others there is only one chance to successfully 
 * complete a task or else it is game over. 
 * 
 * Program Details: JavaFX components were utilized all throughout the program. There are buttons in the end to see 
 * the scoreboard and add the user’s name to the list. There are also buttons within the tasks such as in the code task 
 * where the user must enter the code from the screen. There are labels for the instructions as well as for the countdowns 
 * shown on many of the levels within the task. RadioButtons are used in the second task in level 1 where the user can 
 * select which enemy they would like to obtain from the database. Textfield is used in the task where the user must enter 
 * the code as a place for the user to input. Alerts with custom images are used as an error when the user tries to enter a
 * level that they have not unlocked yet. Alerts with custom buttons are used in each of the tasks to inform the user of 
 * what the instructions and controls for that task are. There are also alerts with custom images used in the codetask as 
 * well as the matchingtask to let the user know if their selection is correct. . Many different layouts are used all throughout the program 
 * such as tilepane for the menu buttons, gridpane to set up the layout of the matching task, VBox to place the scoreboard and add name button 
 * next to each other, Borderpane to format the send back task, as well as HBox and pane used as well. 1D arrays are used constantly in the program 
 * for checking if a level is complete through a boolean array, many image arrays such as in the electrotask and shoottask for 
 * the hearts, and for the dark matter object. The menu buttons also use an array. A 2D array is used in the matchingtask as 
 * the user needs to select the matching images from the square where random images are generated to each of the buttons. 
 * The bubble sort algorithm is used to sort the scores in the text file by score. Binary search from the Collections 
 * class is used to find where in the leaderboard the current user would place based on the other scores. 
 * For object oriented programming, there are about 24 classes used in this program where each task has its own class 
 * as well as many other elements such as the player and the enemy. The abstract class is Spawn where it has the general
 * methods and fields that something that spawns would need. It is inherited by ElectricitySpawn, GoodSpawn, and BadSpawn. 
 * side from this, inheritance is also used in DarkMatter which inherits the Background because the dark matter must move 
 * at the same pace as the scrolling background. Polymorphism is used for the tasks where the parent class is MiniTasks and
 * all the other class tasks inherit this class as it has the basic functionality needed for a task. The MiniTasks variable 
 * is re-instantiated every time a new task is run. Animation is used when moving the player within tasks as well as from 
 * the scrolling background. There are also many other moving parts such as enemies, spawns, and the darkmatter. 
 * Collision detection is used in many tasks such as ElectroTask, CatchTask, ShootTask, SendBackTask, and many others. 
 * They are used to detect collision between player and enemy, player and spawn, projectile and enemy, etc. The File class
 * is used to print the user’s name along with their score. Their score is the amount of time it took them to complete the 
 * full game. ArrayLists are used in ElectroTask for the electricity spawns, in ShootTask for the enemies, for the user’s 
 * score, for the user’s name, and many other places. As for sounds, there is background music in the menu screen, each level 
 * has its own background music, and then there are also sound effects sprinkled throughout the program. 
 */
package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Main extends Application {	
	//fields
	Image [] levelButtons;
	MenuButtons [] buts; 
	AnimationTimer movingButs, playerTimer, taskTimer, storyTimer,check;
	int butVal=0, level=1, winCounter, taskDoing, fullTime;
	Timeline entering, scoreKeep; 
	Player play; 
	Background bg;
	Stage primary; 
	Pane root; 
	ImageView ivEnter, ivMenuBg, ivAlert, ivTitle, ivError;
	boolean up, down, left, right, alertShown=false, tShown; 
	boolean [] levelComplete;
	DarkMatter [] dm; 
	int counter=0, otherCounter=0, onlyOnce=0;
	MiniTasks mt; 
	TilePane levelArrange;
	Image menuBg, alertImg, titleBg, errorImg;
	Button enter, story, scoreboard, addName, leaveGame;
	Label winner, youWin; 
	Font title, bigtitle;
	File file; 
	File [] bgMusics;
	Media bgMedia;
	MediaPlayer bgPlayer;
	ArrayList <Double> score; 
	ArrayList <String> name;
	TheStories ts;
	AudioClip portal, but, enterTask, levWin;
	double conversion;
	@Override
	public void start(Stage primaryStage) {
		try { 	
			
			//initializing variables
			primary = primaryStage; 

			root = new Pane();
			ts = new TheStories(level);

			file = new File("scores.txt"); 

			//initializing audioclips for sound effects
			but = new AudioClip("file:sounds/button.wav"); 
			portal = new AudioClip("file:sounds/portalSound.mp3"); 
			enterTask = new AudioClip("file:sounds/enterTask.mp3"); 
			levWin = new AudioClip("file:sounds/levelWin.mp3");

			//array of files for background music
			bgMusics = new File [5]; 

			//initializing the files for the background music
			bgMusics[0] = new File ("sounds/menuBg.mp3"); 
			bgMusics[1] = new File ("sounds/milesBg.mp3"); 
			bgMusics[2] = new File ("sounds/peterBg.mp3"); 
			bgMusics[3] = new File ("sounds/gwenBg.mp3");
			bgMusics[4] = new File ("sounds/miguelBg.mp3"); 

			//initializing Media and MediaPlayer for the background musics
			bgMedia = new Media(bgMusics[0].toURI().toString());
			bgPlayer = new MediaPlayer(bgMedia);
			bgPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			bgPlayer.play();

			//initializing the title screen image 
			titleBg = new Image("file:images/backgrounds/titleScreen.png"); 
			ivTitle = new ImageView(titleBg);
			
			//formatting the title screen image
			ivTitle.setPreserveRatio(true);
			ivTitle.setFitWidth(800);
			ivTitle.setFitHeight(600);

			//initializing fonts
			title = Font.loadFont("file:images/backgrounds/miniTasks/miniLevelTitles.ttf", 22); 
			bigtitle = Font.loadFont("file:images/backgrounds/miniTasks/miniLevelTitles.ttf", 100); 

			//initializing image for errors alerts
			errorImg = new Image("file:images/backgrounds/miniTasks/catchImages/badSpider.png");
			ivError = new ImageView(errorImg);
			ivError.setPreserveRatio(true); 
			ivError.setFitHeight(60); 

			//initializing and formatting the leave game button
			leaveGame = new Button();
			leaveGame.setFont(title);
			leaveGame.setStyle("-fx-background-color: blue");
			leaveGame.setText("Leave Game");
			leaveGame.setTextFill(Color.WHITE);
			leaveGame.setOnAction(e -> leave()); 

			//initializing and formatting the enter button
			enter = new Button(); 
			enter.setText("PLAY"); 
			enter.setTextFill(Color.WHITE); 
			enter.setFont(title);
			enter.setStyle("-fx-background-color: black");
			enter.setOnAction(e -> enterGame(e)); 

			//setting the position of the enter button
			enter.setLayoutX((ivTitle.getFitWidth()/2) - 50);
			enter.setLayoutY(542);

			levelComplete = new boolean [4]; 

			//checking if the mouse enters the enter button
			enter.setOnMouseEntered(new EventHandler <MouseEvent> () {
				public void handle(MouseEvent e) {
					enter.setStyle("-fx-background-color: blue");
				}
			});

			//checking if the mouse exits the enter button
			enter.setOnMouseExited(new EventHandler <MouseEvent> () {
				public void handle(MouseEvent e) {
					enter.setStyle("-fx-background-color: black");
				}
			});

			//adding the title screen and enter button
			root.getChildren().addAll(ivTitle, enter); 

			winner = new Label();

			//initializing the menu background image
			menuBg = new Image("file:images/backgrounds/menuBg.jpg"); 
			ivMenuBg = new ImageView(menuBg);

			//initializing the default alert graphic image
			alertImg = new Image("file:images/random/alertImg.png"); 
			ivAlert = new ImageView(alertImg); 

			//initializing objects from classes created
			dm = new DarkMatter [3]; 
			play = new Player(level); 
			bg = new Background(); 
			mt = new MiniTasks(); 
			buts = new MenuButtons[4]; 

			//initializing tilepane for menu buttons and formatting it
			levelArrange = new TilePane();
			levelArrange.setPrefColumns(2);
			levelArrange.setVgap(50); 
			levelArrange.setHgap(350);

			levelButtons = new Image[4];


			//initializing the level buttons
			for (int i = 0; i < levelButtons.length; i++) {
				levelButtons[i] = new Image("file:images/buttonLevels/level " + (i+1) + ".png"); 
			}
			
			//initializing and formatting the menu buttons
			for (int i = 0; i < buts.length; i++) {
				int levelIdentify = (i+1); 
				buts[i] = new MenuButtons(levelButtons[i]);
				buts[i].getNode().setPreserveRatio(true);
				buts[i].getNode().setFitHeight(200);
				levelArrange.getChildren().add(buts[i].getNode()); 

				final int index = i;
				//checking if the mouse has entered one of the buttons
				buts[i].getNode().setOnMouseEntered(new EventHandler<MouseEvent> () {
					public void handle(MouseEvent e) {
						//spinning the button
						butVal = index;
						movingButs.start();
					}
				});

				//checking if the mouse has exited one of the buttons
				buts[i].getNode().setOnMouseExited(new EventHandler<MouseEvent> () {
					//stopping the button from spinning
					public void handle(MouseEvent e) {
						if (butVal == index) {
							movingButs.stop();
							buts[index].setRotation(0); 
							butVal = -1;
						}
					}
				});

				int val = i;
				//checking if a button has been clicked
				buts[i].getNode().setOnMouseClicked(new EventHandler <MouseEvent> () {

					public void handle(MouseEvent e) {
						//checking that the user is only clicking unlocked levels
//						if (level == (val+1) && !levelComplete[val]) {
							//starting the portal graphic
							portal.play();
							
							//stopping the music
							bgPlayer.stop();

							//initializing image of portal
							Image enterLevel = new Image("file:images/buttonLevels/enteringLevel.gif"); 
							ivEnter = new ImageView(enterLevel); 

							//formatting image of portal
							ivEnter.setFitHeight(500);
							ivEnter.setFitWidth(850);
							ivEnter.setPreserveRatio(true);
							
							//adding only the portal to pane
							root.getChildren().clear();
							root.getChildren().add(ivEnter); 
							entering.play(); 
							
							//resizing the stage to the size of the portal image
							primaryStage.setWidth(ivEnter.getFitWidth());
							primaryStage.setHeight(ivEnter.getFitHeight());
							level = levelIdentify;			
//						}
//						else {
//							//telling the user that they cannot access a level because they have not unlocked it
//							Alert alrt = new Alert(AlertType.ERROR); 
//							//formatting the alert
//							alrt.setGraphic(ivError); 
//							alrt.setContentText("ERROR: Complete the level before this one to unlock OR you're already done this level!"); 
//							alrt.setHeaderText(null);
//							alrt.setTitle("ERROR"); 
//							alrt.showAndWait(); 
//						}
					}

				});
			}

			//setting the position of the menu buttons
			levelArrange.setLayoutX(menuBg.getWidth()/2 - 375); 
			levelArrange.setLayoutY(menuBg.getHeight()/2 - 225); 			

			//initializing the scene and formatting pane
			Scene scene = new Scene(root,ivTitle.getFitWidth(),ivTitle.getFitHeight());
			root.setStyle("-fx-background-color: black"); 

			//making the portal image show for 3.5 seconds
			KeyFrame kf = new KeyFrame(Duration.millis(3500), new EventHandler <ActionEvent> () {
				public void handle(ActionEvent e) {

					root.getChildren().remove(ivEnter); 
					levelCheck(); 

					//stopping the background music and playing the sound effect
					bgPlayer.stop();
					bgMedia = new Media(bgMusics[level].toURI().toString());
					bgPlayer = new MediaPlayer(bgMedia);
					bgPlayer.play();
				}
			});

			//timeline for keyframe
			entering = new Timeline(kf); 
			entering.setCycleCount(1); 

			//rotating the buttons
			movingButs = new AnimationTimer() {
				@Override
				public void handle(long arg0) {
					buts[butVal].move(); 
				}
			};

			//full timer for the game to keep track of how long it takes player to win
			KeyFrame full = new KeyFrame(Duration.millis(1000), new EventHandler <ActionEvent> () {
				public void handle(ActionEvent e) {
					fullTime++; 
				}
			});

			//timeline for keyframe
			scoreKeep = new Timeline(full);
			scoreKeep.setCycleCount(Timeline.INDEFINITE); 
			scoreKeep.play(); 

			//checking the keys that the user presses
			scene.setOnKeyPressed(new EventHandler <KeyEvent> () {
				public void handle(KeyEvent e) {
					//keys for movement
					if (e.getCode() == KeyCode.A) {
						left = true;
					}
					else if (e.getCode() == KeyCode.D) {
						right = true;
					}
					else if (e.getCode() == KeyCode.W) {
						up = true;
					}
					else if (e.getCode() == KeyCode.S)  {
						down = true; 
					}
					//key for attacking
					else if (e.getCode() == KeyCode.J) {
						play.attacking = true;
					}
					//key to enter tasks
					else if (e.getCode() == KeyCode.SPACE) {
						left = false; right = false;

						//checking if the user intersects with a dark matter to play a sound effect
						if (play.getNode().getBoundsInParent().intersects(dm[0].getNode().getBoundsInParent()) || !tShown
								|| play.getNode().getBoundsInParent().intersects(dm[0].getNode().getBoundsInParent()) 
								||play.getNode().getBoundsInParent().intersects(dm[0].getNode().getBoundsInParent())) {

							enterTask.play();
						}


						//tasks for level 1
						if (level == 1) {
							//checking if the player is entering the first task
							if (play.getNode().getBoundsInParent().intersects(dm[0].getNode().getBoundsInParent())&& !tShown) {
								//polymorphism to re-instantiate the MiniTasks object
								mt = new PuzzleTask(); 
								((PuzzleTask)mt).setUp(); 
								((PuzzleTask)mt).gameChecker(); 
								taskDoing = 1;
								tShown = true;
							}
							else if (play.getNode().getBoundsInParent().intersects(dm[1].getNode().getBoundsInParent())&& !tShown) {
								//polymorphism to re-instantiate the MiniTasks object
								mt = new SendBackTask(); 
								((SendBackTask)mt).setUp(); 
								taskDoing = 2;
								tShown = true; 
							}
						}
						//tasks for level 2
						else if (level == 2) {
							if (play.getNode().getBoundsInParent().intersects(dm[0].getNode().getBoundsInParent())&& !tShown) {
								//polymorphism to re-instantiate the MiniTasks object
								mt = new StabilizeTask(); 
								((StabilizeTask)mt).setUp(); 								((StabilizeTask)mt).setUp(); 
								taskDoing = 1;
								tShown = true; 
							}

							if (play.getNode().getBoundsInParent().intersects(dm[1].getNode().getBoundsInParent()) && !tShown) {
								//polymorphism to re-instantiate the MiniTasks object
								mt = new MatchingTask(); 
								((MatchingTask)mt).setUp(); 
								taskDoing = 2;
								tShown = true; 
							}

							if (play.getNode().getBoundsInParent().intersects(dm[2].getNode().getBoundsInParent()) && !tShown) {
								//polymorphism to re-instantiate the MiniTasks object
								mt = new ElectroTask(); 
								((ElectroTask)mt).timerMovement(); 
								((ElectroTask)mt).setUp();
								((ElectroTask)mt).spawn(); 		
								taskDoing = 3; 
								tShown = true; 
							}
						}
						//tasks for level 3
						else if (level == 3) {
							if (play.getNode().getBoundsInParent().intersects(dm[0].getNode().getBoundsInParent()) && !tShown) {
								//polymorphism to re-instantiate the MiniTasks object
								mt = new CatchTask(); 
								((CatchTask)mt).setUp(); 
								taskDoing = 1; 
								tShown = true; 
							}
							if (play.getNode().getBoundsInParent().intersects(dm[1].getNode().getBoundsInParent()) && !tShown) {
								//polymorphism to re-instantiate the MiniTasks object
								mt = new CodeTask(); 
								((CodeTask)mt).setUp(); 
								taskDoing = 2; 
								tShown = true; 
							}
							if (play.getNode().getBoundsInParent().intersects(dm[2].getNode().getBoundsInParent()) && !tShown) {
								//polymorphism to re-instantiate the MiniTasks object
								mt = new ShootTask();
								((ShootTask)mt).timerMovement(); 
								((ShootTask)mt).setUp(); 
								taskDoing = 3; 
								tShown = true; 
							}
						}
						//tasks for level 4
						else if (level == 4) {
							if (play.getNode().getBoundsInParent().intersects(dm[0].getNode().getBoundsInParent())&& !tShown) {
								//polymorphism to re-instantiate the MiniTasks object
								mt = new PongTask();
								((PongTask)mt).timerMovement(); 
								((PongTask)mt).setUp();
								taskDoing = 1;
								tShown = true; 
							}

							if (play.getNode().getBoundsInParent().intersects(dm[1].getNode().getBoundsInParent())&& !tShown) {
								//polymorphism to re-instantiate the MiniTasks object
								mt = new FindItemsTask(); 
								((FindItemsTask)mt).setUp();
								taskDoing = 2; 
								tShown = true; 
							}

							if (play.getNode().getBoundsInParent().intersects(dm[2].getNode().getBoundsInParent())&& !tShown) {
								//polymorphism to re-instantiate the MiniTasks object
								mt = new CollectTask(); 
								((CollectTask)mt).timerMovement(); 
								((CollectTask)mt).setUp();
								taskDoing = 3; 
								tShown = true; 
							}

						}
					}
				}
			}); 

			//checking if keys have been released
			scene.setOnKeyReleased(new EventHandler <KeyEvent> () {
				public void handle(KeyEvent e) {
					if (e.getCode() == KeyCode.A) {
						left = false;
					}
					else if (e.getCode() == KeyCode.D) {
						right = false;
					}
					else if (e.getCode() == KeyCode.W) {
						up = false;
					}
					else if (e.getCode() == KeyCode.S)  {
						down = false; 
					}
					else if (e.getCode() == KeyCode.J) {
						play.attacking = false;
						play.setPlayerImage(); 
					}
				}
			}); 


			playerTimer = new AnimationTimer() {
				public void handle(long val) {
					//moving the background to keep the player within the screen (boundary checking) 
					if (left == true && play.getX() > bg.getX() + 40) {
						play.setPlayerImage(play.LEFT);
						bg.setXDirection(bg.RIGHT); 
						bg.setYDirection(-1);
						bg.move(); 
						
						//moving the dark matter with the background
						for (int i = 0; i < dm.length; i++) {
							dm[i].setXDirection(dm[i].RIGHT); 
							dm[i].setYDirection(-1);
							dm[i].move();
						}
					}
					else if (right == true && play.getX() + play.getNode().getFitWidth() < bg.getX() + bg.getNodeOne().getFitWidth() + bg.getNodeOne().getFitWidth() - 20) {
						play.setPlayerImage(play.RIGHT);
						bg.setYDirection(-1);
						bg.setXDirection(bg.LEFT);
						bg.move(); 

						//moving the dark matter with the background
						for (int i = 0; i < dm.length; i++) {
							dm[i].setXDirection(dm[i].LEFT); 
							dm[i].setYDirection(-1);
							dm[i].move();
						}
					}
					else if (play.attacking == true) {
						play.setPlayerImage(); 
					}
					
					//changing the image of dark matter if the player intersects with it so they know to enter
					for (int i = 0; i < dm.length; i++) {
						if (play.getNode().getBoundsInParent().intersects(dm[i].getNode().getBoundsInParent())) {
							dm[i].imgSwitch(true); 
						}
						else {
							dm[i].imgSwitch(false);
						}
					}
					//closing the stage if the user loses a task
					if (mt.lose) {
						primaryStage.close(); 
					}

					if (level == 1) {
//						checking if the user won their task in level 1
						if (mt.winner) {
							if (taskDoing == 1) {
								root.getChildren().remove(dm[0].getNode()); 
							}
							else if (taskDoing == 2) {
								root.getChildren().remove(dm[1].getNode()); 
							}
							winCounter++; 
							mt.winner = false; 
							tShown = false;
						}
					}
					else if (level == 2) {
						//checking if the user won their task in level 2
						if (mt.winner) {
							if (taskDoing == 1) {
								root.getChildren().remove(dm[0].getNode());
							}
							else if (taskDoing == 2) {
								root.getChildren().remove(dm[1].getNode());
							}
							else if (taskDoing == 3) {
								root.getChildren().remove(dm[2].getNode());
							}
							winCounter++; 
							mt.winner = false;
							tShown = false;
						}
					}
					else if (level == 3) {
						//checking if the user won their task in level 3
						if (mt.winner) {
							if (taskDoing == 1) {
								root.getChildren().remove(dm[0].getNode());
							}
							else if (taskDoing == 2) {
								root.getChildren().remove(dm[1].getNode());
							}
							else if (taskDoing == 3) {
								root.getChildren().remove(dm[2].getNode());
							}
							winCounter++;
							mt.winner=false;
							tShown = false;
						}
					}
					else if (level == 4) {
						if (mt.winner) {
							if (taskDoing == 1) {
								root.getChildren().remove(dm[0].getNode()); 
							}
							else if (taskDoing == 2) {
								root.getChildren().remove(dm[1].getNode());
							}
							else if (taskDoing == 3) {
								root.getChildren().remove(dm[2].getNode());
							}
							winCounter++;
							mt.winner=false;
							tShown = false;
						}
					}

					Platform.runLater(new Runnable() { 
						@Override
						public void run() {

							//checking if level 1 was won
							if (level == 1) {
								if (winCounter == 2 && !alertShown) {
									//playing a sound effect
									levWin.play();
									
									//displaying an alert indicating the user has won the level
									Alert alert = new Alert(AlertType.INFORMATION);
									alert.setContentText("Congratulations! You have passed level 1 and completed your tasks as Miles Morales. Great Job! Go to the next level for your next adventure!");
									alert.setTitle("Level 1");
									alert.setHeaderText(null);
									alert.setGraphic(ivAlert);
									alertShown = true; 
									alert.showAndWait(); 
									
									//moving on
									level = 2;
									mainScreen();
									
									//resetting values
									winCounter = 0; 
									levelComplete[0] = true;
								}
							}

							if (level == 2) {
								//checking if level 2 was won
								if (winCounter==3 && !alertShown) {
									//playing a sound effect
									levWin.play();
									
									//displaying an alert indicating the user has won the level
									Alert alert = new Alert(AlertType.INFORMATION);
									alert.setContentText("Congratulations! You have passed level 2 and completed your tasks as Peter Parker. Great Job! Go to the next level for your next adventure!");
									alert.setTitle("Level 2");
									alert.setHeaderText(null);
									alert.setGraphic(ivAlert);
									alertShown = true; 
									alert.showAndWait(); 
									
									//moving on
									level = 3;
									mainScreen(); 
									
									//resetting values
									winCounter = 0; 
									levelComplete[1] = true;
								}	
							}

							if (level == 3) {
								//checking if level 3 was won
								if (winCounter==3 && !alertShown) {	
									//playing a sound effect
									levWin.play();
									
									//displaying an alert indicating the user has won the level
									Alert alert = new Alert(AlertType.INFORMATION);
									alert.setContentText("Congratulations! You have passed level 3 and completed your tasks as Gwen Stacy. Great Job! Go to the next level for your next adventure!");
									alert.setTitle("Level 3");
									alert.setHeaderText(null);
									alert.setGraphic(ivAlert);
									alertShown = true; 
									alert.showAndWait(); 
									
									//moving on
									level = 4;
									mainScreen(); 
									
									//resetting values
									winCounter = 0; 
									levelComplete[2] = true;
								}	
							}

							if (level == 4) {
								//checking if level 4 was won
								if (winCounter==3 && !alertShown) {	
									//playing a sound effect
									levWin.play();
									
									//displaying an alert indicating the user has won the level
									Alert alert = new Alert(AlertType.INFORMATION);
									alert.setContentText("Congratulations! You have passed level 4 and completed your tasks as Miguel O'Hara. Great Job! Go to the next level for your next adventure!");
									alert.setTitle("Level 4");
									alert.setHeaderText(null);
									alert.setGraphic(ivAlert);
									alertShown = true; 
									alert.showAndWait(); 
									
									//moving on
									level = 5;
									mainScreen();
									winner(); 
									
									//resetting values
									winCounter = 0; 
									levelComplete[3] = true;
								}	
							}
							
							//clearing the pane if a task is closed
							mt.openStage().setOnCloseRequest(new EventHandler <WindowEvent> () {
								@Override
								public void handle(WindowEvent e) {
									mt.root.getChildren().clear();
									tShown = false;
								}

							});

						}
					}); 

				}
			};

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	//checking which level the user is on
	public void levelCheck() {
		//introductory gif for each level
		ts = new TheStories(level);
		ts.changeImage(); 

		root.getChildren().clear(); 

		otherCounter=0; 

		//setting the size of the stage to the introductory gif
		primary.setWidth(ts.getNode().getFitWidth());
		primary.setHeight(ts.getNode().getFitHeight()+ 100); 

		//playing the gif on a timer
		storyTimer = new AnimationTimer () {
			public void handle(long val) {
				ts.story();
				if (ts.done && otherCounter < 1) {
					//going into the level
					levels(); 
					otherCounter++; 
				}
			}
		};
		storyTimer.start();
		
		//adding the story to pane
		root.getChildren().addAll(ts.getNode(), ts.meet());
	}

	//setting up each level
	public void levels() {	
		root.getChildren().clear(); 

		//stopping timer for story
		if (otherCounter > 0) {
			storyTimer.stop(); 
		}

		//initializing dark matter array based on the level
		if (level == 1) { 
			dm = new DarkMatter [2]; 
		}
		else if (level == 2) {
			dm = new DarkMatter [3];
		}
		else if (level == 3) {
			dm = new DarkMatter [3];
		}

		//setting the images of background and player based on the level
		play.setPlayer(level);
		bg.changeImage(level); 

		//setting the size of the pane to the background
		primary.setWidth(bg.oneWidth());
		primary.setHeight(bg.getHeight());

		//setting the position of the player
		play.setX(100);
		play.setY(270);
		playerTimer.start();
		
		//setting the position of the background
		bg.setX(0);

		//adding background to pane
		root.getChildren().addAll(bg.getNodeOne(), bg.getNodeTwo());

		int numIncrease=400; 

		//initializing and formatting the dark matter on the screen
		for (int i = 0; i < dm.length; i++) {
			dm[i] = new DarkMatter(bg.getWidth(), bg.getHeight(), numIncrease, level);
			dm[i].setImage(level); 

			numIncrease += 300; 

			//spawning the dark matter
			dm[i].spawning(level); 

			//adding it to pane
			root.getChildren().add(dm[i].getNode()); 			
		}

		//adding the player to pane
		root.getChildren().addAll(play.getNode()); 

		//changing the y position of the player based on the level
		if (level == 1) {
			play.setY(bg.getHeight()-130);
		}
		else if (level == 2) {
			play.setY(220); 
		}
		else if (level == 4) {
			play.setY(bg.getHeight()-130);
		}

	}

	//menu screen set up
	public void mainScreen() {
		//stopping the music
		bgPlayer.stop();
		
		//changing the music to the menu screen music and playing it
		bgMedia = new Media(bgMusics[0].toURI().toString());
		bgPlayer = new MediaPlayer(bgMedia);
		bgPlayer.play();

		root.getChildren().clear(); 
		
		//setting the size of the stage to the menu background
		primary.setWidth(menuBg.getWidth());
		primary.setHeight(menuBg.getHeight());
		
		//adding the menu items to pane
		root.getChildren().addAll(ivMenuBg, levelArrange);
		alertShown = false;
	}

	//entering game
	public void enterGame(ActionEvent e) {
		root.getChildren().clear(); 
		//playing button sound effect
		but.play(); 
		
		//introduction story telling
		ts.intro(); 
		
		//resetting size of stage
		primary.setWidth((int)ts.getIntroNode().getFitWidth());
		primary.setHeight((int)ts.getIntroNode().getFitHeight() + 100);


		//initializing and formatting skip button
		Button skip = new Button(); 
		skip.setText("SKIP");
		skip.setTextFill(Color.BLACK);
		skip.setLayoutX(700);
		skip.setFont(title);
		skip.setOnAction(k -> enterGame(k));

		//checking when the story is done to go to main screen
		check = new AnimationTimer() {
			public void handle (long val) {
				if (ts.count() >= 5 || skip.isPressed()) {
					mainScreen();
					check.stop();
				}
			}
		};
		check.start();

		//adding nodes to pane
		root.getChildren().addAll(ts.getIntroNode(), ts.getLabel(), skip);
	}

	//formatting scoreboard
	public void scoreBoard() { 
		root.getChildren().clear(); 		

		String lineOfInfo;

		try {
			//initializing filereader and bufferedreader
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr); 

			//initializing layouts
			HBox hb = new HBox();
			VBox vb = new VBox();

			//initializing arraylists
			name = new ArrayList <String> (); 
			score = new ArrayList <Double> (); 

			//reading the file
			while ((lineOfInfo = br.readLine()) != null) {
				String [] splitting = new String [2]; 
				splitting = lineOfInfo.split(",");

				//adding the names to one list and scores to another
				name.add(splitting[0]);
				score.add(Double.parseDouble(splitting[1])); 
			}	
			//closing the stream
			fr.close();
			br.close();

			//bubble sorting the data from the file
			bubbleSort(score, name);

			String full="Top 10 Scores\n\n"; 
			winner.setFont(title);

			DecimalFormat df = new DecimalFormat("##.00");

			//writing the top 10 scores to a label
			if (name.size() < 10) { 
				for (int i = 0; i < name.size(); i++) {
					full += (i+1) + ". " + name.get(i) + " " + df.format(score.get(i)) + "\n";
				}
			}
			else {
				for (int i = 0; i < 10; i++) {
					full += (i+1) + ". " + name.get(i) + " " + df.format(score.get(i)) + "\n";
				}
			}

			//formatting the vbox
			vb.getChildren().addAll(addName, leaveGame); 
			vb.setSpacing(20);
			vb.setAlignment(Pos.CENTER);

			//formatting the winner label
			winner.setText(full);
			winner.setTextFill(Color.WHITE);
			
			//formatting the HBox
			hb.getChildren().addAll(winner, vb); 
			hb.setSpacing(20);
			hb.setAlignment(Pos.CENTER);
			hb.setLayoutX((menuBg.getWidth()/2) - 200);
			root.getChildren().add(hb);

			winner.setLayoutX(300);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//bubble sorting algorithm
	public void bubbleSort (ArrayList <Double> score, ArrayList <String> name) {		
		for (int end = score.size()-1; end > 0; end--) {
			boolean done = true;

			//sorting from lowest to highest
			for (int i = 0; i < end; i++) {
				if (score.get(i).compareTo(score.get(i+1)) > 0) {
					done = false;
					double temp = score.get(i); 
					String nameTemp = name.get(i);

					name.add(i, name.get(i+1)); 
					name.remove(i+1); 
					name.add(i+1, nameTemp);
					name.remove(i+2); 

					score.add(i, score.get(i+1)); 
					score.remove(i+1);
					score.add(i+1, temp); 
					score.remove(i+2); 
				}
			}
			if (done) {
				break;
			}
		}
	}

	//winscreen
	public void winner () {
		//initializing layouts
		VBox winScreen = new VBox(); 
		HBox buts = new HBox(); 

		root.getChildren().clear();

		//initializing and formatting the you win label
		youWin = new Label(); 
		youWin.setText("YOU WIN");
		youWin.setFont(bigtitle);
		youWin.setTextFill(Color.WHITE);

		story = new Button();

		//initializing and formatting the button for the scoreboard
		scoreboard = new Button(); 
		scoreboard.setText("Scoreboard"); 
		scoreboard.setFont(title); 
		scoreboard.setTextFill(Color.WHITE); 
		scoreboard.setStyle("-fx-background-color: blue"); 
		scoreboard.setOnAction(e -> scoreBoard()); 
		scoreboard.setLayoutX((menuBg.getWidth()/2) - (scoreboard.getWidth()/2));
		scoreboard.setLayoutY((menuBg.getHeight()/2) - (scoreboard.getHeight()/2));

		//initializing and formatting the button for adding name
		addName = new Button();
		addName.setText("Add My Name"); 
		addName.setFont(title); 
		addName.setTextFill(Color.WHITE); 
		addName.setStyle("-fx-background-color: blue"); 
		addName.setOnAction(e -> addingName());	
		addName.setLayoutX((menuBg.getWidth()/2) - (addName.getWidth()/2));
		addName.setLayoutY((menuBg.getHeight()/2) - (addName.getHeight()/2));

		//formatting the VBox
		winScreen.setSpacing(20); 
		winScreen.setLayoutX(280);
		winScreen.setLayoutY(100);
		winScreen.setAlignment(Pos.CENTER);

		//formatting the HBox
		buts.setSpacing(20); 
		buts.setAlignment(Pos.CENTER); 
		buts.getChildren().addAll(scoreboard, leaveGame); 
		winScreen.getChildren().addAll(youWin, buts);

		root.getChildren().add(winScreen);

		scoreKeep.stop();
	}

	public void addingName() {
		//ensuring the user only adds their name once
		if (onlyOnce <= 0) {
			//initializing image
			Image img = new Image("file:images/random/alertName.png"); 
			ImageView ivImg = new ImageView(img); 

			//input dialog for user to enter their name
			TextInputDialog inp = new TextInputDialog();
			inp.setHeaderText(null);
			inp.setTitle("Add Name"); 
			inp.setContentText("Enter your name: ");
			inp.setGraphic(ivImg);

			Optional <String> result = inp.showAndWait(); 

			FileWriter fw;
			try {
				//initializing filewriter and bufferedwriter
				fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw); 

				conversion = fullTime / 60.0; 

				//writing the user's name and score
				bw.write(result.get() + "," + conversion); 
				bw.newLine();

				bw.close(); 

				//adding their name and score to the lists to sort them
				name.add(result.get());
				score.add(conversion); 
				bubbleSort(score, name);

				//finding what place on the list the user would be
				int place = (Collections.binarySearch(score, conversion) + 1);

				//using an alert to tell the user what place they are on the leaderboard
				Alert msg = new Alert(AlertType.INFORMATION); 
				msg.setContentText("You are in place " + place + " on the leaderboard. Replay the game to see your name.");
				msg.setHeaderText(null);
				msg.setTitle("Place on Scoreboard");
				msg.showAndWait();
				msg.setGraphic(ivImg);
				onlyOnce++; 

			} catch (IOException e) {
				e.printStackTrace();
			} 	
		}

	}
	//leaving the game;
	public void leave() { 
		//confirming the user wants to leave the game with an alert
		Alert al = new Alert(AlertType.CONFIRMATION); 
		al.setContentText("Are you sure you want to leave?");
		al.setTitle("Leave Confirmation");
		al.setHeaderText(null);
		al.setGraphic(ivError);

		//closing the game if they want to leave
		Optional <ButtonType> result = al.showAndWait(); 
		if (result.get() == ButtonType.OK) {
			Platform.exit(); 
		}
		//closing the alert if they do not want to leave
		else if (result.get() == ButtonType.CANCEL) {
			al.close();
		}
	}

}