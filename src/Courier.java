// Courier.java
// A class that stores the details of ONE courier (delivery person).

public class Courier {

    // private fields = encapsulation (only this class can change them directly)
    private final String id;
    private final String name;
    private final String phone;
    private final String zone;
    private String status;   // AVAILABLE or BUSY

    // Parameterized constructor
    public Courier(String id, String name, String phone, String zone, String status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.zone = zone;
        this.status = status;
    }

    // Getters (accessors)
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getZone() {
        return zone;
    }

    public String getStatus() {
        return status;
    }

    // Setter (mutator)
    public void setStatus(String status) {
        this.status = status;
    }

    // One line of text to save in couriers.txt
    public String toFileLine() {
        return id + "," + name + "," + phone + "," + zone + "," + status;
    }

    // toString() is used when we print the object
    @Override
    public String toString() {
        return String.format("%-6s %-10s %-12s %-16s %s", id, name, phone, zone, status);
    }
}
