import java.util.*;

public class EmployeeResourceSystem {
    
    // This method adds an employee to the system by creating a new Employee object
    // This method adds an employee to the system by creating a new Employee object
public static void addEmployee(Scanner in, String addedEmployee, OfficeResource[] resources){
    String[] addedItems = addedEmployee.split(" ");
    int employeeId = Integer.parseInt(addedItems[1]);
    String employeeName = addedItems[2];
    double employeeSalary = Double.parseDouble(addedItems[3]);
    String department = addedItems[4];
    int resourceRequest = Integer.parseInt(addedItems[5]);
    
    // Create an Employee object
    Employee newEmployee = new Employee(employeeId, employeeName, employeeSalary, department, resourceRequest);
    
    // Convert Employee to OfficeResource (add it to resources[] array)
    OfficeResource newResource = new OfficeResource(employeeId, employeeName, employeeSalary, resourceRequest);
    
    int indexHold = 0;
    for (int i = 0; i < resources.length; i++) {
        if(resources[i] == null || resources[i].getEmployeeId() > employeeId){
            indexHold = i;
            break;
        }
    }
    arrayShift(resources, indexHold, newResource); // Shift the array to insert the new resource (OfficeResource)
    System.out.printf("Employee %d, %s, with a salary of $%.2f and assigned to department %s, has been added to the employee database.\n\n",employeeId, employeeName, employeeSalary, department);
}

    // This method shifts the array to make space for the new office resource (OfficeResource)
    public static void arrayShift(OfficeResource[] resources, int indexHold, OfficeResource newResource) {
        for (int i = resources.length - 1; i > indexHold; i--) {
            resources[i] = resources[i - 1];
        }
        resources[indexHold] = newResource; // Insert the new OfficeResource
    }

    // This method performs a binary search to find an employee by ID in the array
    public static void findEmployee(Scanner in, String employeeSearch, OfficeResource[] resources, ResourceRequest[] requests) {
    if (resources[0] == null) {
        System.out.println("\tCannot perform command; there are no employees in the database.\n");
        return;
    }

    String[] employeeToSearch = employeeSearch.split(" ");
    int employeeSearchId = Integer.parseInt(employeeToSearch[1]);
    int resourcesRequested = 0;
    double totalAmount = 0.00;
    StringBuilder indicesViewed = new StringBuilder();

    int low = 0;
    int high = findValidHighIndex(resources);

    // Binary search loop
    // Binary search loop
while (low <= high) {
    int mid = (low + high) / 2;
    indicesViewed.append(mid).append(" ");

    // No need to call getEmployee(), directly access the employeeId of the OfficeResource
    if (resources[mid] != null && resources[mid].getEmployeeId() == employeeSearchId) {
        Employee employee = new Employee(resources[mid].getEmployeeId(), resources[mid].getEmployeeName(), resources[mid].getSalary(), "Department", resources[mid].getRequestAmount());
        
        // The rest of your logic will work with the 'employee' object here
        // Calculate total resources requested and total amount spent
        for (ResourceRequest request : requests) {
            if (request != null) {
                int[] requestedItems = request.getItemsRequested();
                for (int i = 0; i < requestedItems.length; i += 2) {
                    int requestedItemId = requestedItems[i];
                    int requestedQuantity = requestedItems[i + 1];

                    if (requestedItemId == employeeSearchId) {
                        resourcesRequested += requestedQuantity;
                        totalAmount += requestedQuantity * resources[mid].getSalary();
                    }
                }
            }
        }
        // Print the details of the employee found
        System.out.printf("	Performing Binary Search...(Indices viewed: %s )\n" +
                          "	Employee #%d (%s)\n" +
                          "	Salary           :  $%.2f\n" +
                          "	Department       :  %s\n" +
                          "	Resources Requested:  %d\n" +
                          "	Total Amount     :  $%.2f\n",
                          indicesViewed.toString().trim(),
                          employee.getEmployeeId(),
                          employee.getEmployeeName(),
                          employee.getSalary(),
                          employee.getDepartment(),  // Now this works with the Employee's department
                          resourcesRequested,
                          totalAmount);
        return;
    } else if (resources[mid] == null || employeeSearchId < resources[mid].getEmployeeId()) {
        high = mid - 1;
    } else {
        low = mid + 1;
    }
}


    System.out.printf("	Performing Binary Search...(Indices viewed: %s )\n", indicesViewed.toString().trim());
    System.out.printf("	Employee #%d was not found in the employee database.\n", employeeSearchId);
}

    
    
