//All the departments

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DepartmentInfo {
	private Department nurse;
	private Department forager;
	private Department guard;
	private Department waxMason;
	private Department houseBee;
	private Department fanner;
	private Department drone;
	private Department cluster;
    private List<Department> departments;// = new ArrayList<>(Arrays.asList(nurse, forager, guard, waxMason, houseBee,
    																	 //fanner, drone, cluster));
	//Some constructors for your departments
    	//Make list from departments
    public DepartmentInfo(Department nurse, Department forager, Department guard, Department waxMason,
			Department houseBee, Department fanner, Department drone, Department cluster) {
		this.nurse = nurse;
		this.forager = forager;
		this.guard = guard;
		this.waxMason = waxMason;
		this.houseBee = houseBee;
		this.fanner = fanner;
		this.drone = drone;
		this.cluster = cluster;
		departments.add(nurse);
		departments.add(forager);
		departments.add(guard);
		departments.add(waxMason);
		departments.add(houseBee);
		departments.add(fanner);
		departments.add(drone);
		departments.add(cluster);
	}
    	//Put a list in outside of department inputs (may be useless?)
    public DepartmentInfo(Department nurse, Department forager, Department guard, Department waxMason,
			Department houseBee, Department fanner, Department drone, Department cluster,
			List<Department> departments) {
		this.nurse = nurse;
		this.forager = forager;
		this.guard = guard;
		this.waxMason = waxMason;
		this.houseBee = houseBee;
		this.fanner = fanner;
		this.drone = drone;
		this.cluster = cluster;
		this.departments = departments;
	}
    
    //Your getters and setters
    public Department getNurse() { return nurse; }
    public void setNurse(Department nurse) { this.nurse = nurse; }
    public Department getForager() { return forager; }
    public void setForager(Department forager) { this.forager = forager; }
    public Department getGuard() { return guard; }
    public void setGuard(Department guard) { this.guard = guard; }
    public Department getWaxMason() { return waxMason; }
    public void setWaxMason(Department waxMason) { this.waxMason = waxMason; }
    public Department getHouseBee() { return houseBee; }
    public void setHouseBee(Department houseBee) { this.houseBee = houseBee; }
    public Department getFanner() { return fanner; }
    public void setFanner(Department fanner) { this.fanner = fanner; }
    public Department getDrone() { return drone; }
    public void setDrone(Department drone) { this.drone = drone; }
    public Department getCluster() { return cluster; }
    public void setCluster(Department cluster) { this.cluster = cluster; }
    public List<Department> getDepartments() { return departments; }
    public void setDepartments(List<Department> departments) { this.departments = departments; }
}
