package com.mycompany.a1;

import com.mycompany.a1.gameObjects.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GameWorld {
	private int lives = 3;
	private int score = 0;
	private int elapsed;
	
	// each object will be stored to specified list
	HashMap<Integer, SpaceStation> spaceStationList = new HashMap<Integer, SpaceStation>();
	HashMap<Double, Ship> shipList = new HashMap<Double, Ship>();
	HashMap<Integer, Asteroid> asteroidList = new HashMap<Integer, Asteroid>();
	HashMap<Integer, FlyingSaucer> flyingSaucerList = new HashMap<Integer, FlyingSaucer>();
	HashMap<Integer, Missile> missileList = new HashMap<Integer, Missile>();
	
	public GameWorld() {}
	
	public void init() {
		
		System.out.println("Game world initialized.");
		addShip();
		addAsteroid();
		addFlyingSaucer();
		showMap();
		for(Map.Entry<Double, Ship> ship: shipList.entrySet()) {
			for(int i = 0; i <= 14; i++ ) {	
				shipFired(ship.getValue());
				clockTick();
			}
		}
	}
	
	public void clockTick() {
		// iterate through hashmaps and update locations
		for(Map.Entry<Double, Ship> ship: shipList.entrySet()) {
			ship.getValue().updateLocation(ship.getValue().getLocationX(), ship.getValue().getLocationY(), ship.getValue().getSpeed(), ship.getValue().getDirection());
		}
		for(Entry<Integer, Asteroid> astr: asteroidList.entrySet()) {
			astr.getValue().updateLocation(astr.getValue().getLocationX(), astr.getValue().getLocationY(), astr.getValue().getSpeed(), astr.getValue().getDirection());
		}
		for(Entry<Integer, FlyingSaucer> fs: flyingSaucerList.entrySet()) {
			fs.getValue().updateLocation(fs.getValue().getLocationX(), fs.getValue().getLocationY(), fs.getValue().getSpeed(), fs.getValue().getDirection());
		}
		for(Entry<Integer, Missile> missile: missileList.entrySet()) {
			if (missile.getValue().getFuel() == 0) missileList.remove(missile.getValue().getId());
			else {
				// decrement fuel by 1
				missile.getValue().setFuel(missile.getValue().getFuel()-1);
				missile.getValue().updateLocation(missile.getValue().getLocationX(), missile.getValue().getLocationY(), missile.getValue().getSpeed(), missile.getValue().getDirection());
				missileList.put(missile.getValue().getId(), missile.getValue());
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
		listAllSpaceStations();
		listAllShips();
		listAllAsteroids();
		listAllFlyingSaucers();
		listAllMissiles();
	}
	
	// ship actions
	
	public void listAllShips() {
		System.out.println("Ships:");
		for(Map.Entry<Double, Ship> ship: shipList.entrySet()) {
			System.out.println(ship.getValue().toString());
		}
		System.out.println();
	}
	
	public void addShip() {
		Ship ship = new Ship();
		shipList.put(ship.getLocationX()+ship.getLocationY(), ship);
	}
	
	public void shipDocked(Ship ship) {
		System.out.println("missiles reloaded: Shipped has docked and resupplied.");
		ship.reloadMissiles();
	}
	
	public void moveShip(Ship ship, int newSpeed, int newDirection) {
		ship.move(newSpeed, newDirection);
		shipList.put(ship.getLocationX()+ship.getLocationY(), ship);
	}
	
	public void turnRight(Ship ship) {
		ship.changeDirection(2);
		shipList.put(ship.getLocationX()+ship.getLocationY(), ship);
	}
	
	public void turnLeft(Ship ship) {
		ship.changeDirection(-2);
		shipList.put(ship.getLocationX()+ship.getLocationY(), ship);
	}
	
	public void jump(Ship ship) {
		Ship newShip = new Ship();
		newShip.setDirection(ship.getDirection());
		newShip.setSpeed(ship.getSpeed());
		newShip.setMissiles(ship.getMissiles());
		newShip.setLocation(512.0, 384.0);
		// replace old ship with new ship with new coordinates
		// but same missile count, speed, direction
		shipList.remove(ship.getLocationX()+ship.getLocationY());
		shipList.put(newShip.getLocationX()+newShip.getLocationY(), ship);
	}
	
	public void shipFired(Ship ship) {
		ship.firedMissile();
		shipList.put(ship.getLocationX()+ship.getLocationY(), ship);
		addMissile(ship);
	}
	
	// missile actions
	
	public void listAllMissiles() {
		System.out.println("Missiles:");
		for(Map.Entry<Integer, Missile> missile: missileList.entrySet()) {
			System.out.println(missile.getValue().toString());
		}
		System.out.println();
	}
	
	public void addMissile(Ship ship) {
		Missile missile = new Missile(ship.getLocationX(), ship.getLocationY());
		missileList.put(missile.getId(), missile);
	}
	
	// space station actions
	
	public void listAllSpaceStations() {
		System.out.println("Space stations:");
		for(Integer key: spaceStationList.keySet()) {
			System.out.println("ID: " + key + "\n" + spaceStationList.get(key));
		}
		System.out.println();
	}
	
	public void addSpaceStation() {
		SpaceStation spaceStation = new SpaceStation();
		spaceStationList.put(spaceStation.getId(), spaceStation);
	}
	
	// asteroid actions
	
	public void listAllAsteroids() {
		System.out.println("Asteroids:");
		for(Entry<Integer, Asteroid> astr: asteroidList.entrySet()) {
			System.out.println(astr.getValue().toString());
		}
		System.out.println();
	}
	
	public void addAsteroid() {
		Asteroid asteroid = new Asteroid();
		asteroidList.put(asteroid.getId(), asteroid);
	}
	
	// flying saucer actions
	
	public void listAllFlyingSaucers() {
		System.out.println("Flying saucers:");
		for(Entry<Integer, FlyingSaucer> fs: flyingSaucerList.entrySet()) {
			System.out.println(fs.getValue().toString());
		}
		System.out.println();
	}
	
	public void addFlyingSaucer() {
		FlyingSaucer flyingsaucer = new FlyingSaucer();
		flyingSaucerList.put(flyingsaucer.getId(), flyingsaucer);
	}
	
	// collisions
	
	public int collisionMissileAsteroid(Missile ms, Asteroid astr) {
		System.out.println("+1: Asteroid has been destroyed!");
		missileList.remove(ms.getId());
		asteroidList.remove(astr.getId());
		return score++;
	}
	
	public int collisionMissileFlyingSaucer(Missile ms, FlyingSaucer fs) {
		System.out.println("+2: Flying saucer has been destroyed!");
		missileList.remove(ms.getId());
		flyingSaucerList.remove(fs.getId());
		return score += 2;
	}
	
	public int collisionShipAsteroid(Ship ship, Asteroid astr) {
		System.out.println("-1 life: Ship and asteroid destroyed.");
		shipList.remove(ship.getLocationX()+ship.getLocationY());
		asteroidList.remove(astr.getId());
		return lives--;
	}
	
	public int collisionShipFlyingSaucer(Ship ship, FlyingSaucer fs) {
		System.out.println("-1 life: Ship and flying saucer destroyed.");
		shipList.remove(ship.getLocationX()+ship.getLocationY());
		flyingSaucerList.remove(fs.getId());
		return lives--;
	}
}
