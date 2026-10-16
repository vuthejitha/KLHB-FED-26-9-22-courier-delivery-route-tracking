import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Storage.java
// Saves couriers and parcels into text files, and reads them back.
// One object = one line. Values are separated by commas.

public class Storage {

    static final String COURIER_FILE = "couriers.txt";
    static final String PARCEL_FILE = "parcels.txt";

    // ---------------- WRITING ----------------
    public static void saveAll() {
        saveCouriers();
        saveParcels();
    }

    private static void saveCouriers() {
        // try-with-resources closes the file automatically
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURIER_FILE))) {
            for (int i = 0; i < Main.courierCount; i++) {
                writer.write(Main.couriers[i].toFileLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save couriers: " + e.getMessage());
        }
    }

    private static void saveParcels() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PARCEL_FILE))) {
            for (int i = 0; i < Main.parcelCount; i++) {
                writer.write(Main.parcels[i].toFileLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save parcels: " + e.getMessage());
        }
    }

    // ---------------- READING ----------------
    public static void loadAll() {
        loadCouriers();
        loadParcels();
    }

    private static void loadCouriers() {
        File file = new File(COURIER_FILE);

        // Missing file: first time the program runs, so add sample couriers
        if (!file.exists()) {
            Main.addCourier(new Courier("C01", "Rahul", "9876543210", "West Zone", "AVAILABLE"));
            Main.addCourier(new Courier("C02", "Arjun", "9876543211", "North-West Zone", "BUSY"));
            Main.addCourier(new Courier("C03", "Kiran", "9876543212", "Central Zone", "AVAILABLE"));
            Main.addCourier(new Courier("C04", "Sneha", "9876543213", "East Zone", "AVAILABLE"));
            saveCouriers();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] p = line.split(",");
                if (p.length == 5) {
                    Main.addCourier(new Courier(p[0], p[1], p[2], p[3], p[4]));
                } else {
                    System.out.println("Skipped a broken line in couriers.txt");
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Could not read couriers: " + e.getMessage());
        }
    }

    private static void loadParcels() {
        File file = new File(PARCEL_FILE);

        if (!file.exists()) {
            Main.addParcel(Main.createParcel("Priya", "9000000001", "Madhapur", "Uppal", 0.5, "C01", "DELIVERED"));
            Main.addParcel(Main.createParcel("Rohit", "9000000002", "Kukatpally", "Charminar", 2.0, "C02", "IN_TRANSIT"));
            Main.addParcel(Main.createParcel("Sai", "9000000003", "Secunderabad", "Shamshabad", 3.0, "None", "PENDING"));
            saveParcels();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] p = line.split(",");
                try {
                    double weight = Double.parseDouble(p[5]);
                    int distance = Integer.parseInt(p[6]);
                    int price = Integer.parseInt(p[7]);
                    Main.addParcel(new Parcel(p[0], p[1], p[2], p[3], p[4], weight, distance,
                            price, p[8], p[9], p[10]));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    // multi-catch: a broken line should not stop the whole program
                    System.out.println("Skipped a broken line in parcels.txt");
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Could not read parcels: " + e.getMessage());
        }
    }
}
