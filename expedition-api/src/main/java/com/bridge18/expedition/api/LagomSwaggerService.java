package com.bridge18.expedition.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;
import io.swagger.models.Swagger;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

public interface LagomSwaggerService extends Service {
    ServiceCall<NotUsed, Swagger> getSwagger();

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("expeditionSwagger").withCalls(
                restCall(Method.GET, "/v1/api/expedition/swagger.json",  this::getSwagger)
        ).withAutoAcl(true);
        // @formatter:on
    }
}