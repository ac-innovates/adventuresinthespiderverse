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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

//extending MiniTasks
public class PuzzleTask extends MiniTasks {
	//fields
	private Image [] pieces; 
	private ImageView [] ivPieces; 
	private int counter =0;
	private Rectangle [] slots;
	private HBox hSlots;
	private boolean [] imgPlaced;
	private ArrayList <ImageView> spawn; 
	private ArrayList <Integer> check;

	//constructor
	public PuzzleTask() {
		//initializing fields
		stage = new Stage(); 
		bg = new Image("file:images/backgrounds/miniTasks/puzzleImages/puzzleBg.png"); 
		ivBg = new ImageView(bg); 
		root = new Pane(); 
		lbl = new Label(); 
		
		//initializing arraylists
		spawn = new ArrayList <ImageView> (); 
		check = new ArrayList <Integer> (); 
		
		scene = new Scene(root, bg.getWidth(), bg.getHeight()); 
		
		//initializing arrays
		pieces = new Image [6]; 
		ivPieces = new ImageView [6]; 
		slots = new Rectangle [6]; 
		hSlots = new HBox(); 
		imgPlaced = new boolean [6]; 

		//formatting and initializing the puzzle pieces and adding them to pane
		int increase=30;
		for (int i = 0; i < pieces.length; i++) {
			pieces[i] = new Image("file:images/backgrounds/miniTasks/puzzleImages/" + i + ".jpg"); 
			ivPieces[i] = new ImageView(pieces[i]); 
			slots[i] = new Rectangle(increase, 130, 130, 343);
			hSlots.getChildren().add(slots[i]);
			slots[i].setFill(Color.GREY);
			spawn.add(ivPieces[i]); 
		}
		//formatting the HBox 
		hSlots.setSpacing(10); 
		hSlots.setLayoutX(28); 
		hSlots.setLayoutY(148); 
	}

	//setting up the task
	public void setUp() {
		//opening the stage
		openStage(); 
		
		//setting the scene to the stage
		stage.setScene(scene);	
		
		//shuffling the order of the puzzle pieces
		Collections.shuffle(spawn);
		
		//adding the nodes to the pane
		root.getChildren().addAll(getNode(), getInstructions(), hSlots); 
		
		//setting the position of the puzzle pieces
		for (int i = 0; i < ivPieces.length; i++) {
			ivPieces[i].setLayoutX(1000); 
			ivPieces[i].setLayoutY(100);
			
			root.getChildren().addAll(spawn.get(i)); 
		}
		
		//showing the stage
		stage.show(); 
		
		//showing the alert
		getAlert();
	}
	
	//returning the label with the instructions
	public Label getInstructions() {
		lbl.setFont(title);
		lbl.setTextFill(Color.WHITE);
		lbl.setText("Pieces of the multiverse have been split apart! Use your mouse and drag the pieces back to their place! If the image locks then the piece is in the correct spot.");
		
		return lbl; 
	}
	
	//game play
	public void gameChecker() {
		for (int i = 0; i < ivPieces.length; i++) {
			final int index = i; 
			
			final double [] initialValX = new double[1]; 
			final double [] initialValY = new double[1]; 
			
			//checking if a puzzle piece has been pressed
			ivPieces[index].setOnMousePressed(new EventHandler <MouseEvent> () {
				public void handle(MouseEvent e) {
					initialValX[0] = e.getX() - ivPieces[index].getX(); 
					initialValY[0] = e.getY() - ivPieces[index].getY(); 
				}
			});
			
			//checking if a puzzle pieces is being dragged
			ivPieces[index].setOnMouseDragged(new EventHandler <MouseEvent>() {
				public void handle(MouseEvent e) {
					ivPieces[index].setX(e.getX() - initialValX[0]);
					ivPieces[index].setY(e.getY() - initialValY[0]);
					
					//bringing the piece to the front
					ivPieces[index].toFront(); 
					
					//checking if the first piece is in the first slot
					if (ivPieces[0].getX() >= -978 && ivPieces[0].getX() <= -970) {
						if (ivPieces[0].getY() <= 49 && ivPieces[0].getY() >= 45) {
							imgPlaced[0] = true; 
						}
					}
					//checking if the second piece is in the second slot
					if (ivPieces[1].getX() >= -838 && ivPieces[1].getX() <= -830) {
						if (ivPieces[1].getY() <= 49 && ivPieces[1].getY() >= 45) {
							imgPlaced[1] = true; 
						}
					}
					//checking if the third piece is in the third slot
					if (ivPieces[2].getX() >= -696 && ivPieces[2].getX() <= -688) {
						if (ivPieces[2].getY() <= 49 && ivPieces[2].getY() >= 45) {
							imgPlaced[2] = true; 
						}
					}
					//checking if the fourth piece is in the fourth slot
					if (ivPieces[3].getX() >= -557 && ivPieces[3].getX() <= -549) {
						if (ivPieces[3].getY() <= 49 && ivPieces[3].getY() >= 45) {
							imgPlaced[3] = true; 
						}
					}
					//checking if the fifth piece is in the fifth slot
					if (ivPieces[4].getX() >= -414 && ivPieces[4].getX() <= -410) {
						if (ivPieces[4].getY() <= 49 && ivPieces[4].getY() >= 45) {
							imgPlaced[4] = true; 
						}
					}
					//checking if the sixth piece is in the sixth slot
					if (ivPieces[5].getX() >= -274 && ivPieces[5].getX() <= -270) {
						if (ivPieces[5].getY() <= 49 && ivPieces[5].getY() >= 45) {
							imgPlaced[5] = true; 
						}
					}
					

					//locking the images in place if the user is correct
					if (imgPlaced[0] == true) {
						ivPieces[0].setX(-974);
						ivPieces[0].setY(47);
					}
					
					if (imgPlaced[1] == true) {
						ivPieces[1].setX(-834);
						ivPieces[1].setY(47);
					}
					if (imgPlaced[2] == true) {
						ivPieces[2].setX(-692);
						ivPieces[2].setY(47);
					}
					if (imgPlaced[3] == true) {
						ivPieces[3].setX(-553);
						ivPieces[3].setY(47);
					}
					if (imgPlaced[4] == true) {
						ivPieces[4].setX(-412);
						ivPieces[4].setY(47);
					}
					if (imgPlaced[5] == true) {
						ivPieces[5].setX(-272);
						ivPieces[5].setY(47);
					}
	
					//adding a value to check if the image is placed correctly
					for (int i = 0; i < imgPlaced.length; i++) {
						if (imgPlaced[i] && !check.contains(i)) {
							counter++; 
							check.add(i); 
						}
					}
					
					//user wins if all 6 images are placed in the right spot
					if (counter == 6) {
						//showing the winner screen
						winner = true; 
						winCheck(); 
						counter=7;
					}
				}
			});

	
		}
	}
	
	//introductory alert
	public void getAlert() {
		Optional <ButtonType> result = al.showAndWait(); 
		
		while (true) {
            result = al.showAndWait();
            if (result.isPresent()) {
                ButtonType buttonType = result.get();
                //checking if the control button is clicked
                if (buttonType == control) {
                    al.setContentText("Use your mouse to drag the pieces.");
                }
                //checking if the instructions button is clicked
                else if (buttonType == instructions) {
                    al.setContentText("Pieces of the multiverse have been split apart! Drag the pieces back in their correct spot to avoid the multiverse from crumbling.");
                }
                //checking if the close button is clicked
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
