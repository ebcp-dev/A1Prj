package com.mycompany.a1.gameObjects;

import java.util.Random;

public class SpaceStation extends FixedObject {
	static Random rand = new Random();
	private static int id = rand.nextInt(1000);
	private int blinkRate;
	private boolean blinking;
	
	public SpaceStation( ) {
		Random rand = new Random();
		blinkRate = (rand.nextInt(5 - 1) + 1) + 1;
		this.setColor(255, 45, 0);
	}
	
	public boolean getBlinkStatus() {
		return blinking;
	}
	
	public int getBlinkRate() {
		return blinkRate;
	}
	
	public void setBlinking(boolean blink) {
		blinking = blink;
	}
	
	public int getId() {
		return id;
	}
	
	public void setBlinkRate(int newBlinkRate) {
		blinkRate = newBlinkRate;
	}
	
	public String toString() {
		return "Location: " + this.getLocation() + " " + this.getColor() + "\nBlink rate: " + this.getBlinkRate();
	}
}
