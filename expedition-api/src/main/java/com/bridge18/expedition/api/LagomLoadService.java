package com.bridge18.expedition.api;

import akka.NotUsed;
import com.bridge18.expedition.dto.v1.LoadDTO;
import com.bridge18.swagger.annotations.ApiPath;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;
import io.swagger.annotations.*;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

@ApiPath("/v1/api/expedition/loads")
@Api(consumes = "application/json", produces = "application/json")
public interface LagomLoadService extends Service {
    @ApiOperation(value = "Create load", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Load created", response = LoadDTO.class)
    })
    ServiceCall<NotUsed, LoadDTO> createNewLoad();

    @ApiPath("/{id}")
    @ApiOperation(value = "Add load details", httpMethod = "PUT")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", name = "body", dataType = "com.bridge18.expedition.dto.v1.LoadDTO"),
        @ApiImplicitParam(paramType = "path", name = "id", dataType = "java.lang.String")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Load updated", response = LoadDTO.class)
    })
    ServiceCall<LoadDTO, LoadDTO> addLoadDetails(String id);

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("expeditionLoad").withCalls(
                restCall(Method.POST, "/v1/api/expedition/loads",  this::createNewLoad),
                restCall(Method.PUT, "/v1/api/expedition/loads/:id", this::addLoadDetails)
        ).withAutoAcl(true);
        // @formatter:on
    }
}