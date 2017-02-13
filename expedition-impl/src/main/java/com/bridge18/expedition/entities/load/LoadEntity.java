package com.bridge18.expedition.entities.load;

import java.util.Optional;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

public class LoadEntity extends PersistentEntity<LoadCommand, LoadEvent, LoadState> {

    @Override
    public Behavior initialBehavior(Optional<LoadState> snapshotState) {

        BehaviorBuilder b = newBehaviorBuilder(
                snapshotState.orElse(LoadState.builder().id(entityId()).build()));

        b.setCommandHandler(LoadCommand.CreateLoad.class, (cmd, ctx) ->
            ctx.thenPersist(new LoadEvent.LoadCreated(),
                        evt -> {
                            ctx.reply(state().withId(entityId()));
                        }
                    )
        );

        b.setCommandHandler(LoadCommand.AddLoadDetails.class, (cmd, ctx) ->
                ctx.thenPersist(new LoadEvent.LoadDetailsAdded(cmd.customerId, cmd.customerAddressId,
                                cmd.carrierLoadNumber, cmd.brokerLoadNumber,
                                cmd.loadType, cmd.freightType),

                        evt -> {
                            ctx.reply(state());
                        })
        );

        b.setEventHandler(LoadEvent.LoadCreated.class,
                evt -> LoadState.of(entityId(), Optional.empty(), Optional.empty(),
                        Optional.empty(), Optional.empty(),
                        Optional.empty(), Optional.empty()));

        b.setEventHandler(LoadEvent.LoadDetailsAdded.class,
                (LoadEvent.LoadDetailsAdded evt) ->
                        state().withId(entityId()).withCustomerId(evt.customerId)
                                .withCustomerAddressId(evt.customerAddressId)
                                .withCarrierLoadNumber(evt.carrierLoadNumber)
                                .withBrokerLoadNumber(evt.brokerLoadNumber)
                                .withLoadType(evt.loadType)
                                .withFreightType(evt.freightType)
                );

        return b.build();
    }
}
