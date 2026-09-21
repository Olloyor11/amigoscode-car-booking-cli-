package com.ollayor.booking;

import com.ollayor.car.Car;
import com.ollayor.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CarBooking {
    private UUID id;
    private User user;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingStatus status;
    private LocalDateTime bookedAt;
}
