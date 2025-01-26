public class Employee {
    private int employeeId;
    private String employeeName;
    private double salary;
    private String department;
    private int resourceRequest;

    public Employee(int employeeId, String employeeName, double salary, String department, int resourceRequest) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.salary = salary;
        this.department = department;
        this.resourceRequest = resourceRequest;
    }

    // Getters and setters methods for our data members
    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public double getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    public int getResourceRequest() {
        return resourceRequest;
    }

    public void setResourceRequest(int resourceRequest) {
        this.resourceRequest = resourceRequest;
    }
}
