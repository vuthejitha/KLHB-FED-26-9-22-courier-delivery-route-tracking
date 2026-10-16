import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// Main.java
// HYDERABAD COURIER - Delivery Route & Tracking System
// The program starts here. It shows menus for the CUSTOMER and the ADMIN.

public class Main {

    static Scanner sc = new Scanner(System.in);

    // Arrays to store all couriers and parcels (+ how many we have)
    static Courier[] couriers = new Courier[50];
    static int courierCount = 0;

    static Parcel[] parcels = new Parcel[200];
    static int parcelCount = 0;

    public static void main(String[] args) {
        Storage.loadAll();

        int choice;
        do {
            line();
            System.out.println("   HYDERABAD COURIER - Delivery Route & Tracking");
            line();
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("0. Exit");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    customerLogin();
                    break;
                case 2:
                    adminLogin();
                    break;
                case 0:
                    System.out.println("Thank you for using Hyderabad Courier!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    // =====================================================
    //                    CUSTOMER
    // =====================================================
    static void customerLogin() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty() || name.contains(",")) {
            System.out.println("Please enter a valid name (no commas).");
            return;
        }

        System.out.print("Enter your 10 digit phone number: ");
        String phone = sc.nextLine().trim();
        if (!isValidPhone(phone)) {
            System.out.println("Invalid phone number.");
            return;
        }

        int choice;
        do {
            line('-');
            System.out.println("Welcome, " + name);
            System.out.println("1. Check price and delivery date");
            System.out.println("2. Book a parcel");
            System.out.println("3. My orders");
            System.out.println("4. Track an order");
            System.out.println("5. View all areas and zones");
            System.out.println("0. Back");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    bookParcel(name, phone, false);
                    break;
                case 2:
                    bookParcel(name, phone, true);
                    break;
                case 3:
                    myOrders(phone);
                    break;
                case 4:
                    trackOrder(phone);
                    break;
                case 5:
                    showAllAreas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // If confirm is false we only show the price. If true we also save the booking.
    static void bookParcel(String name, String phone, boolean confirm) {
        String from = chooseArea("PICKUP");
        if (from == null) {
            return;
        }
        String to = chooseArea("DESTINATION");
        if (to == null) {
            return;
        }
        if (from.equals(to)) {
            System.out.println("Pickup and destination cannot be the same.");
            return;
        }

        double weight = readDouble("Enter weight in kg: ");
        if (weight <= 0 || weight > 50) {
            System.out.println("Weight must be between 0 and 50 kg.");
            return;
        }

        Parcel p = createParcel(name, phone, from, to, weight, "None", "PENDING");

        line('-');
        System.out.println(RouteFinder.getRouteText(from, to));
        System.out.println("Distance      : " + p.getDistance() + " km");
        System.out.println("Price         : Rs." + p.getPrice());
        System.out.println("Delivery by   : " + p.getDeliveryDate());

        if (confirm) {
            addParcel(p);
            Storage.saveAll();
            System.out.println("BOOKED! Your order ID is " + p.getId());
        }
    }

    static void myOrders(String phone) {
        line('-');
        int found = 0;
        for (int i = 0; i < parcelCount; i++) {
            if (parcels[i].getPhone().equals(phone)) {
                System.out.println(parcels[i]);
                found++;
            }
        }
        if (found == 0) {
            System.out.println("You have no orders yet.");
        }
    }

    static void trackOrder(String phone) {
        System.out.print("Enter order ID: ");
        Parcel p = findParcel(sc.nextLine().trim());

        // A customer can only see their own parcels
        if (p == null || !p.getPhone().equals(phone)) {
            System.out.println("Order not found.");
            return;
        }

        line('-');
        System.out.println("Order " + p.getId() + "  (simulated tracking, not real GPS)");
        System.out.println(RouteFinder.getRouteText(p.getFromArea(), p.getToArea()));
        System.out.println("Delivery by : " + p.getDeliveryDate());

        String courierName = "Not assigned yet";
        Courier c = findCourier(p.getCourierId());
        if (c != null) {
            courierName = c.getName() + " (" + c.getPhone() + ")";
        }
        System.out.println("Courier     : " + courierName);

        // Show progress with a switch
        switch (p.getStatus()) {
            case "PENDING":
                System.out.println("[X] Booked  [ ] Assigned  [ ] On the way  [ ] Delivered");
                break;
            case "ASSIGNED":
                System.out.println("[X] Booked  [X] Assigned  [ ] On the way  [ ] Delivered");
                break;
            case "IN_TRANSIT":
                System.out.println("[X] Booked  [X] Assigned  [X] On the way  [ ] Delivered");
                break;
            case "DELIVERED":
                System.out.println("[X] Booked  [X] Assigned  [X] On the way  [X] Delivered");
                break;
            default:
                System.out.println("This order was CANCELLED.");
        }
    }

    // =====================================================
    //                      ADMIN
    // =====================================================
    static void adminLogin() {
        int attempts = 0;

        // while loop: maximum 3 tries
        while (attempts < 3) {
            System.out.print("Username: ");
            String user = sc.nextLine().trim();
            System.out.print("Password: ");
            String pass = sc.nextLine().trim();

            if (user.equals("admin") && pass.equals("admin123")) {
                adminMenu();
                return;
            }
            attempts++;
            System.out.println("Wrong details. Tries left: " + (3 - attempts));
        }
        System.out.println("Too many wrong tries.");
    }

    static void adminMenu() {
        int choice;
        do {
            line('-');
            System.out.println("ADMIN MENU");
            System.out.println("1. View all orders");
            System.out.println("2. Assign courier to an order");
            System.out.println("3. Update order status");
            System.out.println("4. Search orders");
            System.out.println("5. Sort orders by price");
            System.out.println("6. Summary report");
            System.out.println("7. View couriers");
            System.out.println("8. Add courier");
            System.out.println("0. Back");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    showOrders(parcels, parcelCount);
                    break;
                case 2:
                    assignCourier();
                    break;
                case 3:
                    updateStatus();
                    break;
                case 4:
                    searchOrders();
                    break;
                case 5:
                    sortByPrice();
                    break;
                case 6:
                    summaryReport();
                    break;
                case 7:
                    showCouriers();
                    break;
                case 8:
                    addNewCourier();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    static void showOrders(Parcel[] list, int count) {
        line('-');
        System.out.println(String.format("%-6s %-9s %-15s    %-15s %-9s %-12s %s",
                "ID", "Customer", "From", "To", "Price", "Delivery", "Status"));
        for (int i = 0; i < count; i++) {
            System.out.println(list[i]);
        }
        if (count == 0) {
            System.out.println("No orders.");
        }
    }

    static void assignCourier() {
        System.out.print("Enter order ID: ");
        Parcel p = findParcel(sc.nextLine().trim());
        if (p == null) {
            System.out.println("Order not found.");
            return;
        }
        if (!p.getStatus().equals("PENDING")) {
            System.out.println("Only PENDING orders can be assigned.");
            return;
        }

        System.out.println("Available couriers:");
        for (int i = 0; i < courierCount; i++) {
            if (couriers[i].getStatus().equals("AVAILABLE")) {
                System.out.println("  " + couriers[i]);
            }
        }

        System.out.print("Enter courier ID: ");
        Courier c = findCourier(sc.nextLine().trim());
        if (c == null || !c.getStatus().equals("AVAILABLE")) {
            System.out.println("Please choose an AVAILABLE courier.");
            return;
        }

        p.setCourierId(c.getId());
        p.setStatus("ASSIGNED");
        c.setStatus("BUSY");
        Storage.saveAll();
        System.out.println(c.getName() + " is assigned to " + p.getId());
    }

    static void updateStatus() {
        System.out.print("Enter order ID: ");
        Parcel p = findParcel(sc.nextLine().trim());
        if (p == null) {
            System.out.println("Order not found.");
            return;
        }
        if (p.getStatus().equals("DELIVERED") || p.getStatus().equals("CANCELLED")) {
            System.out.println("This order is already finished.");
            return;
        }

        System.out.println("1. IN_TRANSIT   2. DELIVERED   3. CANCELLED");
        int option = readInt("New status: ");

        String newStatus;
        if (option == 1) {
            newStatus = "IN_TRANSIT";
        } else if (option == 2) {
            newStatus = "DELIVERED";
        } else if (option == 3) {
            newStatus = "CANCELLED";
        } else {
            System.out.println("Invalid option.");
            return;
        }

        if (p.getCourierId().equals("None") && !newStatus.equals("CANCELLED")) {
            System.out.println("Assign a courier first.");
            return;
        }

        p.setStatus(newStatus);

        // When the order is finished, the courier is free again
        Courier c = findCourier(p.getCourierId());
        if (c != null && (newStatus.equals("DELIVERED") || newStatus.equals("CANCELLED"))) {
            c.setStatus("AVAILABLE");
        }
        Storage.saveAll();
        System.out.println(p.getId() + " is now " + newStatus);
    }

    static void searchOrders() {
        System.out.print("Enter ID, name, area or status: ");
        String word = sc.nextLine().trim().toLowerCase();

        Parcel[] result = new Parcel[parcelCount];
        int count = 0;
        for (int i = 0; i < parcelCount; i++) {
            Parcel p = parcels[i];
            String text = (p.getId() + " " + p.getCustomerName() + " " + p.getFromArea() + " "
                    + p.getToArea() + " " + p.getStatus()).toLowerCase();
            if (text.contains(word)) {
                result[count] = p;
                count++;
            }
        }
        showOrders(result, count);
    }

    // BUBBLE SORT on a copy, so the original order stays the same
    static void sortByPrice() {
        Parcel[] copy = new Parcel[parcelCount];
        for (int i = 0; i < parcelCount; i++) {
            copy[i] = parcels[i];
        }

        for (int i = 0; i < parcelCount - 1; i++) {
            for (int j = 0; j < parcelCount - 1 - i; j++) {
                if (copy[j].getPrice() > copy[j + 1].getPrice()) {
                    Parcel temp = copy[j];
                    copy[j] = copy[j + 1];
                    copy[j + 1] = temp;
                }
            }
        }
        showOrders(copy, parcelCount);
    }

    static void summaryReport() {
        String[] statuses = {"PENDING", "ASSIGNED", "IN_TRANSIT", "DELIVERED", "CANCELLED"};
        int[] counts = new int[statuses.length];
        int totalMoney = 0;

        for (int i = 0; i < parcelCount; i++) {
            for (int s = 0; s < statuses.length; s++) {
                if (parcels[i].getStatus().equals(statuses[s])) {
                    counts[s]++;
                }
            }
            if (parcels[i].getStatus().equals("DELIVERED")) {
                totalMoney += parcels[i].getPrice();
            }
        }

        line('-');
        System.out.println("Total orders : " + parcelCount);
        for (int s = 0; s < statuses.length; s++) {
            System.out.println(String.format("%-12s : %d", statuses[s], counts[s]));
        }
        System.out.println("Money earned (delivered) : Rs." + totalMoney);
    }

    static void showCouriers() {
        line('-');
        for (int i = 0; i < courierCount; i++) {
            System.out.println(couriers[i]);
        }
    }

    static void addNewCourier() {
        System.out.print("Courier name: ");
        String name = sc.nextLine().trim();
        System.out.print("Phone (10 digits): ");
        String phone = sc.nextLine().trim();

        if (name.isEmpty() || name.contains(",") || !isValidPhone(phone)) {
            System.out.println("Invalid name or phone.");
            return;
        }

        System.out.println("Zone:");
        for (int i = 0; i < RouteFinder.ZONES.length; i++) {
            System.out.println((i + 1) + ". " + RouteFinder.ZONES[i]);
        }
        int z = readInt("Choose zone: ");
        if (z < 1 || z > RouteFinder.ZONES.length) {
            System.out.println("Invalid zone.");
            return;
        }

        String id = "C" + String.format("%02d", courierCount + 1);
        addCourier(new Courier(id, name, phone, RouteFinder.ZONES[z - 1], "AVAILABLE"));
        Storage.saveAll();
        System.out.println("Courier added with ID " + id);
    }

    // =====================================================
    //                 HELPER METHODS
    // =====================================================

    // Makes a new Parcel with the distance, price and delivery date calculated
    static Parcel createParcel(String name, String phone, String from, String to,
                               double weight, String courierId, String status) {
        int km = RouteFinder.getDistance(from, to);
        int price = 30 + km * 5 + (int) (weight * 10);     // typecasting double to int
        int days = RouteFinder.getDays(from, to);

        String date = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        String id = "P" + (1001 + parcelCount);

        return new Parcel(id, name, phone, from, to, weight, km, price, date, courierId, status);
    }

    static void addParcel(Parcel p) {
        if (parcelCount < parcels.length) {
            parcels[parcelCount] = p;
            parcelCount++;
        } else {
            System.out.println("Storage full.");
        }
    }

    static void addCourier(Courier c) {
        if (courierCount < couriers.length) {
            couriers[courierCount] = c;
            courierCount++;
        }
    }

    // Linear search by ID
    static Parcel findParcel(String id) {
        for (int i = 0; i < parcelCount; i++) {
            if (parcels[i].getId().equalsIgnoreCase(id)) {
                return parcels[i];
            }
        }
        return null;
    }

    static Courier findCourier(String id) {
        for (int i = 0; i < courierCount; i++) {
            if (couriers[i].getId().equalsIgnoreCase(id)) {
                return couriers[i];
            }
        }
        return null;
    }

    // The user types the area name (or part of it). The program finds the zone by itself.
    static String chooseArea(String title) {
        System.out.print("\nType " + title + " area name (or part of it, e.g. tol): ");
        String word = sc.nextLine().trim().toLowerCase();
        if (word.isEmpty()) {
            System.out.println("Please type an area name.");
            return null;
        }

        // Search every area and keep the ones that contain the typed word
        String[] matches = new String[RouteFinder.AREAS.length];
        int count = 0;
        for (int i = 0; i < RouteFinder.AREAS.length; i++) {
            if (RouteFinder.AREAS[i][0].toLowerCase().contains(word)) {
                matches[count] = RouteFinder.AREAS[i][0];
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No area found. Choose 'View all areas and zones' to see the list.");
            return null;
        }

        // Only one match: choose it directly
        if (count == 1) {
            System.out.println("Selected: " + matches[0] + " (" + RouteFinder.getZone(matches[0]) + ")");
            return matches[0];
        }

        // Many matches: let the user pick one
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + matches[i] + "  (" + RouteFinder.getZone(matches[i]) + ")");
        }
        int a = readInt("Choose number: ");
        if (a < 1 || a > count) {
            System.out.println("Invalid choice.");
            return null;
        }
        return matches[a - 1];
    }

    // Prints every zone and the areas inside it
    static void showAllAreas() {
        for (int z = 0; z < RouteFinder.ZONES.length; z++) {
            String[] areas = RouteFinder.getAreasInZone(RouteFinder.ZONES[z]);
            line('-');
            System.out.println(RouteFinder.ZONES[z].toUpperCase() + " (" + areas.length + " areas)");
            System.out.println(String.join(", ", areas));
        }
    }

    // A phone number must be exactly 10 digits
    static boolean isValidPhone(String phone) {
        if (phone.length() != 10) {
            return false;
        }
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // Reads a whole number. Returns -1 if the user types letters.
    static int readInt(String message) {
        System.out.print(message);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static double readDouble(String message) {
        System.out.print(message);
        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // METHOD OVERLOADING: same name, different parameters
    static void line() {
        line('=');
    }

    static void line(char symbol) {
        for (int i = 0; i < 60; i++) {
            System.out.print(symbol);
        }
        System.out.println();
    }
}
