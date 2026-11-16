package com.ollayor.car;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CarDAO {

    List<Car> getCars();

    Car getCarById(UUID id);


}
