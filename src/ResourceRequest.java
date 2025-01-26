public class ResourceRequest {
    private String firstName;
    private String lastName;
    private int numItemsRequested;
    private int[] itemsRequested;  // Contains item IDs and their quantities (in pairs)

    public ResourceRequest(String firstName, String lastName, int numItemsRequested, int[] itemsRequested) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.numItemsRequested = numItemsRequested;
        this.itemsRequested = itemsRequested;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getNumItemsRequested() {
        return numItemsRequested;
    }

    public int[] getItemsRequested() {
        return itemsRequested;
    }
}
