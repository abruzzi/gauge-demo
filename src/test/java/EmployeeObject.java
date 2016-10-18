import com.thoughtworks.jigsaw.Employee;

public class EmployeeObject {
    private String name;
    private String role;
    private String currentProject;

    public EmployeeObject() {
    }

    public EmployeeObject(String name, String currentProject, String role) {
    	this.name = name;
    	this.currentProject = currentProject;
    	this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(String currentProject) {
        this.currentProject = currentProject;
    }

    public Employee toEmployee() {
        return new Employee(name, role, currentProject);
    }
}
