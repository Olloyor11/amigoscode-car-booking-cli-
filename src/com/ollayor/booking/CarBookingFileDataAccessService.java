package com.ollayor.booking;

import com.ollayor.car.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDAO {

    private final String path;

    public CarBookingFileDataAccessService(String path) {
        this.path = path;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CarBooking> getBookings() {
        File file = new File(path);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<CarBooking>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Could not read bookings from " + path, e);
        }
    }
    @Override
    public void addBooking(CarBooking booking) {
        List<CarBooking> bookings = getBookings();
        bookings.add(booking);
        writeBookings(bookings);
    }


    @Override
    public void deleteBooking(UUID id) {
        List<CarBooking> allBookings = getBookings();
        CarBooking carBooking = allBookings
                .stream()
                .filter(booking -> booking.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Not Found booking with id: " + id));
        carBooking.setStatus(BookingStatus.CANCELLED);
        writeBookings(allBookings);

    }

    private void writeBookings(List<CarBooking> bookings) {
        File file;
        try {
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        try (

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
            out.writeObject(bookings);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not write bookings to " + path, e);
        }
    }
}
