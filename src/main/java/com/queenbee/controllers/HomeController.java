package com.queenbee.controllers;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.queenbee.workers.Work;

public class HomeController extends AbstractActor implements Controller {

    private String prefixName;
    private Object[] objects;
    public HomeController(String prefixName, Object... objects) {
        this.prefixName = prefixName;
        this.objects = objects;
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Work.class, message -> {
            System.out.println("HomeController : " + message.getPayload());
        }).build();
    }

    @Override
    public Props props(String prefixName, Object... objects) {
        return Props.create(HomeController.class, prefixName, objects);
    }
}
