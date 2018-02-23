package com.mycompany.a1.gameObjects;

public class Missile extends MovableObject {
	private int fuel = 10;
	
	public Missile(double lat, double longt) {
		this.setLocation(lat, longt);
		this.setColor(255, 0, 0);
		this.setSpeed(7);
	}
	
	public int getFuel() {
		return fuel;
	}
	
	public void setFuel(int newFuel) {
		this.fuel = newFuel;
	}
	
	public String toString() {
		return "Location: " + this.getLocation() + " " + this.getColor() + "\nSpeed: " + this.getSpeed() + " Direction: " + this.getDirection() + "\nFuel: " + this.getFuel();
	}
}
