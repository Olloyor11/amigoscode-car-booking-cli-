package com.ollayor.car;

import com.ollayor.user.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CarService {

    private final CarDAO carDAO;

    public CarService(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public List<Car> getAllCars() {
        return carDAO.getCars();
    }

    public Car findCarById(UUID id){
        Car car = carDAO.getCarById(id);

        if (car == null){
            throw new IllegalArgumentException("Car Not Found");
        }
        return car;
    }
}
