package com.bridge18.expedition.services.lagom;

import akka.Done;
import akka.NotUsed;
import com.bridge18.expedition.api.LagomEquipmentService;
import com.bridge18.expedition.dto.v1.EquipmentDTO;
import com.bridge18.expedition.dto.v1.EquipmentSummary;
import com.bridge18.expedition.dto.v1.MileageRecordDTO;
import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.expedition.entities.equipment.EquipmentSubType;
import com.bridge18.expedition.entities.equipment.EquipmentType;
import com.bridge18.expedition.entities.equipment.OperatingMode;
import com.bridge18.expedition.entities.equipment.Ownership;
import com.bridge18.expedition.repository.EquipmentRepository;
import com.bridge18.expedition.services.objects.EquipmentService;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
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
        return request ->{
            MileageRecordDTO mileageRecord = Optional.ofNullable(request.mileageRecord).orElse(new MileageRecordDTO());
            return equipmentService.createEquipment(Optional.ofNullable(request.vin),
                    Optional.ofNullable(Ownership.values()[Optional.ofNullable(request.ownership).orElse(0)]),
                    Optional.ofNullable(EquipmentType.values()[Optional.ofNullable(request.type).orElseThrow(
                            () -> new WebServiceException("Type field is mandatory"))]),
                    Optional.ofNullable(EquipmentSubType.values()[Optional.ofNullable(request.subType).orElseThrow(
                            () -> new WebServiceException("Subtype field is mandatory"))]),
                    Optional.ofNullable(OperatingMode.values()[Optional.ofNullable(request.operatingMode).orElse(0)]),
                    Optional.ofNullable(request.make),
                    Optional.ofNullable(request.model),
                    Optional.ofNullable(request.colour),
                    Optional.ofNullable(request.isSleeperBerthAvailable),
                    Optional.ofNullable(request.number),
                    Optional.ofNullable(request.licensePlateState),
                    Optional.ofNullable(request.licensePlateNumber),
                    Optional.ofNullable(request.licensePlateExpiration),
                    Optional.ofNullable(request.notes),
                    Optional.ofNullable(mileageRecord.miles),
                    Optional.ofNullable(mileageRecord.takenAt)
            ).thenApply(equipmentState -> {
                        MileageRecordDTO mileageRecordDTO = new MileageRecordDTO(equipmentState.getMiles().orElse(null),
                                equipmentState.getTakenAt().orElse(null));
                        return new EquipmentDTO(equipmentState.getId(),
                                equipmentState.getVin().orElse(null),
                                equipmentState.getOwnership().orElse(Ownership.COMPANY).ordinal(),
                                equipmentState.getType().orElseThrow(() -> new WebServiceException("Type field is mandatory"))
                                        .ordinal(),
                                equipmentState.getSubType().orElseThrow(() -> new WebServiceException("Subtype field is mandatory"))
                                        .ordinal(),
                                equipmentState.getOperatingMode().orElse(OperatingMode.INTER_STATE).ordinal(),
                                equipmentState.getMake().orElse(null),
                                equipmentState.getModel().orElse(null),
                                equipmentState.getColour().orElse(null),
                                equipmentState.getIsSleeperBerthAvailable().orElse(null),
                                equipmentState.getNumber().orElse(null),
                                equipmentState.getLicensePlateState().orElse(null),
                                equipmentState.getLicensePlateNumber().orElse(null),
                                equipmentState.getLicensePlateExpiration().orElse(null),
                                equipmentState.getNotes().orElse(null),
                                mileageRecordDTO
                        );
                    }

            );
        };

    }

    @Override
    public ServiceCall<EquipmentDTO, EquipmentDTO> changeEquipmentInformation(String id) {
        return request -> {
            MileageRecordDTO mileageRecord = Optional.ofNullable(request.mileageRecord).orElse(new MileageRecordDTO());
            return equipmentService.changeEquipment(id,
                    Optional.ofNullable(request.vin),
                    Optional.ofNullable(Ownership.values()[Optional.ofNullable(request.ownership).orElse(0)]),
                    Optional.ofNullable(EquipmentType.values()[Optional.ofNullable(request.type).orElseThrow(
                            () -> new WebServiceException("Type field is mandatory"))]),
                    Optional.ofNullable(EquipmentSubType.values()[Optional.ofNullable(request.subType).orElseThrow(
                            () -> new WebServiceException("Subtype field is mandatory"))]),
                    Optional.ofNullable(OperatingMode.values()[Optional.ofNullable(request.operatingMode).orElse(0)]),
                    Optional.ofNullable(request.make),
                    Optional.ofNullable(request.model),
                    Optional.ofNullable(request.colour),
                    Optional.ofNullable(request.isSleeperBerthAvailable),
                    Optional.ofNullable(request.number),
                    Optional.ofNullable(request.licensePlateState),
                    Optional.ofNullable(request.licensePlateNumber),
                    Optional.ofNullable(request.licensePlateExpiration),
                    Optional.ofNullable(request.notes),
                    Optional.ofNullable(mileageRecord.miles),
                    Optional.ofNullable(mileageRecord.takenAt)
            )
                    .thenApply(equipmentState -> {
                                MileageRecordDTO mileageRecordDTO = new MileageRecordDTO(equipmentState.getMiles().orElse(null),
                                        equipmentState.getTakenAt().orElse(null));
                                return new EquipmentDTO(equipmentState.getId(),
                                        equipmentState.getVin().orElse(null),
                                        equipmentState.getOwnership().orElse(Ownership.COMPANY).ordinal(),
                                        equipmentState.getType().orElseThrow(() -> new WebServiceException("Type field is mandatory"))
                                                .ordinal(),
                                        equipmentState.getSubType().orElseThrow(() -> new WebServiceException("Subtype field is mandatory"))
                                                .ordinal(),
                                        equipmentState.getOperatingMode().orElse(OperatingMode.INTER_STATE).ordinal(),
                                        equipmentState.getMake().orElse(null),
                                        equipmentState.getModel().orElse(null),
                                        equipmentState.getColour().orElse(null),
                                        equipmentState.getIsSleeperBerthAvailable().orElse(null),
                                        equipmentState.getNumber().orElse(null),
                                        equipmentState.getLicensePlateState().orElse(null),
                                        equipmentState.getLicensePlateNumber().orElse(null),
                                        equipmentState.getLicensePlateExpiration().orElse(null),
                                        equipmentState.getNotes().orElse(null),
                                        mileageRecordDTO
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
                                    MileageRecordDTO mileageRecordDTO = new MileageRecordDTO(equipmentState.getMiles().orElse(null),
                                            equipmentState.getTakenAt().orElse(null));
                                    return new EquipmentDTO(equipmentState.getId(),
                                            equipmentState.getVin().orElse(null),
                                            equipmentState.getOwnership().orElse(Ownership.COMPANY).ordinal(),
                                            equipmentState.getType().orElseThrow(() -> new WebServiceException("Type field is mandatory"))
                                                    .ordinal(),
                                            equipmentState.getSubType().orElseThrow(() -> new WebServiceException("Subtype field is mandatory"))
                                                    .ordinal(),
                                            equipmentState.getOperatingMode().orElse(OperatingMode.INTER_STATE).ordinal(),
                                            equipmentState.getMake().orElse(null),
                                            equipmentState.getModel().orElse(null),
                                            equipmentState.getColour().orElse(null),
                                            equipmentState.getIsSleeperBerthAvailable().orElse(null),
                                            equipmentState.getNumber().orElse(null),
                                            equipmentState.getLicensePlateState().orElse(null),
                                            equipmentState.getLicensePlateNumber().orElse(null),
                                            equipmentState.getLicensePlateExpiration().orElse(null),
                                            equipmentState.getNotes().orElse(null),
                                            mileageRecordDTO
                                    );
                                }

                        );
    }

    @Override
    public ServiceCall<NotUsed, PaginatedSequence<EquipmentSummary>> getEquipmentSummaries(Optional<Integer> pageNo, Optional<Integer> pageSize) {
        return request -> equipmentRepository.getEquipments(pageNo.orElse(0), pageSize.orElse(10));
    }
}
