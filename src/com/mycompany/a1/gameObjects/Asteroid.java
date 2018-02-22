package com.mycompany.a1.gameObjects;

import java.util.Random;

public class Asteroid extends MovableObject {
	static Random rand = new Random();
	private int size = rand.nextInt((24 - 6) + 1) + 6;
	private static int id = rand.nextInt(1000);
	
	public Asteroid() {
		this.setColor(189, 189, 189);
		this.setSpeed(3);
	}
	
	public int getSize() {
		return size;
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return "Location: " + this.getLocation() + " " + this.getColor() + "\nSpeed: " + this.getSpeed() + " Direction: " + this.getDirection() + "\nSize: " + this.getSize();
	}
}
