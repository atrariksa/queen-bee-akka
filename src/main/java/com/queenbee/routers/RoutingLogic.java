package com.queenbee.routers;

import akka.actor.ActorSystem;
import akka.actor.Scheduler;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.RandomRoutingLogic;
import akka.routing.SmallestMailboxRoutingLogic;
import akka.routing.BroadcastRoutingLogic;
import akka.routing.ScatterGatherFirstCompletedRoutingLogic;
import akka.routing.TailChoppingRoutingLogic;
import akka.routing.ConsistentHashingRoutingLogic;
import scala.PartialFunction;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;

public class RoutingLogic {
    private static final String roundRobin = "round-robin";
    private static final String random = "random";
    private static final String smallestMailbox = "smallest-mailbox";
    private static final String broadcast = "broadcast";
    private static final String scatterGather = "scatter-gather";
    private static final String tailChopping = "tail-chopping";
    private static final String consistentHashing = "consistent-hashing";

    public static akka.routing.RoutingLogic getRoutingLogic(String routingLogicName) throws Exception {
        switch (routingLogicName) {
            case RoutingLogic.roundRobin :
                return new RoundRobinRoutingLogic();
            case RoutingLogic.random :
                return new RandomRoutingLogic();
            case RoutingLogic.smallestMailbox :
                return new SmallestMailboxRoutingLogic();
            case RoutingLogic.broadcast :
                return new BroadcastRoutingLogic();
            default:
                throw new Exception("Logic not found or config not complete.");
        }
    }
    public static akka.routing.RoutingLogic getRoutingLogic(String routingLogicName, FiniteDuration finiteDuration) throws Exception {
        if (routingLogicName.equals(RoutingLogic.scatterGather)) {
            return new ScatterGatherFirstCompletedRoutingLogic(finiteDuration);
        }
        else {
            throw new Exception("Logic not found or config not complete.");
        }
    }
    public  static akka.routing.RoutingLogic getRoutingLogic(String routingLogicName, FiniteDuration within, FiniteDuration interval, Scheduler scheduler, ExecutionContext context) throws Exception {
        if (routingLogicName.equals(RoutingLogic.tailChopping)) {
            return new TailChoppingRoutingLogic(scheduler, within, interval, context);
        }
        else {
            throw new Exception("Logic not found or config not complete.");
        }
    }
    public  static akka.routing.RoutingLogic getRoutingLogic(String routingLogicName, ActorSystem actorSystem, int virtualNodeFactor, PartialFunction<Object, Object> hashMapping) throws Exception {
        if (routingLogicName.equals(RoutingLogic.consistentHashing)) {
            return new ConsistentHashingRoutingLogic(actorSystem, virtualNodeFactor, hashMapping);
        }
        else {
            throw new Exception("Logic not found or config not complete.");
        }
    }
}
