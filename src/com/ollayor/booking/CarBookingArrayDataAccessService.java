package com.ollayor.booking;

import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDAO{

    private CarBooking[] bookings = new CarBooking[100];

    public void addBooking(CarBooking booking) {
        int bookingCount = 0;
        for (int i = 0; i < bookings.length; i++) {
            if (bookings[i] != null) {
                bookingCount++;
            }
        }
        if (bookingCount == bookings.length) {
            CarBooking[] temp = new CarBooking[bookingCount * 2];
            for (int i = 0; i < bookings.length; i++) {
                temp[i] = bookings[i];

            }
            bookings = temp;
        }
        bookings[bookingCount] = booking;
    }

    public CarBooking[] getBookings() {
        return bookings;
    }


    public void deleteBooking(UUID id) {

        CarBooking[] allBookings = getBookings();

        boolean find = false;
        for (int i = 0; i < allBookings.length; i++) {
            if (allBookings[i] != null && allBookings[i].getId().equals(id)) {
                allBookings[i].getCar().setBooked(false);
                allBookings[i] = null;
                find = true;
            }
        }

        if (!find) {
            throw new IllegalArgumentException("Not Found booking with id: " + id);
        }

        int index = 0;
        for (CarBooking booking : allBookings) {
            if (booking != null && !booking.getId().equals(id)) {
                allBookings[index++] = booking;
            }
        }
        this.bookings = allBookings;
    }
}
