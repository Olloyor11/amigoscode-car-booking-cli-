package com.ollayor.car;

import java.math.BigDecimal;
import java.util.UUID;

public class CarArrayDataAccessService implements CarDAO{
    private static final Car[] cars;

    static {
        cars = new Car[]{
                new Car(UUID.fromString("fbccd80e-3881-4f6d-94e8-79599f386330"),
                        "WZ777AN",
                        new BigDecimal(60),
                        Brand.AUDI,
                        false),
                new Car(
                        UUID.fromString("f3c3bdf4-2b18-4c5d-9b1f-7a9d8d7d1001"),
                        "WA12345",
                        new BigDecimal("120.00"),
                        Brand.TESLA,
                        true
                ),
                new Car(
                        UUID.fromString("8b7d7b5d-5a2d-4b4c-95d2-8f3f9a1b1002"),
                        "KR56789",
                        new BigDecimal("95.50"),
                        Brand.AUDI,
                        false
                ),
                new Car(
                        UUID.fromString("c1d45f67-3f24-44be-a321-9b6f3d7c1003"),
                        "GD34567",
                        new BigDecimal("140.00"),
                        Brand.MERCEDES,
                        false
                ),
                new Car(
                        UUID.fromString("d5a8c3f2-11d9-42b4-8d55-2a7d5e9b1004"),
                        "PO98765",
                        new BigDecimal("80.00"),
                        Brand.TOYOTA,
                        false
                ),
                new Car(
                        UUID.fromString("e2b9d1c7-9f44-4d4b-92b7-5f8d6a3e1005"),
                        "LU24680",
                        new BigDecimal("150.00"),
                        Brand.TESLA,
                        true
                ),
                new Car(
                        UUID.fromString("f7c1a3d8-2b4f-45c7-9d8e-6a2b5f4d1006"),
                        "WR11223",
                        new BigDecimal("110.00"),
                        Brand.AUDI,
                        false
                ),
                new Car(
                        UUID.fromString("a4d8e2b1-6f33-4d8a-8f77-3b5d9c2a1007"),
                        "KT77889",
                        new BigDecimal("170.00"),
                        Brand.MERCEDES,
                        false
                ),
                new Car(
                        UUID.fromString("b8e5d9c2-4a22-47f1-91c4-7d3a6b8f1008"),
                        "EL55667",
                        new BigDecimal("75.00"),
                        Brand.TOYOTA,
                        false
                ),
                new Car(
                        UUID.fromString("c9f2b7a4-8d11-4c6e-a8b5-1f7d9e3c1009"),
                        "WX33445",
                        new BigDecimal("160.00"),
                        Brand.TESLA,
                        true
                ),
                new Car(
                        UUID.fromString("d3a7e5f8-1c55-4b9d-93e8-8c2f6a4b1010"),
                        "SZ99887",
                        new BigDecimal("100.00"),
                        Brand.AUDI,
                        false
                )
        };


    }

    @Override
    public  Car[] getCars() {
        return cars;
    }

    @Override
    public Car getCarById(UUID id) {
        for (Car car : cars) {
            if (car.getId().equals(id)) {
                return car;
            }
        }
        throw new IllegalArgumentException("Car Not Found");
    }
}
