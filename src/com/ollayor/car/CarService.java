package com.ollayor.car;

import com.ollayor.user.User;

import java.util.UUID;

public class CarService {

    private final CarDAO carDAO;

    public CarService(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public Car[] getAllAvailableCars() {
        Car[] cars = carDAO.getCars();
        int count = 0;
        for (Car car : cars) {
            if (!car.isBooked()) {
                count++;
            }
        }

        Car[] availableCars = new Car[count];
        int index = 0;
        for (Car car : cars) {
            if (!car.isBooked()) {
                availableCars[index++] = car;
            }
        }
        return availableCars;

    }

    public Car findCarById(UUID id){
        Car car = carDAO.getCarById(id);

        if (car == null){
            throw new IllegalArgumentException("Car Not Found");
        }
        return car;
    }

    public Car[] getAllAvailableElectricCars() {
        Car[] cars = carDAO.getCars();
        int count = 0;
        for (Car car : cars) {
            if (car.isElectric() && !car.isBooked()) {
                count++;
            }
        }

        Car[] electricCars = new Car[count];
        int index = 0;
        for (Car car : cars) {
            if (car.isElectric() && !car.isBooked()) {
                electricCars[index++] = car;
            }
        }
        return electricCars;

    }
}
