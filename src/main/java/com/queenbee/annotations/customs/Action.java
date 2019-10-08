package com.queenbee.annotations.customs;

import akka.http.javadsl.server.AllDirectives;
import com.queenbee.QueenBee;
import com.queenbee.errorhandlers.ErrorHandler;

public class Action<T> extends ErrorHandler {
    public Action(QueenBee app) {
        super(app);
    }
}
