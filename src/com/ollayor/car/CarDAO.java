package com.ollayor.car;

import java.math.BigDecimal;
import java.util.UUID;

public interface CarDAO {

    Car[] getCars();

    Car getCarById(UUID id);


}
