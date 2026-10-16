# Hyderabad Courier — Delivery Route & Tracking System

A simple Java console program (1st year BTech, Academic Year 2026-27).

**Team:** V. Uthejitha (2620080041), D. Bhavyasree (2620080023), L. Manasa (2620030500)

## Problem

Small courier offices track parcels on paper. Customers cannot easily find the price, the route
or the delivery date, and the office cannot easily see which couriers are free.

## What the program does

**Customer:** check price and delivery date, book a parcel, see my orders, track an order.
**Admin** (`admin` / `admin123`): view all orders, assign a courier, update status, search,
sort by price, summary report, view and add couriers.

77 Hyderabad areas are grouped into 6 zones. The shortest route between zones is found with
Dijkstra's algorithm. Tracking is simulated (no GPS, no internet).

- Distance = 3 km + km between zones + 3 km
- Price = Rs.30 + Rs.5 per km + Rs.10 per kg
- Delivery days = number of zones on the route

## How to run in VS Code

1. Open this folder in VS Code (File > Open Folder).
2. Open `Main.java` and click **Run** above `main`, or in the terminal type:

```
javac *.java
java Main
```

Data is saved in `couriers.txt` and `parcels.txt` (created automatically).

## Files

| File | What it does |
|---|---|
| `Main.java` | Menus for customer and admin, booking, tracking, sorting, report |
| `Courier.java` | Class for one courier |
| `Parcel.java` | Class for one parcel |
| `RouteFinder.java` | Areas, zones, road distances, Dijkstra's shortest path |
| `Storage.java` | Saves and loads data from text files |

## Topics used (Modules 1 to 5 only)

| Module | Where it is used |
|---|---|
| 1. Basics | Variables, data types, operators, typecasting `(int)`, Scanner input, `String.format` output |
| 2. Control flow | if / else-if, switch (menus, tracking), for, while (3 login tries), do-while (menus) |
| 3. Methods, recursion, arrays | Methods, method overloading (`line()`), recursion (`addPath`), 1D and 2D arrays, linear search, counting, bubble sort |
| 4. OOP and exceptions | Classes, objects, constructors, `this`, private fields, getters/setters, static members, `toString()`, try-catch, multi-catch |
| 5. Strings and files | `equals`, `equalsIgnoreCase`, `contains`, `toLowerCase`, `split`, `trim`, StringBuilder, BufferedReader/BufferedWriter, try-with-resources, missing file and broken line handling |
