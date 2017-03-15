package com.bridge18.expedition.services.lagom;

import akka.Done;
import akka.NotUsed;
import com.bridge18.expedition.api.LagomEquipmentService;
import com.bridge18.expedition.dto.v1.EquipmentDTO;
import com.bridge18.expedition.dto.v1.EquipmentSummary;
import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.expedition.repository.EquipmentRepository;
import com.bridge18.expedition.services.objects.EquipmentService;
import com.google.common.collect.Lists;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LagomEquipmentServiceImpl implements LagomEquipmentService {
    private EquipmentService equipmentService;
    private EquipmentRepository equipmentRepository;

    @Inject
    public LagomEquipmentServiceImpl(EquipmentService equipmentService, EquipmentRepository equipmentRepository) {
        this.equipmentService = equipmentService;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public ServiceCall<EquipmentDTO, EquipmentDTO> createNewEquipment() {
        return request -> {
            return equipmentService.createEquipment(Optional.ofNullable(request.vin),
                    Optional.ofNullable(request.ownership),
                    Optional.ofNullable(Optional.ofNullable(request.type).orElseThrow(
                            () -> new WebServiceException("Type field is mandatory"))),
                    Optional.ofNullable(Optional.ofNullable(request.subType).orElseThrow(
                            () -> new WebServiceException("Subtype field is mandatory"))),
                    Optional.ofNullable(request.operatingMode),
                    Optional.ofNullable(request.make),
                    Optional.ofNullable(request.model),
                    Optional.ofNullable(request.colour),
                    Optional.ofNullable(request.isSleeperBerthAvailable),
                    Optional.ofNullable(request.number),
                    Optional.ofNullable(request.licensePlateState),
                    Optional.ofNullable(request.licensePlateNumber),
                    Optional.ofNullable(request.licensePlateExpiration),
                    Optional.ofNullable(request.notes),
                    Optional.ofNullable(request.mileageRecords)
            ).thenApply(equipmentState ->
                    new EquipmentDTO(equipmentState.getId(),
                            equipmentState.getVin().orElse(null),
                            equipmentState.getOwnership().orElse(null),
                            equipmentState.getType().orElseThrow(() -> new WebServiceException("Type field is mandatory")),
                            equipmentState.getSubType().orElseThrow(() -> new WebServiceException("Subtype field is mandatory")),
                            equipmentState.getOperatingMode().orElse(null),
                            equipmentState.getMake().orElse(null),
                            equipmentState.getModel().orElse(null),
                            equipmentState.getColour().orElse(null),
                            equipmentState.getIsSleeperBerthAvailable().orElse(null),
                            equipmentState.getNumber().orElse(null),
                            equipmentState.getLicensePlateState().orElse(null),
                            equipmentState.getLicensePlateNumber().orElse(null),
                            equipmentState.getLicensePlateExpiration().orElse(null),
                            equipmentState.getNotes().orElse(null),
                            equipmentState.getMileageRecords().orElse(null)
                    )

            );
        };

    }

    @Override
    public ServiceCall<EquipmentDTO, EquipmentDTO> updateEquipmentInformation(String id) {
        return request -> {
            return equipmentService.changeEquipment(id,
                    Optional.ofNullable(request.vin),
                    Optional.ofNullable(request.ownership),
                    Optional.ofNullable(Optional.ofNullable(request.type).orElseThrow(
                            () -> new WebServiceException("Type field is mandatory"))),
                    Optional.ofNullable(Optional.ofNullable(request.subType).orElseThrow(
                            () -> new WebServiceException("Subtype field is mandatory"))),
                    Optional.ofNullable(request.operatingMode),
                    Optional.ofNullable(request.make),
                    Optional.ofNullable(request.model),
                    Optional.ofNullable(request.colour),
                    Optional.ofNullable(request.isSleeperBerthAvailable),
                    Optional.ofNullable(request.number),
                    Optional.ofNullable(request.licensePlateState),
                    Optional.ofNullable(request.licensePlateNumber),
                    Optional.ofNullable(request.licensePlateExpiration),
                    Optional.ofNullable(request.notes),
                    Optional.ofNullable(request.mileageRecords)
            )
                    .thenApply(equipmentState -> {
                                return new EquipmentDTO(equipmentState.getId(),
                                        equipmentState.getVin().orElse(null),
                                        equipmentState.getOwnership().orElse(null),
                                        equipmentState.getType().orElseThrow(() -> new WebServiceException("Type field is mandatory")),
                                        equipmentState.getSubType().orElseThrow(() -> new WebServiceException("Subtype field is mandatory")),
                                        equipmentState.getOperatingMode().orElse(null),
                                        equipmentState.getMake().orElse(null),
                                        equipmentState.getModel().orElse(null),
                                        equipmentState.getColour().orElse(null),
                                        equipmentState.getIsSleeperBerthAvailable().orElse(null),
                                        equipmentState.getNumber().orElse(null),
                                        equipmentState.getLicensePlateState().orElse(null),
                                        equipmentState.getLicensePlateNumber().orElse(null),
                                        equipmentState.getLicensePlateExpiration().orElse(null),
                                        equipmentState.getNotes().orElse(null),
                                        equipmentState.getMileageRecords().orElse(null)
                                );
                            }

                    );
        };
    }

    @Override
    public ServiceCall<NotUsed, Done> deleteEquipment(String id) {
        return request ->
                equipmentService.deleteEquipment(id);
    }

    @Override
    public ServiceCall<NotUsed, EquipmentDTO> getEquipment(String id) {
        return request ->
                equipmentService.getEquipment(id)

                        .thenApply(equipmentState -> {
                                    return new EquipmentDTO(equipmentState.getId(),
                                            equipmentState.getVin().orElse(null),
                                            equipmentState.getOwnership().orElse(null),
                                            equipmentState.getType().orElseThrow(() -> new WebServiceException("Type field is mandatory")),
                                            equipmentState.getSubType().orElseThrow(() -> new WebServiceException("Subtype field is mandatory")),
                                            equipmentState.getOperatingMode().orElse(null),
                                            equipmentState.getMake().orElse(null),
                                            equipmentState.getModel().orElse(null),
                                            equipmentState.getColour().orElse(null),
                                            equipmentState.getIsSleeperBerthAvailable().orElse(null),
                                            equipmentState.getNumber().orElse(null),
                                            equipmentState.getLicensePlateState().orElse(null),
                                            equipmentState.getLicensePlateNumber().orElse(null),
                                            equipmentState.getLicensePlateExpiration().orElse(null),
                                            equipmentState.getNotes().orElse(null),
                                            equipmentState.getMileageRecords().orElse(null)
                                    );
                                }

                        );
    }

    @Override
    public ServiceCall<NotUsed, PaginatedSequence<EquipmentSummary>> getEquipmentSummaries(Optional<Integer> pageNo, Optional<Integer> pageSize) {
        return request -> equipmentRepository.getEquipments(pageNo.orElse(0), pageSize.orElse(10));
    }
}
