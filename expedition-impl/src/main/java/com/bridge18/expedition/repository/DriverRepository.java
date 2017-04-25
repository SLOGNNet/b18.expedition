package com.bridge18.expedition.repository;


import com.bridge18.expedition.dto.v1.DriverDTO;
import com.bridge18.expedition.dto.v1.DriverSummaryDTO;
import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.expedition.repository.mongo.Driver;

import java.util.concurrent.CompletionStage;

public interface DriverRepository {
    CompletionStage<PaginatedSequence<DriverDTO>> getDrivers(int pageNumber, int pageSize);
}
