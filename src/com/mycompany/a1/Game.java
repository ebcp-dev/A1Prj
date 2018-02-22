package com.mycompany.a1;


import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import java.lang.String;

import com.mycompany.a1.GameWorld;

public class Game extends Form {
	 private GameWorld gw;
	
	public Game() {
		gw = new GameWorld();
		gw.init();
		play();
	}
	
	private void play() {
		System.out.println("Play method called.");
		Label myLabel = new Label("Enter a command.");
		this.addComponent(myLabel);
		final TextField myTextField = new TextField();
		this.addComponent(myTextField);
		this.show();
		myTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String sCommand = myTextField.getText().toString();
				myTextField.clear();
				System.out.println(sCommand);
				switch (sCommand.charAt(0)) {
					case 'a':
						gw.addAsteroid();
						break;
					case 'y':
						gw.addFlyingSaucer();
						break;
					case 'b':
						gw.addSpaceStation();
						break;
					case 's':
						gw.addShip();
						break;
					case 'i':
						gw.speedUp();
						break;
					case 'd':
						gw.speedDown();
						break;
					case 'l':
						gw.turnLeft();
						break;
					case 'r':
						gw.turnRight();
						break;
					case 'f':
						gw.shipFired();
						break;
					case 'j':
						gw.jump();
						break;
					case 'n':
						gw.shipDocked();
						break;
					case 'k':
						gw.collisionMissileAsteroid();
						break;
					case 'e':
						gw.collisionMissileFlyingSaucer();
						break;
					case 'c':
						gw.collisionShipAsteroid();
						break;
					case 'h':
						gw.collisionShipFlyingSaucer();
						break;
					case 'x':
						gw.collisionAsteroids();
						break;
					case 'w':
						gw.collisionAsteroidFlyingSaucer();
						break;
					case 't':
						gw.clockTick();
						break;
					case 'p':
						gw.showState();
						break;
					case 'm':
						gw.showMap();
						break;
					case 'q':
						gw.quitGame();
						break;
					default:
						break;
				}
			}
		});
	}
}