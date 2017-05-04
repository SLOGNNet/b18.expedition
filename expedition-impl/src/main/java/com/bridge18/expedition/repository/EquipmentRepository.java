package com.bridge18.expedition.repository;

import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.expedition.entities.equipment.EquipmentState;

public interface EquipmentRepository {
    PaginatedSequence<EquipmentState> getEquipments(int pageNumber, int pageSize);
}
