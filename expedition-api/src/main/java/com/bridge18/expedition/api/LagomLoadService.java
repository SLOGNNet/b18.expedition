package com.bridge18.expedition.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

import akka.Done;
import akka.NotUsed;
import com.bridge18.expedition.dto.LoadDTO;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

public interface LagomLoadService extends Service {
    ServiceCall<NotUsed, LoadDTO> createNewLoad();
    ServiceCall<LoadDTO, LoadDTO> addLoadDetails(String id);

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("expedition").withCalls(
                restCall(Method.POST, "/api/expedition/loads",  this::createNewLoad),
                restCall(Method.POST, "/api/expedition/loads/:id", this::addLoadDetails)
        ).withAutoAcl(true);
        // @formatter:on
    }
}