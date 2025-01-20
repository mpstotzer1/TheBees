/*
The queen is responsible for keeping the hive running smoothly. If she's sick or old,
then no new eggs can be laid. Additionally, her health influences the productivity of
every job in the hive: healthy queen, happy hive. if there are two (or more) queens,
they will kill each other.
	From a coding perspective, the queen is an object with a health bar that slowly
	decreases as she ages. The amount of queenHealth being produced by the nurse bees
	makes it decrease slower rather than faster. Once she hits a certain age, though she
	is considered infirm and a new queen needs to be produced. Producing new queens is
	expensive and having more than one queen doesn't bring any advantages. In fact, if there
	are two (or more) queens, then their health will drain until only one remains alive. While
	the queens are fighting, hive productivity will plummet in every area, and NO egg-laying
	will be able to happen. If you want to swarm, you've got the queen-fighting window to do
	so, though.
*/

public class Queen {
	private int health;
	
	public Queen(int h){
		health = h;
	}
	
	//Miscellaneous Methods
	public void subHealth(int h){ health -= h; }
	
	//Getters and setters
	public int getHealth(){ return health; }
	public void setHealth(int h){ health = h; }
}
