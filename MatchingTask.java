package application;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MatchingTask extends MiniTasks {
	//fields
	private Image [] pics;
	private Button [][] select;
	private boolean [][] booSelect;
	private Button selection; 
	private Random rnd; 
	private TilePane tp; 
	private int randPic;
	private GridPane gp; 
	private Label lbl; 
	private int [][] mark;
	private int [] picCheck; 
	private ArrayList <Integer> check; 
	
	//constructor
	public MatchingTask() {
		//initializing the fields
		bg = new Image("file:images/backgrounds/miniTasks/matchBg.jpg");
		ivBg = new ImageView(bg); 
		stage = new Stage(); 
		root = new Pane();
		tp = new TilePane(); 
		
		gp = new GridPane(); 
		rnd = new Random(); 
		
		picCheck = new int[4]; 
				
		select = new Button [5][5];
		booSelect = new boolean[5][5]; 
		mark = new int [5][5]; 
		scene = new Scene(root, bg.getWidth(), bg.getHeight()); 
		lbl = new Label();
				
		//initializing and formatting the selection button
		selection = new Button(); 
		selection.setFont(title);
		selection.setText("Make Selection"); 
		selection.setStyle("-fx-background-color: blue; -fx-text-fill: white"); 
		selection.setOnAction(e -> butHandle(e));
				
		pics = new Image [4]; 
		check = new ArrayList <Integer> (); 
		
		//setting the images of the pics array
		for (int i = 0; i < pics.length; i++) {
			pics[i] = new Image("file:images/backgrounds/miniTasks/pic " + (i+1) + ".jpg");
		}
		
	}
	
	public void setUp() {
//		gp.setGridLinesVisible(true);
		openStage(); 
		stage.setScene(scene);
		
		//initializing the 2D array, setting images to each of the buttons and formatting it 
		for (int rows = 0; rows < select.length; rows++) {
			for (int cols = 0; cols < select[rows].length; cols++) {
				randPic = rnd.nextInt(4);
				mark[rows][cols] = randPic; 
				ImageView ivPic = new ImageView(pics[randPic]); 
				booSelect[rows][cols] = false;
				select[rows][cols] = new Button(); 
				select[rows][cols].setGraphic(ivPic);
				select[rows][cols].setStyle("-fx-background-color: black");
				select[rows][cols].setOnAction(e -> butHandle(e));
				tp.getChildren().add(select[rows][cols]); 
				
				//checking how many pictures of each type there are
				if (randPic == 0) {
					picCheck[0]++; 
				}
				else if (randPic == 1) {
					picCheck[1]++; 
				}
				else if (randPic == 2) { 
					picCheck[2]++; 
				}
				else if (randPic == 3) {
					picCheck[3]++; 
				}
				
			}
		}
		//formatting the tile pane 
		tp.setVgap(10);
		tp.setHgap(10);
		tp.setPrefColumns(5);
		tp.setAlignment(Pos.CENTER);
		
		//formatting the grid pane
		gp.setVgap(30);
		gp.setHgap(40); 
		gp.setPadding(new Insets(10));
		
		//adding nodes to the grid pane
		gp.add(getInstructions(), 0, 0);
		gp.add(tp, 0, 1);
		gp.add(selection, 1, 1);
		
		//formatting and aligning nodes on the grid pane
		GridPane.setColumnSpan(getInstructions(), 2);
		GridPane.setHalignment(getInstructions(), HPos.CENTER);
		
		GridPane.setHalignment(selection, HPos.CENTER);
			
		//adding nodes to the pane
		root.getChildren().addAll(ivBg, gp); 

		//showing the stage
		stage.show(); 
		
		//positioning the gridPane
		gp.setLayoutX((bg.getWidth()/2) - (gp.getWidth()/2));
		
		//showing alert
		getAlert();
	}
	
	//instructions for task
	public Label getInstructions() {
		lbl.setFont(title);
		lbl.setTextFill(Color.WHITE);
		lbl.setText("The spiderverse has been disrupted! Pair the correct characters with each other to ensure their\ntimelines do not mix up!");
		lbl.setStyle("-fx-background-color: black");
		
		return lbl; 
	}
	
	public void butHandle(ActionEvent e) {
		for (int rows = 0; rows < select.length; rows++) {
			for (int cols = 0; cols < select[rows].length; cols++) {
				//selecting buttons if they have not been selected before
				if (e.getSource() == select[rows][cols] && booSelect[rows][cols] == false) {
					booSelect[rows][cols] = true;
					check.add(mark[rows][cols]); 
					select[rows][cols].setStyle("-fx-background-color: purple"); 
				}
			}
		}
				
		//confirming selection made
		if(e.getSource() == selection) {
			int checking; 
			if (check.size() >= 1) {
				checking = check.get(0); 
				
				Alert al = new Alert(AlertType.INFORMATION); 
				al.setHeaderText(null); 
				al.setTitle("Game Over");
				
				//checking if the user selected images that are different
				for (int i = 0; i < check.size(); i++) {
					if (check.get(i) != checking) {
						al.setContentText("You selected one or more images that were not the same! You lose.");
						al.setGraphic(ivAlert);
						al.showAndWait();
						lose = true;
						winCheck();
						break; 
					}
					else {
						//checking if user selected all similar images
						if (picCheck[checking] != check.size()) {
							al.setContentText("You missed one or more of the same images! You lose.");
							al.setGraphic(ivAlert); 
							al.showAndWait();
							lose = true;
							winCheck();
							
							stage.setWidth(ivWin.getFitWidth()); 
							stage.setHeight(ivWin.getFitHeight()); 
							
							break; 
						}
					}
				}
			}
			
			
			boolean doneOrNot=true; 
			for (int rows = 0; rows < select.length; rows++) {
				for (int cols = 0; cols < select[rows].length; cols++) {
					//confirming selection once it is confirmed that they have selected all correct images
					if (booSelect[rows][cols] == true) {
						select[rows][cols].setStyle("-fx-background-color: green");
					}
					else {
						doneOrNot = false; 
					}
				}
			}
			
			if(doneOrNot) {
				winner = true;
				winCheck();
			}
				
			check.clear(); 
		}		
	}
	
	public void clearPanes() {
		gp.getChildren().clear();
		root.getChildren().clear();
		tp.getChildren().clear(); 
		check.clear(); 
	}
	
	//introductory alert
	public void getAlert() {
		Optional <ButtonType> result = al.showAndWait(); 
		
		while (true) {
            result = al.showAndWait();
            if (result.isPresent()) {
                ButtonType buttonType = result.get();
                //checking if the user clicked the control button
                if (buttonType == control) {
                    al.setContentText("Use your mouse to click the matching buttons.");
                }
                //checking if the user clicked the instructions button
                else if (buttonType == instructions) {
                    al.setContentText("The spiderverse has been disrupted! Click ALL images that are the same and THEN click make selection to confirm your answer. If you miss any. it is game over.");
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
