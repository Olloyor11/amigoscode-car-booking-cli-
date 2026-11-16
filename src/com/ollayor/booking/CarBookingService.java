package com.ollayor.booking;

import com.ollayor.car.Car;
import com.ollayor.car.CarService;
import com.ollayor.user.User;
import com.ollayor.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

        if (startDate.isAfter(endDate) ||
                        startDate.isBefore(LocalDate.now()) ||
                        endDate.isBefore(LocalDate.now())) {

            throw new IllegalArgumentException("Wrong date inserted");
        }

        if (isCarBooked(carId)) {
            throw new IllegalArgumentException("This car is already booked");
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

        carBookingDAO.addBooking(carBooking);
        return carBooking;
    }

    public Set<UUID> bookedCarsIds(){
        Set<UUID> bookedCars = getAllBookings()
                .stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.ACTIVE))
                .map(booking -> booking.getCar().getId())
                .collect(Collectors.toSet());
        return bookedCars;
    }

    public List<Car> getAllAvailableCars() {
        Set<UUID> carsIds = bookedCarsIds();
        return carService.getAllCars()
                .stream()
                .filter(car -> !carsIds.contains(car.getId()))
                .toList();
    }

    public List<Car> getAllAvailableElectricCars() {
        Set<UUID> carsIds = bookedCarsIds();
        return carService.getAllCars()
                .stream()
                .filter(Car::isElectric)
                .filter(car -> !carsIds.contains(car.getId()))
                .toList();
    }

    public boolean isCarBooked(UUID id) {

        if (getAllBookings().isEmpty()) {
            return false;
        }

        return getAllBookings()
                .stream()
                .anyMatch(
                        booking -> booking.getStatus().equals(BookingStatus.ACTIVE) &&
                                booking.getCar().getId().equals(id));

    }


    public List<CarBooking> getAllBookings() {
        return carBookingDAO.getBookings();

    }

    public List<CarBooking> getUserBookings(UUID userId) {

        List<CarBooking> bookings = carBookingDAO.getBookings();
        return bookings
                .stream()
                .filter(booking -> booking.getUser().getId().equals(userId))
                .toList();

    }

    public void cancelBooking(UUID id) {
        carBookingDAO.deleteBooking(id);
    }

}
