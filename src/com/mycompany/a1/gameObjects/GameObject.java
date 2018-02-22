package com.mycompany.a1.gameObjects;

import com.codename1.location.Location;
import com.codename1.charts.util.ColorUtil;
import java.util.Random;
import java.lang.Math;

public abstract class GameObject {
	private Location location;
	private int color;
	
	public GameObject() {
		Random rand = new Random();
		// max latitude is 1024
		double latitude = rand.nextDouble() * 1024;
		// max longitude is 768
		double longitude = rand.nextDouble() * 768;
		location = new Location();
		location.setLatitude(latitude);
		location.setLongitude(longitude);
	}
	
	public String getLocation() {
		return "(" + getLocationX() + ", " + getLocationY() + ")";
	}
	
	public double getLocationX() {
		// round to 1 sigfig
		return Math.round(location.getLatitude() * 10.0)/10;
	}
	
	public double getLocationY() {
		return Math.round(location.getLongitude() * 10.0)/10;
	}
	
	public void setLocation(double newLat, double newLong) {
		Location newLocation = new Location();
		// make sure new location doesn't exceed game world boundaries
		newLat = newLat > 1024 ? 1024: newLat;
		newLong = newLong > 768 ? 768: newLong;
		newLocation.setLatitude(newLat);
		newLocation.setLongitude(newLong);
		location = newLocation;
	}
	
	public String getColor() {
		// return concatenated string of rgb
		return "R: " + ColorUtil.red(color) + " G: " + ColorUtil.green(color) + " B: " + ColorUtil.blue(color) ;
	}
	
	public void setColor(int r, int g, int b) {
		color = ColorUtil.rgb(r, g, b);
	}
}
