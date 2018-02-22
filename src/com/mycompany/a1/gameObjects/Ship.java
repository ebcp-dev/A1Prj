package com.mycompany.a1.gameObjects;

import com.mycompany.a1.gameObjects.interfaces.IStreerable;

public class Ship extends MovableObject implements IStreerable {
	private int missiles = 12;
	private static int max_speed = 6;
	
	public Ship() {
		// put ship in center of map
		this.setLocation(512.0, 384.0);
		this.setSpeed(0);
		this.setDirection(0);
		this.setColor(0, 218, 255);
	}
	
	public int getMissiles() {
		return missiles;
	}
	
	public void setMissiles(int newMissiles) {
		missiles = newMissiles;
	}
	
	public void firedMissile() {
		if (this.missiles > 0) {	
			System.out.println("Missile fired from " + this.getLocation() + " at " + this.getDirection() + " degrees");
			missiles--;
		} else {
			System.out.println("No missiles left.");
		}
	}
	
	public void reloadMissiles() {
		// reset missile count to 12 if less than 12, maintain value otherwise
		missiles = missiles < 12 ? 12 : missiles;
	}

	public void shipSpeed(int offset) {
		int newSpeed = this.getSpeed() + offset;
		if (newSpeed > 6) newSpeed = 6;
		else if (newSpeed < 0) newSpeed = 0;
		this.setSpeed(newSpeed);
	}

	public void move(int newSpeed, int newDirection) {
		// update speed and direction if ship is moved
		// if newSpeed exceeds max_speed, set speed to max_speed
		int moveSpeed = this.getSpeed() <= max_speed ? this.getSpeed() + newSpeed : max_speed;
		this.setSpeed(moveSpeed);
		this.changeDirection(newDirection);
	}
	
	public String toString() {
		return "Location: " + this.getLocation() + " " + this.getColor() + "\nSpeed: " + this.getSpeed() + " Direction: " + this.getDirection() + "\nMissiles: " + this.getMissiles();
	}
}
