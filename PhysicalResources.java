//All the physical resources
public class PhysicalResources {
	private Resource pollen;
	private PotentResource nectar;
	private PotentResource honey;
	private Resource wax;
	
	//The constructor
	public PhysicalResources(Resource pollen, PotentResource nectar, PotentResource honey, Resource wax){
		this.pollen = pollen;
		this.nectar = nectar;
		this.honey = honey;
		this.wax = wax;
    }
	
	//Getters and Setters
	public Resource getPollen() {return pollen;}
	public void setPollen(Resource pollen) {this.pollen = pollen;}
	public PotentResource getNectar() {return nectar;}
	public void setNectar(PotentResource nectar) {this.nectar = nectar;}
	public PotentResource getHoney() {return honey;}
	public void setHoney(PotentResource honey) {this.honey = honey;}
	public Resource getWax() {return wax;}
	public void setWax(Resource wax) {this.wax = wax;}
}
