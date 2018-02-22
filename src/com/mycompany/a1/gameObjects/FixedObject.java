package com.mycompany.a1.gameObjects;

import java.util.Random;

public abstract class FixedObject extends GameObject {
	static Random rand = new Random();
	private static int id = rand.nextInt(1000);
	
	public int getId() {
		return id;
	}
}
