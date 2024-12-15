package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class TheStories {
	//fields
	private Image m1, g1, o1, p1; 
	private Image [] beg; 
	private ImageView ivImg, ivBeg;
	public Timeline play, tl;
	boolean done; 
	private Label who, storyline; 
	private int level, counter=0; 
	private Font bigtitle, title;
	
	//constructor
	public TheStories(int lev) {
		//initializing the images
		m1 = new Image("file:images/stories/introMiles.gif");
		g1 = new Image("file:images/stories/introGwen.gif");
		o1 = new Image("file:images/stories/introMiguel.gif");
		p1 = new Image("file:images/stories/introPeter.gif"); 
		
		beg = new Image [4]; 
		for (int i = 0; i < 3; i++) {
			beg[i] = new Image("file:images/stories/img" + (i+1) + ".png"); 
		}
		beg[3] = new Image("file:images/stories/controls.png");
		ivBeg = new ImageView(beg[0]);
		
		ivBeg.setFitHeight(450);
		ivBeg.setFitWidth(800); 
		
		ivImg = new ImageView(m1);
		
		//initializing fonts
		bigtitle = Font.loadFont("file:images/backgrounds/miniTasks/miniLevelTitles.ttf", 40); 
		title = Font.loadFont("file:images/backgrounds/miniTasks/miniLevelTitles.ttf", 22); 
		
		//setting the size of the image
		ivImg.setFitWidth(m1.getWidth());
		ivImg.setFitHeight(m1.getHeight());
		
		storyline = new Label(); 
		storyline.setFont(title); 
		storyline.setTextFill(Color.WHITE);
		storyline.setLayoutY(ivBeg.getFitHeight() + 10);
		storyline.setText("The multiverse is collapsing! Enemies have escaped everywhere and it is up to you to stop them.");
		storyline.setLayoutX(35);
		
		who = new Label();
		
		level = lev;
	}
	
	//changing the image based on the level
	public void changeImage() {
		if (level == 1) {
			ivImg.setImage(m1);
		}
		else if (level == 2) {
			ivImg.setImage(p1);
		}
		else if (level == 3) {
			ivImg.setImage(g1);
		}
		else if (level == 4) {
			ivImg.setImage(o1);
		}
	}
	
	//setting the amount of time the story is played
	public void story() {
		KeyFrame kf = new KeyFrame(Duration.millis(4000), new EventHandler <ActionEvent> () {
				public void handle(ActionEvent e) {
					done = true; 
				}
		});
		
		play = new Timeline(kf);
		play.setCycleCount(1); 
		play.play();
	}
	
	//returning the image
	public ImageView getNode() {
		return ivImg; 
	}
	
	//boolean for checking when the story is done
	public boolean doneStory() {
		return done; 
	}
	
	//displaying what character is used in the level
	public Label meet() {
		//formatting the label 
		who.setFont(title);
		who.setTextFill(Color.WHITE);
		who.setLayoutX(ivImg.getFitWidth()/2 - 150);
		who.setLayoutY(ivImg.getFitHeight() + 5); 
		//setting the text of the label based on the level
		if (level == 1) {
			who.setText("Meet Miles Morales from Earth-1610!");
		}
		else if (level == 2) {
			who.setText("Meet Peter Parker from Earth-616!");
		}
		else if (level == 3) {
			who.setText("Meet Gwen Stacy from Earth-65!");
		}
		else if (level == 4) {
			who.setText("Meet Miguel O'Hara from Earth-928!");
		}
		
		return who; 
	}
	
	public void intro() {
		//changing the story page on a timer
		KeyFrame kf = new KeyFrame(Duration.millis(4000), new EventHandler <ActionEvent> () {
			public void handle(ActionEvent e) {
				introChange(); 
				labelChange();
				counter++; 
			}
		});
		
		//key frame for timeline
		tl = new Timeline(kf); 
		tl.setCycleCount(5); 
		tl.play(); 
	}
	
	//changing the image for the story 
	public void introChange() {
		for (int i = 0; i < 4; i++) {
			if (counter == i) {
				ivBeg.setImage(beg[i]);
			}
		}
	}
	
	//changing the text of the story label
	public void labelChange() {
		if (counter == 0) {
			storyline.setText("The multiverse is collapsing! Enemies have escaped everywhere and it is up to you to stop them.");
		}
		else if (counter == 1) {
			storyline.setLayoutX(20);
			storyline.setText("Go through each level with a different character and universe to restore the world's balance!");
		}
		else if (counter == 2) {
			storyline.setLayoutX(190);
			storyline.setText("Remember, with great power comes great responsibility.");
		}
	}
	
	//returning the image
	public ImageView getIntroNode() { 
		return ivBeg; 
	}
	
	//returning the story text
	public Label getLabel() {
		return storyline;
	}
	
	//returning the counter
	public int count () {
		return counter;
	}
}
