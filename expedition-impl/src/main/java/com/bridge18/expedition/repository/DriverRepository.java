package com.bridge18.expedition.repository;


import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.expedition.entities.driver.DriverState;

import java.util.concurrent.CompletionStage;

public interface DriverRepository {
    PaginatedSequence<DriverState> getDrivers(int pageNumber, int pageSize);
}
