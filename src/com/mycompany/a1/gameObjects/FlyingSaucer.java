package com.mycompany.a1.gameObjects;

import java.util.Random;

public class FlyingSaucer extends MovableObject{
	static Random rand = new Random();
	private int size = rand.nextInt((20 - 10) + 1) + 10;
	
	public FlyingSaucer() {
		this.setColor(208, 0, 255);
		this.setSpeed(6);
	}
	
	public int getSize() {
		return size;
	}
	
	public String toString() {
		return "Location: " + this.getLocation() + " " + this.getColor() + "\nSpeed: " + this.getSpeed() + " Direction: " + this.getDirection() + "\nSize: " + this.getSize();
	}
}
