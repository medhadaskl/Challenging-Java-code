package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// Test case of vehicle Rental System
public class VehicleRentalSystemTest {

        @Test
        void testCarRentCalculation() {
            Vehicle car = new Car("CAR123", "Toyota", true);
            double rent = car.calculateRent(5);
            assertEquals(2000.0, rent, 0.01);
        }

        @Test
        void testBikeRentCalculation() {
            Vehicle bike = new Bike("BIKE321", "Yamaha", true);
            double rent = bike.calculateRent(3);
            assertEquals(600.0, rent, 0.01);
        }

        @Test
        void testTruckRentCalculation() {
            Vehicle truck = new Truck("TRUCK456", "Tata", true);
            double rent = truck.calculateRent(2);
            assertEquals(1600.0, rent, 0.01);
        }

        @Test
        void testVehicleFactoryCreatesCorrectType() {
            Vehicle car = VehicleFactory.createVehicle("car", "CAR999", "Maruti", true);
            assertTrue(car instanceof Car);

            Vehicle bike = VehicleFactory.createVehicle("bike", "BIKE999", "Honda", true);
            assertTrue(bike instanceof Bike);

            Vehicle truck = VehicleFactory.createVehicle("truck", "TRUCK999", "Mahindra", true);
            assertTrue(truck instanceof Truck);
        }

        @Test
        void testVehicleAvailabilitySetter() {
            Vehicle car = new Car("CAR123", "Kia", true);
            assertTrue(car.isAvailable());

            car.setAvailable(false);
            assertFalse(car.isAvailable());
        }

        @Test
        void testInvalidVehicleTypeThrowsException() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                VehicleFactory.createVehicle("plane", "PL123", "Bmmm", true);
            });
            assertEquals("Invalid vehicle", exception.getMessage());
        }

}