    // This method finds the last valid index in the resources array
    private static int findValidHighIndex(OfficeResource[] resources) {
        int high = resources.length - 1;
        while (high >= 0 && resources[high] == null) {
            high--;
        }
        return high;
    }

    // This method restocks resources that have a quantity of zero, using the restock quantity
    public static void restock(Scanner in, OfficeResource[] resources, ResourceRequest[] requests){
        String restock = "";
        if(resources[0] == null){
            System.out.println("	There are no resources to restock.\n");
            return;
        }
        int restockAmount;
        boolean madeARestock = false;
        for (int i = 0; i < resources.length; i++) {
            if(resources[i] == null){
                break;
            }
            else{
                if(resources[i].getSalary() == 0){
                    madeARestock = true;
                    restockAmount = resources[i].getRequestAmount();
                    resources[i].setSalary(restockAmount);
                    restock += String.format("	Resource %d, %s, restocked to a quantity of %d.\n", resources[i].getEmployeeId(), resources[i].getEmployeeName(), restockAmount);
                }
            }
        }
        if(madeARestock == true){
            System.out.println(restock);
        }
        else{
            System.out.println("	There are no resources to restock.\n");
        }
    }

    // This method lists all available resources with their details
    public static void inventory(Scanner in, OfficeResource[] resources, ResourceRequest[] requests){
        String inventory = "	Contains the following resources:\n";
        String addedInventory = "";
        if(resources[0] == null){
            System.out.println("	Contains no resources.");
        }
        else {
            for (int i = 0; i < resources.length; i++) {
                addedInventory = "";
                if(resources[i] != null){
                    addedInventory += String.format("\t| Resource %6d | %-20s | $%7.2f | %4d resource(s) |\n", resources[i].getEmployeeId(), resources[i].getEmployeeName(), resources[i].getSalary(), resources[i].getRequestAmount());
                }
                inventory += addedInventory;
            }
            System.out.println(inventory);
        }
    }

    // This method processes a customer resource request, adding items and quantities
    public static void customer(Scanner in, ResourceRequest[] requests, String command, OfficeResource[] resources){
        String[] customer = command.split(" ");
        String customerFirstName = customer[1];
        String customerLastName = customer[2];
        int n = Integer.parseInt(customer[3]) * 2;
        int[] itemsRequested = new int[n];
        int itemId;
        int quantityRequested;
        boolean validRequest = false;

        for (int i = 0; i < n; i += 2) {
            if (4 + i >= customer.length) {
                break;
            }
            itemId = Integer.parseInt(customer[4 + i]);
            quantityRequested = Integer.parseInt(customer[5 + i]);
            for (int j = 0; j < resources.length; j++) {
                if (resources[j] != null && resources[j].getEmployeeId() == itemId) {
                    if (resources[j].getRequestAmount() >= quantityRequested) {
                        itemsRequested[i] = itemId;
                        itemsRequested[i + 1] = quantityRequested;
                        resources[j].setRequestAmount(resources[j].getRequestAmount() - quantityRequested);
                        validRequest = true;
                    } else {
                        itemsRequested[i] = itemId;
                        itemsRequested[i + 1] = resources[j].getRequestAmount();
                        resources[j].setRequestAmount(0);
                        if(itemsRequested[i+1] > 0){
                            validRequest = true;
                        }
                    }
                }
            }

            if (itemsRequested[i + 1] == 0) {
                itemsRequested[i] = itemId;
                itemsRequested[i + 1] = 0;
            }
        }
        ResourceRequest newCustomer = new ResourceRequest(customerFirstName, customerLastName, n, itemsRequested);
        for (int i = 0; i < requests.length; i++) {
            if(requests[i] == null){
                requests[i] = newCustomer;
                break;
            }
        }
        if(validRequest){
            System.out.printf("	Customer %s %s made a valid request.\n\n", customerFirstName, customerLastName);
        }
        else{
            System.out.printf("	Customer %s %s made no valid requests.\n\n", customerFirstName, customerLastName);
        }
    }

