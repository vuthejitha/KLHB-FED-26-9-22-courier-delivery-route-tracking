// RouteFinder.java
// Stores all areas, the 6 zones, the roads between zones,
// and finds the shortest route using Dijkstra's algorithm.
// This is SIMULATED. No GPS, no maps, no internet.

public class RouteFinder {

    // The 6 zones (nodes of our graph)
    public static final String[] ZONES = {
        "West Zone", "North-West Zone", "North Zone", "Central Zone", "East Zone", "South Zone"
    };

    // ROADS[i][j] = km between zone i and zone j (0 = no direct road)
    // Order: West, North-West, North, Central, East, South
    public static final int[][] ROADS = {
        { 0, 12,  0, 10,  0, 14},   // West
        {12,  0, 14, 12,  0,  0},   // North-West
        { 0, 14,  0,  8, 11,  0},   // North
        {10, 12,  8,  0,  9, 10},   // Central
        { 0,  0, 11,  9,  0, 12},   // East
        {14,  0,  0, 10, 12,  0}    // South
    };

    // Distance from an area to the hub of its own zone
    public static final int LOCAL_KM = 3;

    // Every area and its zone: { area name, zone name }
    public static final String[][] AREAS = {
        {"Madhapur", "West Zone"}, {"Gachibowli", "West Zone"}, {"Kondapur", "West Zone"},
        {"Hitec City", "West Zone"}, {"Nanakramguda", "West Zone"}, {"Manikonda", "West Zone"},
        {"Kokapet", "West Zone"}, {"Jubilee Hills", "West Zone"}, {"Banjara Hills", "West Zone"},
        {"Shaikpet", "West Zone"}, {"Tolichowki", "West Zone"}, {"Mehdipatnam", "West Zone"},
        {"Khajaguda", "West Zone"}, {"Puppalguda", "West Zone"}, {"Financial District", "West Zone"},

        {"Kukatpally", "North-West Zone"}, {"KPHB Colony", "North-West Zone"}, {"Miyapur", "North-West Zone"},
        {"Nizampet", "North-West Zone"}, {"Bachupally", "North-West Zone"}, {"Pragathi Nagar", "North-West Zone"},
        {"Chanda Nagar", "North-West Zone"}, {"Hafeezpet", "North-West Zone"}, {"Madeenaguda", "North-West Zone"},
        {"BHEL", "North-West Zone"}, {"Lingampally", "North-West Zone"}, {"Moosapet", "North-West Zone"},

        {"Secunderabad", "North Zone"}, {"Begumpet", "North Zone"}, {"Bowenpally", "North Zone"},
        {"Kompally", "North Zone"}, {"Medchal", "North Zone"}, {"Jeedimetla", "North Zone"},
        {"Balanagar", "North Zone"}, {"Alwal", "North Zone"}, {"Neredmet", "North Zone"},
        {"Malkajgiri", "North Zone"}, {"Sainikpuri", "North Zone"}, {"Trimulgherry", "North Zone"},
        {"Marredpally", "North Zone"}, {"Tarnaka", "North Zone"},

        {"Ameerpet", "Central Zone"}, {"Panjagutta", "Central Zone"}, {"Somajiguda", "Central Zone"},
        {"Abids", "Central Zone"}, {"Koti", "Central Zone"}, {"Himayatnagar", "Central Zone"},
        {"Narayanguda", "Central Zone"}, {"Chikkadpally", "Central Zone"}, {"Khairatabad", "Central Zone"},
        {"Lakdikapul", "Central Zone"}, {"Nampally", "Central Zone"}, {"Basheerbagh", "Central Zone"},

        {"Uppal", "East Zone"}, {"Habsiguda", "East Zone"}, {"Nagole", "East Zone"},
        {"L.B. Nagar", "East Zone"}, {"Dilsukhnagar", "East Zone"}, {"Malakpet", "East Zone"},
        {"Vanasthalipuram", "East Zone"}, {"Hayathnagar", "East Zone"}, {"Saroornagar", "East Zone"},
        {"Kothapet", "East Zone"}, {"Boduppal", "East Zone"}, {"Peerzadiguda", "East Zone"},
        {"Pocharam", "East Zone"},

        {"Shamshabad", "South Zone"}, {"Attapur", "South Zone"}, {"Rajendranagar", "South Zone"},
        {"Aramghar", "South Zone"}, {"Chandrayangutta", "South Zone"}, {"Charminar", "South Zone"},
        {"Santosh Nagar", "South Zone"}, {"Saidabad", "South Zone"}, {"Bahadurpura", "South Zone"},
        {"Falakhnuma", "South Zone"}, {"Gachibowli Outer (TSPA Area)", "South Zone"}
    };

