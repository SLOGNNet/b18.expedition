package com.bridge18.expedition.api;

import akka.NotUsed;
import com.bridge18.expedition.dto.v1.LoadDTO;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import static com.lightbend.lagom.javadsl.api.Service.*;

public interface LagomLoadService extends Service {
    ServiceCall<NotUsed, LoadDTO> createNewLoad();
    ServiceCall<LoadDTO, LoadDTO> addLoadDetails(String id);

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("expedition").withCalls(
                restCall(Method.POST, "/v1/api/expedition/loads",  this::createNewLoad),
                restCall(Method.PUT, "/v1/api/expedition/loads/:id", this::addLoadDetails)
        ).withAutoAcl(true);
        // @formatter:on
    }
}