    // This method generates a sales summary report showing the details of resource requests made by customers
    public static void printSummary(Scanner in, OfficeResource[] resources, ResourceRequest[] requests){
        String requestSummary = "Employee Resource Summary Report:\n";
        int requestNumber = 1;
        String resourceName;
        int resourceId;
        int resourceAmount;
        double resourcePrice;
        double totalCost;
        double grandTotal = 0.00;
    
        for (int i = 0; i < requests.length; i++) {
            if(requests[i] == null){
                break;
            }
            else{
                totalCost = 0.00;
                int[] itemsRequested = requests[i].getItemsRequested();
                int itemsCount = 0;
                boolean hasRequests = false;
                for (int j = 1; j < itemsRequested.length; j += 2) {
                    if (itemsRequested[j] > 0) {
                        itemsCount += itemsRequested[j];
                        hasRequests = true;
                    }
                }
                if (hasRequests == true) {
                    requestSummary += String.format("\tRequest #%d, %s %s requested %d resource(s):\n",requestNumber, requests[i].getFirstName(), requests[i].getLastName(),itemsCount);
                    for (int j = 0; j < itemsRequested.length; j += 2) {
                         resourceId = itemsRequested[j];
                         resourceAmount = itemsRequested[j + 1];
                        if (resourceAmount == 0) {
                            continue;
                        }
                         resourceName = "";
                         resourcePrice = 0.0;
                        for (OfficeResource resource : resources) {
                            if (resource != null && resource.getEmployeeId() == resourceId) {
                                resourceName = resource.getEmployeeName();
                                resourcePrice = resource.getSalary();
                                break;
                            }
                        }
                        requestSummary += String.format("\t\t| Resource %6d | %-20s | $%7.2f (x%4d) |\n", resourceId, resourceName, resourcePrice, resourceAmount);
                        totalCost += resourcePrice * resourceAmount;
                    }
                        requestSummary += String.format("\t\tTotal: $%.2f\n", totalCost);
                    grandTotal += totalCost;
                    requestNumber++;
                }
            }
        }
        requestSummary += String.format("	Grand Total: $%.2f\n", grandTotal);
        System.out.println(requestSummary);
    }

    // Main method that handles user input and calls the corresponding operations
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int maxResources = in.nextInt();
        int maxRequests = in.nextInt();
        in.nextLine();
        OfficeResource[] resources = new OfficeResource[maxResources];
        ResourceRequest[] requests = new ResourceRequest[maxRequests];
        while(true) {
            String command = in.nextLine();
            if (command.length() >= 8 && command.substring(0, 8).equals("FINDITEM")) {
                System.out.println("FINDITEM:");
            findEmployee(in, command, resources, requests);
            } 
            else if (command.length() >= 7 && command.substring(0, 7).equals("ADDITEM")) {
                System.out.println("ADDITEM:");
            addEmployee(in, command, resources);
            }
            else if (command.equals("RESTOCK")) {
                System.out.println("RESTOCK:");
            restock(in, resources, requests);
            } 
            else if (command.length() >= 8 && command.substring(0, 8).equals("CUSTOMER")) {
                System.out.println("CUSTOMER:");
            customer(in, requests, command, resources);
            } 
            else if (command.equals("INVENTORY")) {
                System.out.println("INVENTORY:");
            inventory(in, resources, requests);
            } 
            else if (command.equals("PRINTSALESSUMMARY")) {
            System.out.println("PRINTSALESSUMMARY:");
            printSummary(in, resources, requests);
            } 
            else if (command.equals("QUIT")) {
            System.out.println("Goodbye.\n\n");
            break;
            }
        }
    }
}
