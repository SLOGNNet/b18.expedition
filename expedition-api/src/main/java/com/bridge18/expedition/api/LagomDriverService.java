package com.bridge18.expedition.api;

import akka.NotUsed;
import com.bridge18.expedition.dto.v1.DriverDTO;
import com.bridge18.expedition.dto.v1.DriverSummary;
import com.bridge18.expedition.dto.v1.PaginatedSequence;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import java.util.Optional;

import static com.lightbend.lagom.javadsl.api.Service.*;
public interface LagomDriverService extends Service {
    ServiceCall<DriverDTO, DriverDTO> createNewDriver();
    ServiceCall<NotUsed, PaginatedSequence<DriverSummary>> getDriverSummaries(Optional<Integer> pageNo, Optional<Integer> pageSize);
    ServiceCall<DriverDTO, DriverDTO> changeDriverInformation(String id);
    ServiceCall<NotUsed, DriverDTO> getDriverInformation(String id);

    @Override
    default Descriptor descriptor(){
        return named("expeditionDriver").withCalls(
                restCall(Method.POST, "/v1/api/expedition/drivers", this::createNewDriver),
                restCall(Method.GET, "/v1/api/expedition/drivers?pageNo&pageSize", this::getDriverSummaries),
                restCall(Method.PUT, "/v1/api/expedition/drivers/:id", this::changeDriverInformation),
                restCall(Method.GET, "/v1/api/expedition/drivers/:id", this::getDriverInformation)
        ).withAutoAcl(true);
    }
}
