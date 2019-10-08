package com.queenbee.controllers;

import akka.actor.AbstractActor;
import akka.actor.Props;

import java.util.Map;

public interface Controller {
    Props props(String prefixName, Object... objects);
}
