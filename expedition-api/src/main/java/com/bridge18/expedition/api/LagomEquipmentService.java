package com.bridge18.expedition.api;


import akka.Done;
import akka.NotUsed;
import com.bridge18.expedition.dto.v1.EquipmentDTO;
import com.bridge18.expedition.dto.v1.EquipmentSummary;
import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.bridge18.swagger.annotations.ApiPath;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceAcl;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;
import io.swagger.annotations.*;

import java.util.Optional;

import static com.lightbend.lagom.javadsl.api.Service.*;

@ApiPath("/v1/api/expedition/equipment")
@Api(consumes = "application/json", produces = "application/json")
public interface LagomEquipmentService extends Service {

    @ApiOperation(value = "Create new equipment", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "body", dataType = "com.bridge18.expedition.dto.v1.EquipmentDTO")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Equipment created", response = EquipmentDTO.class)
    })
    ServiceCall<EquipmentDTO, EquipmentDTO> createNewEquipment();

    @ApiPath("/{id}")
    @ApiOperation(value = "Update equipment by id", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "body", dataType = "com.bridge18.expedition.dto.v1.EquipmentDTO"),
            @ApiImplicitParam(paramType = "path", name = "id", dataType = "java.lang.String")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Equipment updated", response = EquipmentDTO.class)
    })
    ServiceCall<EquipmentDTO, EquipmentDTO> updateEquipmentInformation(String id);

    @ApiPath("/{id}")
    @ApiOperation(value = "Delete equipment by id", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", dataType = "java.lang.String")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Equipment deleted", response = NotUsed.class)
    })
    ServiceCall<NotUsed, Done> deleteEquipment(String id);

    @ApiPath("/{id}")
    @ApiOperation(value = "Get equipment by id", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", dataType = "java.lang.String")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Equipment by id", response = EquipmentDTO.class)
    })
    ServiceCall<NotUsed, EquipmentDTO> getEquipment(String id);


    @ApiOperation(value = "Get equipments on the page", httpMethod = "GET")
    @ApiImplicitParams(value = {
            //Todo or should be Optional
            @ApiImplicitParam(paramType = "query", name = "pagingState", dataType = "java.lang.String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "java.lang.Integer")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Equipments", response = PaginatedSequence.class)
    })
    ServiceCall<NotUsed, PaginatedSequence<EquipmentSummary>> getEquipmentSummaries(Optional<String> pagingState, Optional<Integer> pageSize);

    @Override
    default Descriptor descriptor() {
        return named("expeditionEquipment").withCalls(
                restCall(Method.POST, "/v1/api/expedition/equipment", this::createNewEquipment),
                restCall(Method.PUT, "/v1/api/expedition/equipment/:id", this::updateEquipmentInformation),
                restCall(Method.DELETE, "/v1/api/expedition/equipment/:id", this::deleteEquipment),
                restCall(Method.GET, "/v1/api/expedition/equipment/:id", this::getEquipment),
                restCall(Method.GET, "/v1/api/expedition/equipment?pagingState&pageSize", this::getEquipmentSummaries)
        ).withAutoAcl(true)
                .withServiceAcls(
                        ServiceAcl.methodAndPath(Method.OPTIONS, "\\*")
                );

    }
}
