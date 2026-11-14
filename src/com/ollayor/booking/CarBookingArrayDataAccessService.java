package com.ollayor.booking;

import java.util.List;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDAO {

    private List<CarBooking> bookings;

    public void addBooking(CarBooking booking) {
        bookings.add(booking);
    }

    public List<CarBooking> getBookings() {
        return bookings;
    }


    public void deleteBooking(UUID id) {

        List<CarBooking> allBookings = getBookings();
        CarBooking carBooking = allBookings
                .stream()
                .filter(booking -> booking.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Not Found booking with id: " + id));
        carBooking.setStatus(BookingStatus.CANCELLED);
    }
}
