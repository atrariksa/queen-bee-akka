package com.queenbee.workers;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.routing.*;

import java.util.ArrayList;
import java.util.List;

public class ActorConsumerTest extends AbstractActor {

    /*
    akka.actor.deployment {
    /actor_consumer/router3 {
            router = round-robin-group
            routees.paths = ["/user/actor_consumer/w1", "/user/actor_consumer/w2", "/user/actor_consumer/w3"]
        }
    }
     */
//    private ActorRef router;
//    @Override
//    public void preStart() {
//        System.out.println("ActorConsumerTest Prestart");
//        for (int i = 0; i < 3; i++) {
//            ActorRef actorConsumerWorker = getContext().actorOf(Props.create(ActorConsumerWorker.class),"w"+(i+1));
//            getContext().watch(actorConsumerWorker);
//        }
//        router = getContext().actorOf(FromConfig.getInstance().props(),"router3");
//        System.out.println(router);
//    }
//    @Override
//    public Receive createReceive() {
//        return receiveBuilder().match(Work.class, message->{
//            System.out.println("ActorConsumer : " + message.getPayload());
//            router.forward(message, getContext());
//        }).build();
//    }




    private ActorRef router;
    @Override
    public void preStart() {
        System.out.println("ActorConsumer Prestart");
        router = getContext().actorOf(new RoundRobinPool(3).props(Props.create(ActorConsumerWorker.class)), "router");
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Work.class, message->{
            System.out.println("ActorConsumer : " + message.getPayload());
            router.forward(message, getContext());
        }).build();
    }





//    private Router router;
//    @Override
//    public void preStart() {
//        List<Routee> routees = new ArrayList<>();
//        System.out.println("ActorConsumer Prestart");
//        for (int i = 0; i < 3; i++) {
//            ActorRef actorConsumerWorker = getContext().actorOf(Props.create(ActorConsumerWorker.class),"worker_"+(i+1));
//            getContext().watch(actorConsumerWorker);
//            routees.add(new ActorRefRoutee(actorConsumerWorker));
//        }
//        router = new Router(new RoundRobinRoutingLogic(), routees);
//    }
//    @Override
//    public Receive createReceive() {
//        return receiveBuilder().match(Work.class, message->{
//            System.out.println("ActorConsumer : " + message.getPayload());
//            router.route(message, getSelf());
//        }).build();
//    }

}
