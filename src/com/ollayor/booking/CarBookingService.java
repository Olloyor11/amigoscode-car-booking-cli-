package com.ollayor.booking;

import com.ollayor.car.Car;
import com.ollayor.car.CarService;
import com.ollayor.user.User;
import com.ollayor.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {

    private final CarBookingDAO carBookingDAO;
    private final UserService userService;
    private final CarService carService;


    public CarBookingService(

            CarBookingDAO carBookingDAO,
            UserService userService,
            CarService carService) {

        this.carBookingDAO = carBookingDAO;
        this.userService = userService;
        this.carService = carService;
    }

    public CarBooking bookCar(
            UUID userId,
            UUID carId,
            LocalDate startDate,
            LocalDate endDate,
            LocalDateTime bookedAt) {

        User userById = userService.findUserById(userId);
        Car carById = carService.findCarById(carId);

        if (
                startDate.isAfter(endDate) ||
                startDate.isBefore(LocalDate.now()) ||
                endDate.isBefore(LocalDate.now())) {

            System.out.println("Wrong date inserted");
        }
//        CarBooking[] bookings = carBookingDAO.getBookings();
//        for (CarBooking booking : bookings) {
//            if (booking != null && booking.getStatus().equals(BookingStatus.ACTIVE) && booking.getCar().getId().equals(carId)) {
//                System.out.println("This car is already booked");
//            }
//        }
        if (isCarBooked(carId)){
            System.out.println("This car is already booked");
        }

        long bookingPeriod;
        if (startDate.equals(endDate)) {
            bookingPeriod = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        } else {
            bookingPeriod = ChronoUnit.DAYS.between(startDate, endDate);
        }


        BigDecimal priceForBookingPeriod = BigDecimal.valueOf(bookingPeriod).
                multiply(carById.getRentalPricePerDay());


        CarBooking carBooking =
                new CarBooking(UUID.randomUUID(), userById, carById,
                        startDate, endDate, priceForBookingPeriod, BookingStatus.ACTIVE, bookedAt);

        carById.setBooked(true);
        carBookingDAO.addBooking(carBooking);
        return carBooking;
    }

    public Car[] getAllAvailableCars() {
        Car[] cars = carService.getAllAvailableCars();
        int count = 0;
        for (Car car : cars) {
            if (!isCarBooked(car.getId())) {
                count++;
            }
        }

        Car[] availableCars = new Car[count];
        int index = 0;
        for (Car car : cars) {
            if (!isCarBooked(car.getId())) {
                availableCars[index++] = car;
            }
        }
        return availableCars;

    }

    public Car[] getAllAvailableElectricCars() {
        Car[] cars = carService.getAllAvailableElectricCars();
        int count = 0;
        for (Car car : cars) {
            if (car.isElectric() && !isCarBooked(car.getId())) {
                count++;
            }
        }

        Car[] electricCars = new Car[count];
        int index = 0;
        for (Car car : cars) {
            if (car.isElectric() && !isCarBooked(car.getId())) {
                electricCars[index++] = car;
            }
        }
        return electricCars;

    }

    public boolean isCarBooked(UUID id) {
        for (CarBooking booking : getAllBookings()) {
            if (
                    booking != null &&
                    booking.getStatus() == BookingStatus.ACTIVE &&
                    booking.getCar().getId().equals(id)){
                return true;
            }
        }
        return false;
    }


    public CarBooking[] getAllBookings() {
        CarBooking[] bookings = carBookingDAO.getBookings();
        int count = 0;
        for (CarBooking booking : bookings) {
            if (booking != null) {
                count++;
            }
        }

        CarBooking[] allBooking = new CarBooking[count];
        int index = 0;
        for (CarBooking booking : bookings) {
            if (booking != null) {
                allBooking[index++] = booking;
            }
        }
        return allBooking;

    }

    public CarBooking[] getUserBookings(UUID userId) {

        CarBooking[] bookings = carBookingDAO.getBookings();
        int count = 0;
        for (CarBooking booking : bookings) {
            if (booking != null && booking.getUser().getId().equals(userId)) {
                count++;
            }
        }

        CarBooking[] userBooking = new CarBooking[count];
        int index = 0;
        for (CarBooking booking : bookings) {
            if (booking != null && booking.getUser().getId().equals(userId)) {
                userBooking[index++] = booking;
            }
        }
        return userBooking;

    }

    public void cancelBooking(UUID id) {
        carBookingDAO.deleteBooking(id);
    }

}
