package com.bridge18.expedition.api;

import akka.Done;
import akka.NotUsed;
import com.bridge18.expedition.dto.v1.DisableDriverDTO;
import com.bridge18.expedition.dto.v1.DriverDTO;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

public interface LagomDriverService extends Service {
    ServiceCall<DriverDTO, DriverDTO> createNewDriver();
    ServiceCall<DriverDTO, DriverDTO> changeDriverInformation(String id);
    ServiceCall<DisableDriverDTO, Done> disableDriver(String id);
    ServiceCall<NotUsed, DriverDTO> getDriverInformation(String id);

    @Override
    default Descriptor descriptor(){
        return named("expedition1").withCalls(
                restCall(Method.POST, "/v1/api/expedition/drivers", this::createNewDriver),
                restCall(Method.PUT, "/v1/api/expedition/drivers/:id", this::changeDriverInformation),
                restCall(Method.PUT, "/v1/api/expedition/drivers/disable/:id", this::disableDriver),
                restCall(Method.GET, "/v1/api/expedition/drivers/:id", this::getDriverInformation)
        ).withAutoAcl(true);
    }
}
