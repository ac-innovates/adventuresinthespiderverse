package application;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MiniTasks {
	//fields
	Stage stage; 
	Image bg, winImg, loseImg, alertImg;
	ImageView ivBg, ivWin, ivAlert; 
	Scene scene; 
	Font title, subtitle, bigtitle; 
	Pane root;
	boolean winner, lose;
	Timeline tl;
	Label lbl;
	Alert al;
	ButtonType control, instructions, close;
	boolean up, down, left, right;
	Player play;
	
	//constructor
	public MiniTasks() {
		//initializing the images
		bigtitle = Font.loadFont("file:images/backgrounds/miniTasks/miniLevelTitles.ttf", 40); 
		title = Font.loadFont("file:images/backgrounds/miniTasks/miniLevelTitles.ttf", 22); 
		subtitle = Font.loadFont("file:images/backgrounds/miniTasks/miniLevelTitles.ttf", 15); 

		winImg = new Image("file:images/backgrounds/miniTasks/confetti.gif");
		loseImg = new Image("file:images/backgrounds/loseScreen.png"); 
		ivWin = new ImageView(winImg); 
		
		alertImg = new Image("file:images/random/alertImg.png"); 
		ivAlert = new ImageView(alertImg); 
		
		//initializing the buttontypes for the alerts
		control = new ButtonType("Controls");
		instructions = new ButtonType("Instructions"); 
		close = new ButtonType("Close"); 
		
		//initializing the player
		play = new Player(0);
		
		//initializing and formatting the introduction alert
		al = new Alert(AlertType.INFORMATION); 
		al.setHeaderText(null);
		al.getButtonTypes().clear(); 
		al.setGraphic(ivAlert);
		al.getButtonTypes().addAll(control, instructions, close); 
		al.setContentText("Select from below.");
		al.setTitle("Introduction"); 
		
		//formatting the win image
		ivWin.setPreserveRatio(true);
		ivWin.setFitWidth(1000); 
		
		//initializing the stage
		stage = new Stage(); 
	}
	
	//returning the stage
	public Stage openStage() { 
		return stage;
	}
	
	//returning the scene
	public Scene getScene() {
		return scene;
	}
	
	//returning the imageview
	public ImageView getNode() {
		return ivBg; 
	}
	
	//checking if the user has won or lost
	public void winCheck() {
		if (winner) {			
			ivWin.setPreserveRatio(true);

			root.getChildren().add(ivWin); 

			//showing the win screen for 2 seconds then closing 
			KeyFrame kf = new KeyFrame(Duration.millis(2000), new EventHandler <ActionEvent> (){
				public void handle(ActionEvent e) {
					root.getChildren().clear(); 
					stage.close(); 
				}
			});
			
			//timeline for keyframe
			tl = new Timeline(kf);
			tl.setCycleCount(1);
			tl.play(); 
		}
		
		//checking if the user has lost
		if (lose) {
			root.getChildren().clear(); 
			
			//formatting the image
			ivWin.setImage(loseImg);
			
			ivWin.setFitWidth(667);
			ivWin.setFitHeight(500); 
			
			//setting the size of the stage to the game over screen
			stage.setWidth(ivWin.getFitWidth());
			stage.setHeight(ivWin.getFitHeight());
			
			root.getChildren().add(ivWin);
		}
	}
	
	//movement of player using W A S D
	public void movement() {
		scene.setOnKeyPressed(new EventHandler <KeyEvent> () { 
			public void handle(KeyEvent e) {
				//checking which key the user has pressed
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
		        	play.setPlayerImage(); 
		        }
			}
		}); 
		//checking which key the user has released
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
	
	
}
