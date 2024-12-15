package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//extending MiniTasks
public class SendBackTask extends MiniTasks {
	//fields
	private Image portalImg;
	private Image [] enemies, imgIcons; 
	private ArrayList <Image> icons; 
	private ImageView ivPortal, ivIcon, ivEnemy;
	private BorderPane bp; 
	private Player play; 
	private boolean added, found, clicked;
	private AnimationTimer timer;
	private Label database;
	private RadioButton [] options;
	private GridPane gp; 
	private int visible, selected; 
	private ToggleGroup tg; 
	
	//constructor
	public SendBackTask() {
		//initializing values inherited from MiniTasks
		stage = new Stage(); 
		root = new Pane(); 
		bg = new Image("file:images/backgrounds/miniTasks/sendBackBg.jpg"); 
		ivBg = new ImageView(bg);
		lbl = new Label();
		
		//initializing layouts
		bp = new BorderPane(); 
		gp = new GridPane();
		
		database = new Label(); 
		
		scene = new Scene(root, bg.getWidth(), bg.getHeight()); 
		
		//initializing arrays
		enemies = new Image [4]; 
		imgIcons = new Image [4]; 
		
		icons = new ArrayList <Image> (); 
		
		//initializing ImageViews
		ivIcon = new ImageView(); 
		ivEnemy = new ImageView(); 
		
		//setting the images for the enemies and their icons
		for (int i = 0; i < enemies.length; i++) {
			enemies[i] = new Image("file:images/backgrounds/miniTasks/chuteImages/enemy " + (i+1) + ".png"); 
			imgIcons[i] = new Image("file:images/backgrounds/miniTasks/chuteImages/icon " + (i+1) + ".png");
			icons.add(imgIcons[i]); 
		}
		
		//shuffling the order of the icons
		Collections.shuffle(icons); 
		
		//formatting the icons
		ivIcon.setPreserveRatio(true);
		ivIcon.setFitHeight(60); 
		
		//formatting the enemy images
		ivEnemy.setPreserveRatio(true); 
		ivEnemy.setFitHeight(100); 
		
		//initializing image of portal
		portalImg = new Image("file:images/backgrounds/miniTasks/chuteImages/portal.png"); 
		ivPortal = new ImageView(portalImg); 
				
		play = new Player(1);
		
		options = new RadioButton [4]; 
		
		//toggle so only one radio button can be pressed at a time
		tg = new ToggleGroup(); 

		//initializing and formatting the radio buttons
		for (int i = 0; i < options.length; i++) {
			options[i] = new RadioButton(); 
			options[i].setFont(subtitle); 
			options[i].setStyle("-fx-text-fill: white");
			options[i].setOnAction(e -> handleRb()); 
			tg.getToggles().add(options[i]); 

			gp.add(options[i], 0, i);
		}
		
		//setting the text of the radio buttons
		options[0].setText("Doctor Octopus"); 
		options[1].setText("Green Goblin"); 
		options[2].setText("Vulture");
		options[3].setText("Electro"); 		        
	}
	
	//setting up the task
	public void setUp() {
		//opening the stage
		openStage(); 
		
		//setting the scene to the stage
		stage.setScene(scene);	
		
		//setting nodes to the borderpane
		bp.setTop(getInstructions());
		bp.setLeft(ivPortal);
		bp.setRight(database);
		
		//formatting the border pane
        BorderPane.setMargin(database, new Insets(0, 50, 0, 0));
        
        //setting the position of the icon
        ivIcon.setImage(icons.get(0));
        ivIcon.setX(200);
        ivIcon.setY(100);
        
        //checking which icon is visible at the moment
        for (int i = 0; i < icons.size(); i++) {
        	if (icons.get(0) == imgIcons[i]) {
        		visible = i;
        	}
        }
		
        //adding the nodes to the pane
		root.getChildren().addAll(ivBg, bp, play.getNode(), ivIcon); 
		
		//formatting the grid pane
		gp.setLayoutX(580);
		gp.setLayoutY(170);
		gp.setPrefWidth(165);
		
		gp.setStyle("-fx-background-color: red");
		
		//setting the position of the player
		play.setX(700); 
		play.setY(250);
		
		//formatting the database label
		database.setFont(title);
		database.setText("Official Database");
		database.setTextFill(Color.WHITE);
		database.setStyle("-fx-background-color: black");
		database.setPrefSize(165, 50);

		//showing the stage
		stage.show(); 
		
		//returning the alert
		getAlert(); 
		
		//movement of player
        movement(); 
        timerMovement(); 
		
        //starting timer
        timer.start();
	}
	//returning instructions of task
	public Label getInstructions() {
		lbl.setTextFill(Color.WHITE);
		lbl.setText("Send the character in the image below back to its proper universe. Find the character in the database and\nuse your WASD keys to pick up and drop off the character into the portal. Press space to enter\nthe database.");
		lbl.setFont(title);
		
		return lbl; 
	}
	
