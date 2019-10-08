package com.queenbee.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.routing.Router;
import com.queenbee.controllers.Controller;

public class ControllerMaster extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(object->{
            if (object instanceof Controller) {
                Controller controller = (Controller) object;
                ActorRef actorRef = getContext().actorOf(controller.props("HomeTest"), "HomeTest");
                getContext().watch(actorRef);
            }
            else {
                System.out.println("Ignoring object : " + object.getClass().getName());
            }
        }).build();
    }
}
