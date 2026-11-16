package com.ollayor.booking;

import java.util.List;
import java.util.UUID;

public interface CarBookingDAO  {

    void addBooking(CarBooking booking);

    List<CarBooking> getBookings();

    void deleteBooking(UUID id);

}
