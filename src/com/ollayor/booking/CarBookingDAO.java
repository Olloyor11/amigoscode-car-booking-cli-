package com.ollayor.booking;

import java.util.UUID;

public class CarBookingDAO {

    private CarBooking[] bookings = new CarBooking[100];

    public void addBooking(CarBooking booking) {
        int bookingCount = 0;
        for (int i = 0; i < bookings.length; i++) {
            if (bookings[i] != null){
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
        for (CarBooking booking : allBookings) {
            if (booking != null && booking.getId().equals(id)) {
                booking.getCar().setBooked(false);
                find = true;
            }
        }
        if (!find) {
            throw new IllegalArgumentException("Not Found booking with id: " + id);
        }

        int count = 0;
        for (CarBooking booking : allBookings) {
            if (booking != null && !booking.getId().equals(id)) {
                count++;
            }
        }
        CarBooking[] carBookings = new CarBooking[count];
        int index = 0;
        for (CarBooking booking : getBookings()) {
            if (booking != null && !booking.getId().equals(id)) {
                carBookings[index++] = booking;
            }
        }
        this.bookings = carBookings;

    }

}
