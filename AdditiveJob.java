//These jobs add their produced whatever to their specific resource
public class AdditiveJob extends Job{

	//A constructor
	public AdditiveJob(Resource resource){
		this.resource = resource;
	}
	
	public void produce(int numBees){
		double multiplier = 1;
		for(int i = 0; i < prodMod.size(); i++){
			multiplier *= prodMod.get(i).getModifier();
		}
		resource.add( (int)(numBees * multiplier) );
	}
	
	public int willProduce(int numBees) {
		double multiplier = 1;
		for(int i = 0; i < prodMod.size(); i++){
			multiplier *= prodMod.get(i).getModifier();
		}
		return (int)(numBees * multiplier);
	}
}