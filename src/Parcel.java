// Parcel.java
// A class that stores the details of ONE parcel (one delivery order).

public class Parcel {

    private final String id;            // example: P1001
    private final String customerName;
    private final String phone;
    private final String fromArea;      // pickup area
    private final String toArea;        // destination area
    private final double weight;        // in kg (double because it can be 2.5)
    private final int distance;         // in km
    private final int price;            // in rupees
    private final String deliveryDate;  // example: 19 Oct 2026
    private String courierId;     // "None" until the admin assigns a courier
    private String status;        // PENDING, ASSIGNED, IN_TRANSIT, DELIVERED, CANCELLED

    public Parcel(String id, String customerName, String phone, String fromArea, String toArea,
                  double weight, int distance, int price, String deliveryDate,
                  String courierId, String status) {
        this.id = id;
        this.customerName = customerName;
        this.phone = phone;
        this.fromArea = fromArea;
        this.toArea = toArea;
        this.weight = weight;
        this.distance = distance;
        this.price = price;
        this.deliveryDate = deliveryDate;
        this.courierId = courierId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public String getFromArea() {
        return fromArea;
    }

    public String getToArea() {
        return toArea;
    }

    public double getWeight() {
        return weight;
    }

    public int getDistance() {
        return distance;
    }

    public int getPrice() {
        return price;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getCourierId() {
        return courierId;
    }

    public String getStatus() {
        return status;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toFileLine() {
        return id + "," + customerName + "," + phone + "," + fromArea + "," + toArea + ","
                + weight + "," + distance + "," + price + "," + deliveryDate + ","
                + courierId + "," + status;
    }

    @Override
    public String toString() {
        return String.format("%-6s %-9s %-15s -> %-15s Rs.%-5d %-12s %s",
                id, customerName, fromArea, toArea, price, deliveryDate, status);
    }
}
