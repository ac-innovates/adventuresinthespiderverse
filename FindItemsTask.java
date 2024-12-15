package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

//inheriting MiniTasks
public class FindItemsTask extends MiniTasks{
	//fields
	Image [] toFind;
	ImageView [] ivFind; 
	ArrayList <ImageView> selected;
	int separate= 30, counter; 
	Rectangle [] imgBg, checkFound;
	Image celeb; 
	ImageView  [] ivCeleb; 
	AudioClip found;
	
	//constructor
	public FindItemsTask() { 
		//initializing all variables and objects
		bg = new Image("file:images/backgrounds/miniTasks/findImages/findItemsBg.png"); 
		ivBg = new ImageView(bg); 
		
		toFind = new Image [10]; 
		ivFind = new ImageView [10]; 
		imgBg = new Rectangle [5];
		checkFound = new Rectangle [5]; 
		
		found = new AudioClip("file:sounds/findCorrect.wav"); 
		
		celeb = new Image("file:images/backgrounds/miniTasks/findImages/sparkleCorrect.gif");
		ivCeleb = new ImageView [5];
		
		//initializing and formatting the celebration image
		for (int i = 0; i < ivCeleb.length; i++) {
			ivCeleb[i] = new ImageView(celeb); 
			ivCeleb[i].setPreserveRatio(true);
		}
		
		selected = new ArrayList <ImageView> (); 
		
		for (int i = 0; i < toFind.length; i++) {
			toFind[i] = new Image("file:images/backgrounds/miniTasks/findImages/img " + (i+1) + ".png");
			ivFind[i] = new ImageView(toFind[i]);
			selected.add(ivFind[i]); 
		}
		
		stage = new Stage();
		root = new Pane();
		scene = new Scene(root, bg.getWidth(), bg.getHeight()); 
		
		lbl = new Label();
		
		
	}
	
	//methods
	//setting up the stage, scene, and formatting them
	public void setUp() {
		openStage(); 
		stage.setScene(scene);
	 
		//adding the nodes to the pane
		root.getChildren().addAll(getNode(), getInstructions()); 
		
		//shuffling the images
		Collections.shuffle(selected);
		
		for (int i = 0; i < 5; i++) {
			//setting the position of the images
			selected.get(i).setLayoutX(bg.getWidth() - 80);
			selected.get(i).setLayoutY(separate);
			
			//formatting the images
			selected.get(i).setPreserveRatio(true); 
			selected.get(i).setFitHeight(50); 
			
			//creating backgrounds for the images
			imgBg[i] = new Rectangle(bg.getWidth()-85, separate-5, 60, 60);
			imgBg[i].setFill(Color.WHITE);
			root.getChildren().addAll(imgBg[i], selected.get(i)); 
			
			//creating a mask based on which images were selected from the list
			if (selected.get(i) == ivFind[0]) {
				checkFound[i] = new Rectangle (190, 140, 70, 80); 
			}
			else if (selected.get(i) == ivFind[1]) {
				checkFound[i] = new Rectangle (820, 131, 60, 70); 
			}
			else if (selected.get(i) == ivFind[2]) {
				checkFound[i] = new Rectangle (70, 303, 101, 111); 
			}
			else if (selected.get(i) == ivFind[3]) {
				checkFound[i] = new Rectangle (686, 438, 30, 30); 
			}
			else if (selected.get(i) == ivFind[4]) {
				checkFound[i] = new Rectangle (445, 311, 45, 71); 
			}
			else if (selected.get(i) == ivFind[5]) {
				checkFound[i] = new Rectangle (1100, 507, 90, 118); 
			}
			else if (selected.get(i) == ivFind[6]) {
				checkFound[i] = new Rectangle (699, 383, 26, 16); 
			}
			else if (selected.get(i) == ivFind[7]) {
				checkFound[i] = new Rectangle (245, 264, 52, 38); 
			}
			else if (selected.get(i) == ivFind[8]) {
				checkFound[i] = new Rectangle (276, 565, 38, 49); 
			}
			else if (selected.get(i) == ivFind[9]) {
				checkFound[i] = new Rectangle (97, 420, 41, 44); 
			}
			
			int index = i; 
			separate += 70;
			//adding the masks and 
			root.getChildren().add(checkFound[i]);
			
			//formatting the masks
			checkFound[i].setFill(Color.TRANSPARENT);
			
			//checking if the masks have been clicked
			checkFound[i].setOnMouseClicked(new EventHandler <MouseEvent> () {
				public void handle (MouseEvent e) {
					//adding confetti to the pane if the user clicks the right spot
					found.play();
					root.getChildren().add(ivCeleb[index]); 
					ivCeleb[index].setX(checkFound[index].getX());
					ivCeleb[index].setY(checkFound[index].getY());
					ivCeleb[index].setFitHeight(checkFound[index].getHeight()); 
					root.getChildren().remove(checkFound[index]); 
					counter++; 

					//checking if the user has clicked all 5 images indicating they won
					if (counter == 5) {
						winner = true;
						ivWin.setFitWidth(bg.getWidth());
						winCheck(); 
					}
				}
			});
		}

		//showing the stage
		stage.show(); 
				
		//introductory alert
		getAlert();
	}
	
	public Label getInstructions() {
		lbl.setFont(title);
		lbl.setTextFill(Color.WHITE); 
		lbl.setText("Some items here aren't from this universe! Find and click them to send them back to where they belong.");
		
		return lbl;
	}
	
	//introductory alert
	public void getAlert() {
		Optional <ButtonType> result = al.showAndWait(); 
		
		while (true) {
            result = al.showAndWait();
            if (result.isPresent()) {
                ButtonType buttonType = result.get();
                //checking if the control button was pressed
                if (buttonType == control) {
                    al.setContentText("Use your mouse to click the image.");
                }
                //checking if the instructions button was pressed
                else if (buttonType == instructions) {
                    al.setContentText("The items on the right are not from this universe! Click them to send them back to where they belong and restore balance.");
                }
                //checking if the close button was pressed
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
