package com.mycompany.a1;

import com.mycompany.a1.gameObjects.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GameWorld {
	private int lives = 3;
	private int score = 0;
	private int elapsed;
	
	// each object will be stored to specified list
	ArrayList<SpaceStation> spaceStationList = new ArrayList<SpaceStation>();
	ArrayList<Ship> shipList = new ArrayList<Ship>();
	ArrayList<Asteroid> asteroidList = new ArrayList<Asteroid>();
	ArrayList<FlyingSaucer> flyingSaucerList = new ArrayList<FlyingSaucer>();
	ArrayList<Missile> missileList = new ArrayList<Missile>();
	
	public GameWorld() {}
	
	public void init() {
		
		System.out.println("Game world initialized.");
		addSpaceStation();
		Ship ship = addShip();
		addAsteroid();
		addFlyingSaucer();
		showMap();

		for(int i = 0; i <= 14; i++ ) {
			shipFired(ship);
		}
		for(Missile missile: missileList) {
			System.out.println(missile.toString());
		}

		for(int i = 0; i <= 14; i++ ) {
			clockTick();
		}
	}
	
	public void clockTick() {
		Iterator<SpaceStation> ssl = spaceStationList.iterator();
		Iterator<Ship> shipl = shipList.iterator();
		Iterator<Asteroid> astrl = asteroidList.iterator();
		Iterator<FlyingSaucer> fsl = flyingSaucerList.iterator();
		Iterator<Missile> msll = missileList.iterator();
		// iterate through hashmaps and update locations
		while(ssl.hasNext()) {
			SpaceStation ss = ssl.next();
			if (ss.getBlinkRate() == 0) {
				ss.setBlinking(false);
			}
			spaceStationList.set(spaceStationList.indexOf(ss), ss);
		}
		while(shipl.hasNext()) {
			Ship ship = shipl.next();
			ship.updateLocation(ship.getLocationX(), ship.getLocationY(), ship.getSpeed(), ship.getDirection());
		}
		while(astrl.hasNext()) {
			Asteroid astr = astrl.next();
			astr.updateLocation(astr.getLocationX(), astr.getLocationY(), astr.getSpeed(), astr.getDirection());
			asteroidList.set(asteroidList.indexOf(astr), astr);
		}
		while(astrl.hasNext()) {
			FlyingSaucer fs = fsl.next();
			fs.updateLocation(fs.getLocationX(), fs.getLocationY(), fs.getSpeed(), fs.getDirection());
			flyingSaucerList.set(flyingSaucerList.indexOf(fs), fs);
		}
		while(msll.hasNext()) {
			Missile missile = msll.next();
			if (missile.getFuel() == 0) msll.remove();
			else {
				// decrement fuel by 1
				missile.setFuel(missile.getFuel()-1);
				missile.updateLocation(missile.getLocationX(), missile.getLocationY(), missile.getSpeed(), missile.getDirection());
				missileList.set(missileList.indexOf(missile), missile);
			}
		}
		System.out.println("One tick");
		showMap();
		elapsed++;
	}
	
	public void showState(Ship ship) {
		System.out.println("Score: " + score);
		System.out.println("Missiles: " + ship.getMissiles());
		System.out.println("Elapsed: " + elapsed);
	}
	
	public void showMap() {;
//		listAllSpaceStations();
//		listAllShips();
//		listAllAsteroids();
//		listAllFlyingSaucers();
		listAllMissiles();
	}
	
	// ship actions
	
	public void listAllShips() {
		System.out.println("Ships:");
		for(Ship ship: shipList) {
			System.out.println(ship.toString());
		}
		System.out.println();
	}
	
	public Ship addShip() {
		Ship ship = new Ship();
		shipList.add(ship);
		return ship;
	}
	
	public void shipDocked(Ship ship) {
		System.out.println("missiles reloaded: Shipped has docked and resupplied.");
		ship.reloadMissiles();
	}
	
	public void moveShip(Ship ship, int newSpeed, int newDirection) {
		ship.move(newSpeed, newDirection);
	}
	
	public void turnRight(Ship ship) {
		ship.changeDirection(2);
	}
	
	public void turnLeft(Ship ship) {
		ship.changeDirection(-2);
	}
	
	public void jump(Ship ship) {
		ship.setLocation(512.0, 384.0);
	}
	
	public void shipFired(Ship ship) {
		ship.firedMissile();
		addMissile(ship);
	}
	
	// missile actions
	
	public void listAllMissiles() {
		System.out.println("Missiles:");
		for(Missile missile: missileList) {
			System.out.println(missile.toString());
		}
		System.out.println();
	}
	
	public void addMissile(Ship ship) {
		Missile missile = new Missile(ship.getLocationX(), ship.getLocationY());
		missile.setDirection(ship.getDirection());
		missileList.add(missile);
	}
	
	// space station actions
	
	public void listAllSpaceStations() {
		System.out.println("Space stations:");
		for(SpaceStation ss: spaceStationList) {
			System.out.println("ID: " + ss.getId() + "\nLocation" + ss.toString());
		}
		System.out.println();
	}
	
	public void addSpaceStation() {
		SpaceStation spaceStation = new SpaceStation();
		spaceStationList.add(spaceStation);
	}
	
	// asteroid actions
	
	public void listAllAsteroids() {
		System.out.println("Asteroids:");
		for(Asteroid astr: asteroidList) {
			System.out.println(astr.toString());
		}
		System.out.println();
	}
	
	public void addAsteroid() {
		Asteroid asteroid = new Asteroid();
		asteroidList.add(asteroid);
	}
	
	// flying saucer actions
	
	public void listAllFlyingSaucers() {
		System.out.println("Flying saucers:");
		for(FlyingSaucer fs: flyingSaucerList) {
			System.out.println(fs.toString());
		}
		System.out.println();
	}
	
	public void addFlyingSaucer() {
		FlyingSaucer flyingsaucer = new FlyingSaucer();
		flyingSaucerList.add(flyingsaucer);
	}
	
	// collisions
	
	public void collisionMissileAsteroid(Missile ms, Asteroid astr) {
//		if (ms.getLocationX() == astr.getLocationX() && ms.getLocationY() == astr.getLocationY()) {	
//			System.out.println("+1: Asteroid has been destroyed!");
//			missileList.remove(ms.getId());
//			asteroidList.remove(astr.getId());
//			score++;
//		}
		missileList.remove(ms);
		asteroidList.remove(astr);
	}
	
	public void collisionMissileFlyingSaucer(Missile ms, FlyingSaucer fs) {
//		if (ms.getLocationX() == fs.getLocationX() && ms.getLocationY() == fs.getLocationY()) {
//			System.out.println("+2: Flying saucer has been destroyed!");
//			missileList.remove(ms.getId());
//			flyingSaucerList.remove(fs.getId());
//			score += 2;
//		}
		missileList.remove(ms);
		flyingSaucerList.remove(fs);
	}
	
	public void collisionShipAsteroid(Ship ship, Asteroid astr) {
//		if (ship.getLocationX() == astr.getLocationX() && ship.getLocationY() == astr.getLocationY()) {	
//			System.out.println("-1 life: Ship and asteroid destroyed.");
//			shipList.remove(ship);
//			asteroidList.remove(astr.getId());
//			lives--;
//		}
		shipList.remove(ship);
		asteroidList.remove(astr);
	}
	
	public void collisionShipFlyingSaucer(Ship ship, FlyingSaucer fs) {
//		if (ship.getLocationX() == fs.getLocationX() && ship.getLocationY() == fs.getLocationY()) {
//			System.out.println("-1 life: Ship and flying saucer destroyed.");
//			shipList.remove(ship);
//			flyingSaucerList.remove(fs.getId());
//			lives--;
//		}
		shipList.remove(ship);
		flyingSaucerList.remove(fs);
	}
	
	public void collisionAsteroids(Asteroid astr) {
		asteroidList.remove(astr);
		asteroidList.remove(astr);
	}
	
	// if remaining lives, restart game
	// if no more lives, end the game
	public void restartGame(){
		//Check if player has lives left
		if (lives > 0) {
			init();
		} else {
			System.out.println();
			System.out.println("You have 0 lives remaining.");
			System.out.println("Game Over.");
			System.exit(0);
		}
	}
	
	//quit game
	public void quitGame(){
		System.out.println("Quitting game. Thanks for playing.");
		System.exit(0);
	}
}
