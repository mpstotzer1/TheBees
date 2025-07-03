package beehive.department;

import java.util.ArrayList;

public class DepartmentInfo {
	private Department nurse;
	private Department forager;
	private Department guard;
	private Department waxMason;
	private Department houseBee;
	private Department fanner;
	private Department drone;
	private Department cluster;
    private ArrayList<Department> departments = new ArrayList<Department>();

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
    
    public Department getNurse() { return nurse; }
    public Department getForager() { return forager; }
    public Department getGuard() { return guard; }
    public Department getWaxMason() { return waxMason; }
    public Department getHouseBee() { return houseBee; }
    public Department getFanner() { return fanner; }
    public Department getDrone() { return drone; }
    public Department getCluster() { return cluster; }
    public ArrayList<Department> getDepartments() { return departments; }
}
