package application;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//extending MiniTasks
public class StabilizeTask extends MiniTasks{
	//fields
	private Image [] pieces; 
	private ImageView [] ivPieces;
	private int counter=0; 
	
	//constructor
	public StabilizeTask() {
		//initializing inherited values
		stage = new Stage(); 
		bg = new Image("file:images/backgrounds/miniTasks/stabilizeBg.png"); 
		ivBg = new ImageView(bg);
		root = new Pane(); 
		scene = new Scene(root, bg.getWidth(), bg.getHeight()); 
		lbl = new Label(); 
		
		//initializing array for images
		pieces = new Image [3]; 
		ivPieces = new ImageView[3]; 
		
		//formatting and initializing images
		for (int i = 0; i < pieces.length; i++) {
			pieces[i] = new Image("file:images/backgrounds/miniTasks/stabilize " + (i+1) + ".png"); 
			ivPieces[i] = new ImageView(pieces[i]); 
			ivPieces[i].setLayoutX(400); 
			ivPieces[i].setPreserveRatio(true); 
		}
		
		//setting the size of the images 
		ivPieces[0].setFitHeight(505); 
		ivPieces[1].setFitHeight(305); 
		ivPieces[2].setFitHeight(170); 
	}
		
	//label for instructions
	public Label getInstructions() {
		lbl.setText("The portals have been de-stabilized! Re-align the hexagons to their correct position to fix the portal!");
		lbl.setTextFill(Color.WHITE);
		lbl.setLayoutX(20); 
		lbl.setFont(title);
		lbl.setAlignment(Pos.CENTER);
		
		return lbl;
	}
	
	//returning nodes for pieces
	public ImageView [] getNodes() {
		return ivPieces;
	}
	//setting up the task
	public void setUp() {
		//opening the stage
		openStage(); 
		
		//setting the scene to the stage
		stage.setScene(scene);
		
		//adding the nodes to the pane
		root.getChildren().clear();
		root.getChildren().addAll(getNode(), getInstructions()); 
		
		//adding the hexagons to the pane
		for (int i = 0; i < ivPieces.length; i++) {
			root.getChildren().add(ivPieces[i]); 
		}
		
		//showing the stage
		stage.show(); 
		
		//alert
		getAlert();
		
		gameChecker();
	}
	
	public void gameChecker() {
		for (int i = 0; i < ivPieces.length; i++) {
			final int index = i; 
			
			final double [] initialValX = new double[1]; 
			final double [] initialValY = new double[1]; 
			
			final boolean [] foundVal = new boolean [3]; 
			
			//checking if one of the pieces has been pressed
			ivPieces[index].setOnMousePressed(new EventHandler <MouseEvent> () {
				public void handle(MouseEvent e) {
					initialValX[0] = e.getX() - ivPieces[index].getX(); 
					initialValY[0] = e.getY() - ivPieces[index].getY(); 
				}
			});
			
			//checking if the image is being dragged
			ivPieces[index].setOnMouseDragged(new EventHandler <MouseEvent>() {
				public void handle(MouseEvent e) {
					ivPieces[index].setX(e.getX() - initialValX[0]);
					ivPieces[index].setY(e.getY() - initialValY[0]);
					
					//checking if the first image is in the right spot
					if (ivPieces[0].getX() >= -452 && ivPieces[0].getX() <= -447) {
						if (ivPieces[0].getY() <= 12 && ivPieces[0].getY() >= 8) {
							foundVal[0] = true; 
						}

					}
					
					//checking if the second image is in the right spot
					if (ivPieces[1].getX() >= -346 && ivPieces[1].getX() <= -342) {
						if (ivPieces[1].getY() <= 96 && ivPieces[1].getY() >= 92) {
							foundVal[1] = true; 
						}

					}
					
					//checking if the third image is in the right spot
					if (ivPieces[2].getX() >= -270 && ivPieces[2].getX() <= -267) {
						if (ivPieces[2].getY() <= 165 && ivPieces[2].getY() >= 161) {
							foundVal[2] = true; 
						}

					}		
					
					//locking the image in place if it is in the right spot
					if (foundVal[0] == true) {
						ivPieces[0].setX(-449.33); 
						ivPieces[0].setY(10); 
					}
					if (foundVal[1] == true) {
						ivPieces[1].setX(-344); 
						ivPieces[1].setY(94); 
					}
					if (foundVal[2] == true) {
						ivPieces[2].setX(-268); 
						ivPieces[2].setY(163); 
					}

					//checking if all images are in the right spot
					if (foundVal[0] == true && foundVal[1] == true && foundVal[2] == true) {
						if (counter <= 0) {
							//showing the winner screen
							winner = true; 
							winCheck(); 
							System.out.print(winner);
						}
						counter++; 
					}
				}
			});
	
		}
	}
	
	//introductory alert
	public void getAlert() {
		Optional <ButtonType> result = al.showAndWait(); 
		
		//alert showing options
		while (true) {
            result = al.showAndWait();
            if (result.isPresent()) {
                ButtonType buttonType = result.get();
                //checking if the control button was clicked
                if (buttonType == control) {
                    al.setContentText("Use your mouse to drag the image.");
                }
                //checking if the instructions button was clicked
                else if (buttonType == instructions) {
                    al.setContentText("Stabilize the portals by dragging the correct sized orange portal to its spot in the greyed out portal area");
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
