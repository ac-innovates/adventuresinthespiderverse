package application;

import java.util.Optional;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

class CodeTask extends MiniTasks{
	//fields
	TextField input; 
	Random rnd; 
	int [] passChecks; 
	Label randCode;
	Timeline type;
	int index, point, check;
	String typing="";
	Button ready, submit;
	VBox vb;
	
	//constructor
	public CodeTask() {
		
		//setting default values
		bg = new Image("file:images/backgrounds/miniTasks/codeBg.jpg"); 
		ivBg = new ImageView(bg); 
		
		stage = new Stage(); 
		root = new Pane(); 
		scene = new Scene(root, bg.getWidth(), bg.getHeight()); 
		
		lbl = new Label(); 
		
		ready = new Button(); 
		
		vb = new VBox(); 
		
		//formatting the ready button
		ready.setText("READY"); 
		ready.setTextFill(Color.WHITE); 
		ready.setFont(title); 
		ready.setStyle("-fx-background-color: black"); 
		ready.setOnAction(e -> bPressed(e)); 
		
		submit = new Button(); 
		
		//formatting the submit button
		submit.setText("submit"); 
		submit.setTextFill(Color.WHITE); 
		submit.setFont(subtitle); 
		submit.setStyle("-fx-background-color: black"); 
		submit.setOnAction(e -> bPressed(e)); 
		
		//formatting the label with the random code
		randCode = new Label(); 
		randCode.setFont(bigtitle);
		randCode.setTextFill(Color.WHITE);
		randCode.setText(""); 
		
		input = new TextField(); 
		
		rnd = new Random(); 
		
		passChecks = new int[3]; 
		
		//setting random values for the random number 
		for (int i = 0; i < passChecks.length; i++) {
			passChecks[i] = rnd.nextInt(90000) + 10000; 
		}
	}
	
	//methods 
	public void setUp() {
		
		//opening the new stage and formatting it 
		openStage(); 
		stage.setScene(scene);
						
		//setting the position of the VBox
		vb.setLayoutX((bg.getWidth()/2) - 90);
		vb.setLayoutY(120); 
		vb.setSpacing(10); 
		
		//setting the position of the ready button 
		ready.setLayoutX(560); 
		ready.setLayoutY(145); 
		
		//adding the random code to the pane 
		vb.getChildren().add(randCode);
		
		//adding nodes to the pane 
		root.getChildren().addAll(getNode(), getInstructions(), ready, vb); 
		
		stage.show(); 
		getAlert(); 
		
		//generating the code
		codeGeneration(); 
	}
	
	//returning the label with the instructions for the task
	public Label getInstructions() {	
		lbl.setFont(title);
		lbl.setTextFill(Color.WHITE);
		lbl.setPrefWidth(bg.getWidth());
		lbl.setTextAlignment(TextAlignment.CENTER);
		lbl.setText("Enter in the passwords to have access to the Go-Home-Machine. Press ready when you would like to begin."); 

		return lbl; 
	}
	
	//generating the random code to the screen
	public void codeGeneration() {
		KeyFrame kf = new KeyFrame(Duration.millis(1000), new EventHandler <ActionEvent> () {
			public void handle (ActionEvent e) {
				randCode.setFont(bigtitle);
				check++; 
				
				//typing the code with each number per second
				if (index < 5) {
					typing += (Integer.toString(passChecks[point])).charAt(index) + " ";
					index++; 
					randCode.setText(typing); 
					
				}
				if (check == 6) {
					randCode.setText("Enter the code:");
					randCode.setFont(title);
					vb.getChildren().addAll(input, submit); 
				}
			}
		}); 
		
		//timeline for the keyframe
		type = new Timeline(kf); 
		type.setCycleCount(6);
	}
	
	//checking if a button has been pressed
	public void bPressed(ActionEvent e) { 
		
		//checking if the ready button was pressed
		if (e.getSource() == ready) {
			type.play(); 
			ready.setStyle("-fx-background-color: blue"); 
			ready.setDisable(true);
		}
		
		//checking if the submit button was pressed
		if (e.getSource() == submit) {
			
			//showing an alert indicating the user is correct and formatting it 
			Alert alrt = new Alert(AlertType.INFORMATION); 
			alrt.setHeaderText(null);
			alrt.setGraphic(ivAlert);
			if (input.getText().equals(Integer.toString(passChecks[point]))) {
				alrt.setContentText("Correct! Great Job.");
				alrt.showAndWait();
				typing="";
				randCode.setText(typing);

				point++; 
				index=0;
				check=0;
				
				input.clear();
				vb.getChildren().removeAll(input, submit); 
				type.play();
				
				//checking if the user got all three codes correct
				if (point == 3) {
					winner=true;
					type.stop(); 
					winCheck(); 
				}
			}
			//if the user's code is not correct, then they lose
			else {
				alrt.setContentText("Wrong code. You lose.");
				alrt.showAndWait(); 
				lose = true;
				winCheck(); 
			
				stage.setWidth(ivWin.getFitWidth()); 
				stage.setHeight(ivWin.getFitHeight()); 
			}

		}
	}
	
	public void getAlert() { 
Optional <ButtonType> result = al.showAndWait(); 
		
		//showing the alert 
		while (true) {
            result = al.showAndWait();
            if (result.isPresent()) {
                ButtonType buttonType = result.get();
                
                //checking what button in the alert the user clicks
                if (buttonType == control) {
                    al.setContentText("Use your keyboard to type the numbers into the textfield.");
                }
                else if (buttonType == instructions) {
                    al.setContentText("After pressing ready, the password will appear. Memorize it so that you can enter it into the prompt and access the Go-Home-Machine!");
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
	}

}
