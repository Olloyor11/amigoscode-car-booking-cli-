package com.ollayor.booking;

import java.io.*;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDAO {

    private final String path;

    public CarBookingFileDataAccessService(String path) {
        this.path = path;
    }

    CarBooking[] bookings = new CarBooking[100];


    @Override
    public CarBooking[] getBookings() {
        File file = new File(path);
        if (!file.exists()) {
            return new CarBooking[0];
        }
        try (
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (CarBooking[]) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Could not read bookings from " + path, e);
        }
    }

    @Override
    public void addBooking(CarBooking booking) {
        CarBooking[] current = getBookings();
        CarBooking[] updated = new CarBooking[current.length + 1];
        for (int i = 0; i < current.length; i++) {
            updated[i] = current[i];
        }
        updated[current.length] = booking;
        boolean booked = booking.getCar().isBooked();
        booked = true;
        writeBookings(updated);
    }


    @Override
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
        writeBookings(allBookings);

    }

    private void writeBookings(CarBooking[] bookings) {
        File file;
        try {
            file = new File(path);
            if (!file.exists()){
                file.createNewFile();
            }
        }catch (IOException e){
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
