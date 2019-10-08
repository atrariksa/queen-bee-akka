package com.queenbee.workers;

import akka.actor.AbstractActor;

public class ActorConsumerWorker extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Work.class, message->{
            System.out.println(getSelf().path().name() + " : " + message.getPayload());
        }).build();
    }
}
