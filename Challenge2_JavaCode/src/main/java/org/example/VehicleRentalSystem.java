package org.example;

import java.util.Scanner;

// Abstract class Vehicle
//abstract class Vehicle implements Rentable
//private fields for the vehicle's registration number, brand, and availability
//Constructor to initialize the fields and Getter and setter methods for the fields
//Abstract method to calculate rent, to be implemented by subclasses.

abstract class Vehicle implements Rentable {
    private String registrationNumber;
    private String brand;
    private boolean isAvailable;

    public Vehicle(String registrationNumber, String brand, boolean isAvailable) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.isAvailable = isAvailable;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public abstract double calculateRent(int days);
}


//Interface Rentable
//calculateRent , a method to calculate rent based on the number of days.

interface Rentable {
    double calculateRent(int days);
}


//class Car that extends the abstract class Vehicle.
//Constructor to initialize the object using the superclass constructor.
//calculateRent method to return the rent based on the number of days.
// Subclass Car

class Car extends Vehicle {
    public Car(String registrationNumber, String brand, boolean isAvailable) {
        super(registrationNumber, brand, isAvailable);
    }

    @Override
    public double calculateRent(int days) {
        return days*400;
        //per day price of car is Rs 400
    }
}

// Subclass Bike
class Bike extends Vehicle {
    public Bike(String registrationNumber, String brand, boolean isAvailable) {
        super(registrationNumber, brand, isAvailable);
    }

    @Override
    public double calculateRent(int days) {
        return days*200;
        //per day price of bike is Rs 200.
    }
}

// Subclass Truck
class Truck extends Vehicle {
    public Truck(String registrationNumber, String brand, boolean isAvailable) {
        super(registrationNumber, brand, isAvailable);
    }

    @Override
    public double calculateRent(int days) {
        return days*800;
        //per day price of truck is Rs 800.
    }
}

// Factory Pattern
//Switch statement to determine the type of vehicle and create their object.

class VehicleFactory {
    public static Vehicle createVehicle(String type, String registrationNumber, String brand, boolean isAvailable) {
        switch (type.toLowerCase()) {
            case "car":
                return new Car(registrationNumber, brand, isAvailable);
            case "bike":
                return new Bike(registrationNumber, brand, isAvailable);
            case "truck":
                return new Truck(registrationNumber, brand, isAvailable);
            default:
                throw new IllegalArgumentException("Invalid vehicle");
        }
    }
}

// Main class
public class VehicleRentalSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter vehicle type -> car/bike/truck : ");
        String type = scanner.nextLine();

        System.out.print("Enter registration number: ");
        String registrationNumber = scanner.nextLine();

        System.out.print("Enter brand: ");
        String brand = scanner.nextLine();

        System.out.print("Enter number of days to rent: ");
        int days = scanner.nextInt();

        Vehicle vehicle = VehicleFactory.createVehicle(type, registrationNumber, brand, true);

        System.out.println("Rent price for " + days + " days: Rs " + vehicle.calculateRent(days));
    }
}