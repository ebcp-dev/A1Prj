package com.mycompany.a1;

import com.mycompany.a1.gameObjects.*;
import java.util.ArrayList;

public class GameWorld {
	private int lives = 3;
	private int score = 0;
	private int elapsed = 0;
	
	// each object will be stored to specified list
	private ArrayList<SpaceStation> spaceStationList;
	private ArrayList<Ship> shipList;
	private ArrayList<Asteroid> asteroidList;
	private ArrayList<FlyingSaucer> flyingSaucerList;
	private ArrayList<Missile> missileList;
	
	public GameWorld() {}
	
	public void init() {
		spaceStationList = new ArrayList<SpaceStation>();
		shipList = new ArrayList<Ship>();
		asteroidList = new ArrayList<Asteroid>();
		flyingSaucerList = new ArrayList<FlyingSaucer>();
		missileList = new ArrayList<Missile>();
		System.out.println("Game world initialized.");
	}
	
	public void clockTick() {
		if (!spaceStationList.isEmpty()) {
			for(SpaceStation ss: spaceStationList) {
				if ((elapsed % ss.getBlinkRate()) == 0) {
					ss.setBlinking(true);
					System.out.println("Space station " + spaceStationList.indexOf(ss) + " blinking.");
					spaceStationList.set(spaceStationList.indexOf(ss), ss);
				}
			}
		}
		if (!shipList.isEmpty()) {
			for(Ship ship: shipList) {
				ship.updateLocation(ship.getLocationX(), ship.getLocationY(), ship.getSpeed(), ship.getDirection());
				shipList.set(shipList.indexOf(ship), ship);
			}
		}
		if (!asteroidList.isEmpty()) {
			for(Asteroid astr: asteroidList) {
				astr.updateLocation(astr.getLocationX(), astr.getLocationY(), astr.getSpeed(), astr.getDirection());
				asteroidList.set(asteroidList.indexOf(astr), astr);
				System.out.println(astr.toString());
			}
		}
		if (!flyingSaucerList.isEmpty()) {
			for(FlyingSaucer fs: flyingSaucerList) {
				fs.updateLocation(fs.getLocationX(), fs.getLocationY(), fs.getSpeed(), fs.getDirection());
				flyingSaucerList.set(flyingSaucerList.indexOf(fs), fs);
			}
		}
		if (!missileList.isEmpty()) {
			// store index of missiles to remove in this array
			int[] missilesToRemove = new int[missileList.size()];
			// traverse missile list to decrement fuel by 1 and update location
			for(int i = 0; i < missileList.size(); i++) {
				Missile missile = missileList.get(i);
				// decrement fuel
				missile.setFuel(missile.getFuel()-1);
				// set index value to 1 if need to remove, 0 otherwise
				if (missile.getFuel() == 0) missilesToRemove[i] = 1;
				else missilesToRemove[i] = 0;
				missile.updateLocation(missile.getLocationX(), missile.getLocationY(), missile.getSpeed(), missile.getDirection());
				missileList.set(missileList.indexOf(missile), missile);
			}
			// can't remove items from collection while iterating over it
			// so iterate through array of indexes to remove instead
			for(int i = 0; i < missilesToRemove.length; i++) {
				if (missilesToRemove[i] == 1) {
					missileList.remove(i);
					System.out.println("Missile removed.");
				}
			}
		}
		showMap();
		elapsed++;
		System.out.println("Elapsed: " + elapsed + "\n");
	}
	
	public void showState() {
		System.out.println("Ships: " + shipList.size());
		if (!shipList.isEmpty()) {
			for (Ship ship: shipList) {
				System.out.println("Ship " + shipList.indexOf(ship) + " Missiles: " + ship.getMissiles());
			}
		}
		System.out.println("Lives left: " + lives);
		System.out.println("Score: " + score);
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
		if (!shipList.isEmpty()) {
			System.out.println("Ships:");
			for(Ship ship: shipList) {
				System.out.println(ship.toString());
			}
			System.out.println();
		}
	}
	
	public void addShip() {
		Ship ship = new Ship();
		shipList.add(ship);
		System.out.println("Ship added.");
	}
	
	public void shipDocked() {
		if (!shipList.isEmpty()) {
			for(Ship ship: shipList) {
				ship.reloadMissiles();
				System.out.println("Ship " + shipList.indexOf(ship) + " has resupplied: Missiles reloaded.");
			}
		} else {
			System.out.println("No ships in game.");
		}
	}
	
	public void speedUp() {
		if (!shipList.isEmpty()) {
			for(Ship ship: shipList) {
				ship.shipSpeed(1);
				System.out.println("Ship " + shipList.indexOf(ship) + " speed + 1: " + ship.getSpeed());
			}
		} else {
			System.out.println("No ships in game.");
		}
	}
	
	public void speedDown() {
		if (!shipList.isEmpty()) {
			for(Ship ship: shipList) {
				ship.shipSpeed(-1);
				System.out.println("Ship " + shipList.indexOf(ship) + " speed - 1: " + ship.getSpeed());
			}
		} else {
			System.out.println("No ships in game.");
		}
	}
	// turn left/right by 2 degrees
	public void turnLeft() {
		if (!shipList.isEmpty()) {
			for(Ship ship: shipList) {
				ship.changeDirection(-2);
				System.out.println("Direction: " + ship.getDirection());
			}
		} else {
			System.out.println("No ships in game.");
		}
	}
	
