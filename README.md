# KLHB-FED-26-9-22-courier-delivery-route-tracking
# Courier Delivery Route and Tracking System

## Project Information

**Project Title:** Courier Delivery Route and Tracking System

**Repository Name: KLHB-FED-26-9-22-courier-delivery-route-tracking

**Programming Language:** Java

**Academic Year:** 2026–27

**Team ID:** 22

**Supervisor:** 

---

## Team Members

| S.No | Name              | Student ID | 
| ---- | ------------------|----------- | 
| 1    | V. Uthejitha      | 2620080041  | 
| 2    | L. Manasa Pranavi | 2620030500  | 
| 3    | D. Bhavyasree    | 2620080023  | 


---

## Abstract

The **Courier Delivery Route and Tracking System** is a Java-based project designed to manage courier deliveries and track their delivery status.

The system stores courier details such as courier ID, sender name, receiver name, pickup location, delivery location, and delivery status. It also provides a simple method for assigning and displaying delivery routes.

The project demonstrates the practical application of Java programming concepts such as **classes, objects, methods, arrays, loops, conditional statements, switch statements, and user input using Scanner**.

The main goal of the project is to provide a simple system that can help organize courier information and monitor the progress of deliveries.

---

## Objectives

* To maintain courier and customer details.
* To assign delivery routes.
* To track the current delivery status.
* To update courier status during the delivery process.
* To search and display courier information.
* To demonstrate Java programming concepts through a real-world application.

---

## Features

* Add courier details
* Generate/store courier ID
* Store sender and receiver information
* Store pickup and delivery locations
* Assign delivery routes
* Update delivery status
* Search courier using courier ID
* Display courier details
* Track the current delivery status

---

## Technologies Used

* **Programming Language:** Java
* **IDE:** VS Code / IntelliJ IDEA / Eclipse
* **Version Control:** Git
* **Repository:** GitHub

---

## Java Concepts Used

The project uses the following Java concepts:

* Variables and data types
* Classes and objects
* Constructors
* Methods
* Arrays
* Strings
* `if-else`
* `switch`
* `for` loops
* `while` loops
* `Scanner`
* Object-oriented programming basics

---

## Project Workflow

```text
Start
  ↓
Enter Courier Details
  ↓
Generate Courier ID
  ↓
Enter Pickup & Delivery Locations
  ↓
Assign Delivery Route
  ↓
Update Delivery Status
  ↓
Search / Track Courier
  ↓
Display Courier Information
  ↓
End
```

---

## Delivery Status

The system supports different delivery stages:

```text
Order Placed
     ↓
Picked Up
     ↓
In Transit
     ↓
Out for Delivery
     ↓
Delivered
```

---

## Example

```text
========================================
   COURIER DELIVERY TRACKING SYSTEM
========================================

Enter Courier ID: C101

Courier Details
----------------------------------------
Courier ID       : C101
Sender Name      : Rahul
Receiver Name    : Anil
Pickup Location  : Hyderabad
Delivery Location: Secunderabad

Route:
Hyderabad → Bowenpally → Secunderabad

Current Status:
Out for Delivery
```

---

### Folder Description

| Folder      | Purpose                                              |
| ----------- | ---------------------------------------------------- |
| `/src`      | Java source code                                     |
| `/docs`     | Project documentation, diagrams and design documents |
| `/data`     | Input/sample data or documented data source          |
| `/results`  | Program outputs and screenshots                      |
| `/reports`  | Phase-wise and final project reports                 |
| `README.md` | Project overview and execution instructions          |

---

# Setup Instructions

## Prerequisites

Install the following:

* Java JDK
* Git
* VS Code / IntelliJ IDEA / Eclipse

Check Java installation:

```bash
java -version
```

Check Git installation:

```bash
git --version
```

---

# Clone the Repository

```bash
git clone https://github.com/<username>/<repository-name>.git
```

Move into the project folder:

```bash
cd <repository-name>
```

---

# Compile the Project

Navigate to the source folder:

```bash
cd src
```

Compile the main Java program:

```bash
javac CourierTrackingSystem.java
```

---

# Run the Project

```bash
java CourierTrackingSystem
```

Follow the instructions displayed in the terminal.

---

# Data Source

The current version uses **sample/local data entered by the user through the Java program**.

No external database is currently used.

If an external dataset or database is added in a future phase, its source and usage will be documented in the `/data` folder.

---

# Current Phase Status

**Current Phase:** `<Phase 1 / Phase 2 / Phase 3 / Final>`

**Status:** `<In Progress / Completed>`

### Phase Progress

* [ ] Problem identification
* [ ] Requirement analysis
* [ ] System design
* [ ] Java implementation
* [ ] Route management
* [ ] Courier tracking
* [ ] Testing
* [ ] Documentation
* [ ] Final submission

---

# Conclusion

The **Courier Delivery Route and Tracking System** provides a simple Java-based solution for managing courier information, delivery routes, and delivery status.

The project applies fundamental Java programming concepts to a practical real-world problem while following the required GitHub repository structure and contribution practices.

---