	//handling the radio button if pressed
	public void handleRb() {
		root.getChildren().remove(ivEnemy); 
		
		//checking which of the radio buttons was selected	
		for (int i = 0; i < options.length; i++) {
			if (options[i].isSelected()) {
				ivEnemy.setImage(enemies[i]);
				selected = i; 
			}
		}
		
		//setting the position of the enemy to the player's position
		ivEnemy.setX(play.getX());
		ivEnemy.setY(play.getY());

		root.getChildren().add(ivEnemy);
		clicked = true;
	}
	
	//moving the player and enemy
	public void timerMovement() {
		 timer = new AnimationTimer() {
	            @Override
	            public void handle(long now) {
	            	int valX = 0, valY=0; 
	            	//ensuring the player is moving within the screen and changing the image based on the direction
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
	            	//changing the image if the player is attacking
	            	if (play.attacking == true) {
						play.setPlayerImage(); 									
					}
	            	else {
	            		play.setPlayerImage(); 
	            	}

	            	//moving the player
					play.move(valX, valY); 
					
					//checking if the player intersects the database
					if (play.getNode().getBoundsInParent().intersects(database.getBoundsInParent())) {
						database.setStyle("-fx-background-color: red");
						if (added == false) {
							//showing the radio buttons
							root.getChildren().add(gp); 
							added = true; 
						}
					}
					else {
						database.setStyle("-fx-background-color: black"); 
						added = false; 
						root.getChildren().remove(gp); 
					}
					//setting the position of the enemy to the player's 
					ivEnemy.setX(play.getX());
					ivEnemy.setY(play.getY());
					
					//bringing the player to the front of pane
					play.getNode().toFront(); 
					
					//checking if the enemy intersects the portal
					if (ivEnemy.getBoundsInParent().intersects(ivPortal.getBoundsInParent()) && !found && clicked) {
						ivEnemy.setLayoutX(-1000); 
						found = true;
					}
					else {
						for (int i = 0; i < options.length; i++) {
							if (options[i].isSelected()) { 
								ivEnemy.setLayoutX(0);
								ivEnemy.setX(play.getX());
								ivEnemy.setY(play.getY());
							}
						}		
					}
					//checking if all icons have been added to the portal
					if (icons.size() == 0) {
						//displaying the win screen
						winner = true;
						winCheck(); 
						icons.add(loseImg);
						found = false;
						
					}
					
					//checking if the right image was found
					if (found == true) {
						if (imgIcons[visible] == imgIcons[selected]) {
							//removing the icon and enemy
							root.getChildren().remove(ivEnemy); 
							root.getChildren().remove(ivIcon);
						
							icons.remove(imgIcons[visible]); 

							if (icons.size() >= 1) {
								ivIcon.setImage(icons.get(0));
							}
							
							root.getChildren().addAll(ivIcon); 
							
							//checking which icon is currently visible
							if (icons.size() >= 1) {
								for (int i = 0; i < imgIcons.length; i++) {
									if(imgIcons[i] == icons.get(0)) {
										visible = i; 
									}
								}
							}
							
							//removing the selection from the radio button
							tg.selectToggle(null);
						}
						else {
							//user has lost, showing the lose screen
							lose = true;
							winCheck(); 
							
							//setting the stage size to the size of the lose screen
							stage.setWidth(ivWin.getFitWidth()); 
							stage.setHeight(ivWin.getFitHeight()); 
						}
						selected = -1; 
						found = false;	
						
						ivEnemy.setX(play.getX());
						ivEnemy.setY(play.getY());
						
						
					}
					
	            }
	        };
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
                    al.setContentText("Use WASD to bring the enemies to the portal. Use your mouse to the click the buttons within the database.");
                }
                //checking if the user clicked the instructions button
                else if (buttonType == instructions) {
                    al.setContentText("Send the villains back to their correct universe! Use WASD to walk to the database, click the correct button, then use your keys to walk to the portal.");
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
}
