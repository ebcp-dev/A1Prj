package com.mycompany.a1.gameObjects;

import java.util.Random;
import java.lang.Math;

public abstract class MovableObject extends GameObject {
	private int speed;
	private int direction;
	
	public MovableObject() {
		Random rand = new Random();
		// speed range is 0 to 10
		speed = rand.nextInt(10);
		// direction in degrees
		direction = rand.nextInt(359);
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int newSpeed) {
		if (newSpeed > 10) newSpeed = 10;
		else if (newSpeed < 0) newSpeed = 0;
		speed = newSpeed;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int newDirection) {
		direction = newDirection;
	}
	
	public void updateLocation(double x, double y, int speed, int direction) {
		double angle = 90 - direction;
		double offsetX = Math.cos(angle) * speed;
		double offsetY = Math.sin(angle) * speed;
		double newX = offsetX + x;
		double newY = offsetY + y;
		
		this.setLocation(newX, newY);
	}
}
