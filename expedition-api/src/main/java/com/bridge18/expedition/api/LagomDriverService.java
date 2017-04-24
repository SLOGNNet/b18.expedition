package com.bridge18.expedition.api;

import akka.Done;
import akka.NotUsed;
import com.bridge18.expedition.dto.v1.DriverDTO;
import com.bridge18.expedition.dto.v1.DriverSummaryDTO;
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

@ApiPath("/v1/api/expedition/drivers")
public interface LagomDriverService extends Service {

    @ApiOperation(value = "Create new driver", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "body", dataType = "com.bridge18.expedition.dto.v1.DriverDTO")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Driver created", response = DriverDTO.class)
    })
    ServiceCall<DriverDTO, DriverDTO> createNewDriver();

    @ApiOperation(value = "Get drivers", httpMethod = "GET")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "pagingState", dataType = "java.lang.String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "java.lang.Integer")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Drivers", response = PaginatedSequence.class)
    })
    ServiceCall<NotUsed, PaginatedSequence<DriverSummaryDTO>> getDriverSummaries(Optional<String> pagingState, Optional<Integer> pageSize);

    @ApiPath("/{id}")
    @ApiOperation(value = "Update driver by id", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "body", dataType = "com.bridge18.expedition.dto.v1.DriverDTO"),
            @ApiImplicitParam(paramType = "path", name = "id", dataType = "java.lang.String")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Driver updated", response = DriverDTO.class)
    })
    ServiceCall<DriverDTO, DriverDTO> updateDriverInformation(String id);

    @ApiPath("/{id}")
    @ApiOperation(value = "Delete driver by id", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", dataType = "java.lang.String")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Driver deleted", response = Done.class)
    })
    ServiceCall<NotUsed, Done> deleteDriver(String id);

    @ApiPath("/{id}")
    @ApiOperation(value = "Get driver information by id", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", dataType = "java.lang.String")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Driver", response = DriverDTO.class)
    })
    ServiceCall<NotUsed, DriverDTO> getDriverInformation(String id);

    @Override
    default Descriptor descriptor() {
        return named("expeditionDriver").withCalls(
                restCall(Method.POST, "/v1/api/expedition/drivers", this::createNewDriver),
                restCall(Method.GET, "/v1/api/expedition/drivers?pagingState&pageSize", this::getDriverSummaries),
                restCall(Method.PUT, "/v1/api/expedition/drivers/:id", this::updateDriverInformation),
                restCall(Method.DELETE, "/v1/api/expedition/drivers/:id", this::deleteDriver),
                restCall(Method.GET, "/v1/api/expedition/drivers/:id", this::getDriverInformation)
        ).withAutoAcl(true)
                .withServiceAcls(
                        ServiceAcl.methodAndPath(Method.OPTIONS, "\\*")
                );
    }
}
