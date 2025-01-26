public class OfficeResource {
    private int employeeId;
    private String employeeName;
    private double salary;
    private int requestAmount;

    public OfficeResource(int employeeId, String employeeName, double salary, int requestAmount) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.salary = salary;
        this.requestAmount = requestAmount;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public double getSalary() {
        return salary;
    }

    public int getRequestAmount() {
        return requestAmount;
    }

    // Corrected setRequestAmount method
    public void setRequestAmount(int requestAmount) {
        this.requestAmount = requestAmount;
    }

    // Corrected setSalary method
    public void setSalary(int salary) {
        this.salary = salary;
    }
}
