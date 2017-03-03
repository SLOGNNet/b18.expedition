package com.bridge18.expedition.entities.contact;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

public class ContactEntity extends PersistentEntity<ContactCommand, ContactEvent, ContactState> {

    @Override
    public Behavior initialBehavior(Optional<ContactState> snapshotState) {
        BehaviorBuilder b = newBehaviorBuilder(ContactState.builder().id(entityId()).build());

        b.setCommandHandler(ContactCommand.ContactCreated.class, (cmd, ctx) ->
                ctx.thenPersist(new ContactEvent.ContactCreated(),
                        evt -> {
                            ctx.reply(state().withId(entityId()));
                        }
                )
        );

        b.setCommandHandler(ContactCommand.AddContactDetails.class, (cmd, ctx) ->
                ctx.thenPersist(new ContactEvent.ContactDetailsAdded(cmd.firstName, cmd.middleName, cmd.lastName,
                                cmd.getPosition, cmd.getAddress),
                        evt -> {
                            ctx.reply(state());
                        })
        );


        b.setEventHandler(ContactEvent.ContactCreated.class,
                evt -> ContactState.of(entityId(), Optional.empty(), Optional.empty(), Optional.empty(), Optional
                        .empty(), Optional.empty())
        );

        b.setEventHandler(ContactEvent.ContactDetailsAdded.class,
                evt ->
                        ContactState.builder()
                                .from(state())
                                .firstName(evt.firstName)
                                .middleName(evt.middleName)
                                .lastName(evt.lastName)
                                .address(evt.address)
                                .position(evt.position)
                                .build()
        );

        return b.build();
    }


}
