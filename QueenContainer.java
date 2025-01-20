//Information about the Queen
import java.util.ArrayList;

public class QueenContainer {
	private int queenFightDamage;
	private Resource healthDec;
	private ArrayList<Queen> queens;
	
	//The constructor
	public QueenContainer(){
		this.queenFightDamage = 1;
		this.healthDec.setAmount(0);
		Queen startingQueen = new Queen(100);
		this.queens.add(startingQueen);
	}

	//useful methods
	public void updateHealth(double queenHealthDecMult){
		for(int i = 0; i < queens.size(); i++) {
			queens.get(i).subHealth( (int)(healthDec.getAmount() * queenHealthDecMult) );
		}
	}
	public void incQueenFightDamage(double qfd){ queenFightDamage *= qfd; }

	//getters and setters
	public int getQueenFightDamage(){ return queenFightDamage; }
	public void setQueenFightDamage(int queenFightDamage) { this.queenFightDamage = queenFightDamage; }
	public Resource getHealthDec() { return healthDec; }
	public void setHealthDec(Resource healthDec) { this.healthDec = healthDec; }
	public ArrayList<Queen> getQueens() { return queens; }
	public void setQueens(ArrayList<Queen> queens) { this.queens = queens; }
}
