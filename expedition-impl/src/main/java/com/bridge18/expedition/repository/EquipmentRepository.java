package com.bridge18.expedition.repository;

import com.bridge18.expedition.dto.v1.EquipmentDTO;
import com.bridge18.expedition.dto.v1.PaginatedSequence;

import java.util.concurrent.CompletionStage;

public interface EquipmentRepository {
    CompletionStage<PaginatedSequence<EquipmentDTO>> getEquipments(int pageNumber, int pageSize);
}