	public void turnRight() {
		if (!shipList.isEmpty()) {
			for(Ship ship: shipList) {
				ship.changeDirection(2);
				System.out.println("Direction: " + ship.getDirection());
			}
		} else {
			System.out.println("No ships in game.");
		}
	}
	
	public void jump() {
		if (!shipList.isEmpty()) {
			for(Ship ship: shipList) {
				// reset to center of map
				ship.setLocation(512.0, 384.0);
				System.out.println("Jumped to center of map.");
			}
		} else {
			System.out.println("No ships in game.");
		}
	}
	
	public void shipFired() {
		if (!shipList.isEmpty()) {
			for(Ship ship: shipList) {
				ship.firedMissile();
				addMissile(ship);
			}
		} else {
			System.out.println("No ships in game.");
		}
	}
	
	// missile actions
	public void listAllMissiles() {
		if (!missileList.isEmpty()) {
			System.out.println("Missiles:");
			for(Missile missile: missileList) {
				System.out.println(missile.toString());
			}
			System.out.println();
		}
	}
	
	public void addMissile(Ship ship) {
		Missile missile = new Missile(ship.getLocationX(), ship.getLocationY());
		missile.setDirection(ship.getDirection());
		missileList.add(missile);
	}
	
	// space station actions
	public void listAllSpaceStations() {
		if (!spaceStationList.isEmpty()) {
			System.out.println("Space stations:");
			for(SpaceStation ss: spaceStationList) {
				System.out.println("ID: " + ss.getId() + "\n" + ss.toString());
			}
			System.out.println();
		}
	}
	
	public void addSpaceStation() {
		SpaceStation spaceStation = new SpaceStation();
		spaceStationList.add(spaceStation);
		System.out.println("Space station added.");
	}
	
	// asteroid actions
	public void listAllAsteroids() {
		if (!asteroidList.isEmpty()) {
			System.out.println("Asteroids:");
			for(Asteroid astr: asteroidList) {
				System.out.println(astr.toString());
			}
			System.out.println();
		}
	}
	
	public void addAsteroid() {
		Asteroid asteroid = new Asteroid();
		asteroidList.add(asteroid);
		System.out.println("Asteroid added.");
	}
	
	// flying saucer actions
	public void listAllFlyingSaucers() {
		if (!flyingSaucerList.isEmpty()) {
			System.out.println("Flying saucers:");
			for(FlyingSaucer fs: flyingSaucerList) {
				System.out.println(fs.toString());
			}
			System.out.println();
		} 
	}
	
	public void addFlyingSaucer() {
		FlyingSaucer flyingsaucer = new FlyingSaucer();
		flyingSaucerList.add(flyingsaucer);
		System.out.println("Flying saucer added.");
	}
	
	// check if list of collision objects is empty
	// if any of them are them are empty, don't remove from game
	public void collisionMissileAsteroid() {
		if (!missileList.isEmpty()) {
			if(!asteroidList.isEmpty()) {
				asteroidList.remove(0);
				missileList.remove(0);
				System.out.println("+1: Asteroid destroyed by missile.");
				score++;
			}
		} else {
			System.out.println("No missiles or asteroids in game.");
		}
	}
	
	public void collisionMissileFlyingSaucer() {
		if (!missileList.isEmpty()) {
			if(!flyingSaucerList.isEmpty()) {
				flyingSaucerList.remove(0);
				missileList.remove(0);
				System.out.println("+2: Flying saucer destroyed by missile.");
				score+=2;
			}
		} else {
			System.out.println("No missiles or flying saucers in game.");
		}
	}
	
	public void collisionShipAsteroid() {
		if (!shipList.isEmpty()) {
			if(!asteroidList.isEmpty()) {
				asteroidList.remove(0);
				shipList.remove(0);
				System.out.println("-1 life: Ship and asteroid destroyed.");
				lives--;
				restartGame();
			}
		} else {
			System.out.println("No asteroids or ships in game.");
		}
	}
	
	public void collisionAsteroidFlyingSaucer() {
		if(!asteroidList.isEmpty()) {
			if (!flyingSaucerList.isEmpty()) {
				flyingSaucerList.remove(0);
				asteroidList.remove(0);
				System.out.println("Flying saucer and asteroid destroyed.");
			}
		} else {
			System.out.println("No flying saucers or asteroids in game.");
		}
	}
	
	public void collisionShipFlyingSaucer() {
		if(!shipList.isEmpty()) {
			if (!flyingSaucerList.isEmpty()) {
				flyingSaucerList.remove(0);
				shipList.remove(0);
				System.out.println("-1 life: Ship and flying saucer destroyed.");
				lives--;
				restartGame();
			}
		} else {
			System.out.println("No ships or flying saucers in game.");
		}
	}
	
	public void collisionAsteroids() {
		if(!asteroidList.isEmpty()) {
			asteroidList.remove(0);
			asteroidList.remove(0);
			System.out.println("Asteroids collided.");
		} else {
			System.out.println("No asteroids in game.");
		}
	}
	
	// if remaining lives, restart game
	// if no more lives, end the game
	public void restartGame(){
		//Check if player has lives left
		if (lives > 0) {
			init();
		} else {
			System.out.println();
			System.out.println("Game over. You have 0 lives remaining.");
			quitGame();
		}
	}
	
	//quit game
	public void quitGame(){
		System.out.println("Quitting game. Thanks for playing.");
		System.exit(0);
	}
}
