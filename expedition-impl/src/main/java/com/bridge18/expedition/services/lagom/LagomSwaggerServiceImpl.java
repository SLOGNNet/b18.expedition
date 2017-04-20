package com.bridge18.expedition.services.lagom;

import akka.NotUsed;
import com.bridge18.expedition.api.LagomSwaggerService;
import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import io.swagger.models.Swagger;

import java.util.concurrent.CompletableFuture;

public class LagomSwaggerServiceImpl implements LagomSwaggerService {
    private final Swagger swagger;

    @Inject
    public LagomSwaggerServiceImpl(Swagger swagger) {
        this.swagger = swagger;
    }

    @Override
    public ServiceCall<NotUsed, Swagger> getSwagger() {
        return request -> CompletableFuture.completedFuture(swagger);
    }
}