    // Linear search: returns the zone of an area, or null if not found
    public static String getZone(String area) {
        for (int i = 0; i < AREAS.length; i++) {
            if (AREAS[i][0].equalsIgnoreCase(area)) {
                return AREAS[i][1];
            }
        }
        return null;
    }

    // Returns the position of a zone in the ZONES array
    public static int getZoneIndex(String zone) {
        for (int i = 0; i < ZONES.length; i++) {
            if (ZONES[i].equals(zone)) {
                return i;
            }
        }
        return -1;
    }

    // All areas of one zone (counting technique: count first, then fill the array)
    public static String[] getAreasInZone(String zone) {
        int count = 0;
        for (int i = 0; i < AREAS.length; i++) {
            if (AREAS[i][1].equals(zone)) {
                count++;
            }
        }

        String[] result = new String[count];
        int k = 0;
        for (int i = 0; i < AREAS.length; i++) {
            if (AREAS[i][1].equals(zone)) {
                result[k] = AREAS[i][0];
                k++;
            }
        }
        return result;
    }

    // ---------------- DIJKSTRA'S ALGORITHM ----------------
    // Fills previous[] (where we came from) and returns the shortest distance to every zone
    public static int[] dijkstra(int start, int[] previous) {
        int n = ZONES.length;
        int[] shortest = new int[n];
        boolean[] visited = new boolean[n];

        // Step 1: every zone is "very far" (99999 = infinity), start zone is 0
        for (int i = 0; i < n; i++) {
            shortest[i] = 99999;
            previous[i] = -1;
        }
        shortest[start] = 0;

        for (int count = 0; count < n; count++) {

            // Step 2: pick the unvisited zone with the smallest distance
            int current = -1;
            for (int i = 0; i < n; i++) {
                if (!visited[i] && (current == -1 || shortest[i] < shortest[current])) {
                    current = i;
                }
            }
            visited[current] = true;

            // Step 3: update the neighbours if we found a shorter way
            for (int next = 0; next < n; next++) {
                if (ROADS[current][next] > 0 && !visited[next]) {
                    int newDistance = shortest[current] + ROADS[current][next];
                    if (newDistance < shortest[next]) {
                        shortest[next] = newDistance;
                        previous[next] = current;
                    }
                }
            }
        }
        return shortest;
    }

    // RECURSION: to write the path to a zone, first write the path to the zone before it
    private static void addPath(StringBuilder sb, int[] previous, int zone) {
        if (previous[zone] != -1) {                    // recursive case
            addPath(sb, previous, previous[zone]);
            sb.append(" -> ");
        }
        sb.append(ZONES[zone]);                        // base case: the start zone
    }

    // Total km = 3 km local + km between zones + 3 km local
    public static int getDistance(String fromArea, String toArea) {
        int start = getZoneIndex(getZone(fromArea));
        int end = getZoneIndex(getZone(toArea));
        int[] previous = new int[ZONES.length];
        int[] shortest = dijkstra(start, previous);
        return LOCAL_KM + shortest[end] + LOCAL_KM;
    }

    // Delivery days = number of zones on the path (same zone = 1 day)
    public static int getDays(String fromArea, String toArea) {
        int start = getZoneIndex(getZone(fromArea));
        int end = getZoneIndex(getZone(toArea));
        int[] previous = new int[ZONES.length];
        dijkstra(start, previous);

        int days = 1;
        int zone = end;
        while (previous[zone] != -1) {
            days++;
            zone = previous[zone];
        }
        return days;
    }

    // The full route written as text (StringBuilder = mutable string)
    public static String getRouteText(String fromArea, String toArea) {
        int start = getZoneIndex(getZone(fromArea));
        int end = getZoneIndex(getZone(toArea));
        int[] previous = new int[ZONES.length];
        dijkstra(start, previous);

        StringBuilder sb = new StringBuilder();
        sb.append("Route : ").append(fromArea).append(" -> ");
        addPath(sb, previous, end);
        sb.append(" -> ").append(toArea);
        return sb.toString();
    }
}
