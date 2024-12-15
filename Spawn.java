package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//abstract class for spawns
public abstract class Spawn {
	//fields
	 int xPos, yPos, bgH, bgW, width, height; 
	 Image img; 
	 ImageView ivImg; 
	 Random rnd; 
	 
	 //abstract methods
	 public abstract void spawning();
	 public abstract ImageView getNode(); 
}
