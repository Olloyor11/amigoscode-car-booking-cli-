package com.ollayor.booking;

import java.util.UUID;

public interface CarBookingDAO  {

    void addBooking(CarBooking booking);

    CarBooking[] getBookings();

    void deleteBooking(UUID id);

}
