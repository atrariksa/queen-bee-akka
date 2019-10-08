package com.queenbee.workers;

import java.io.Serializable;

public class Work implements Serializable {
    private static final long serialVersionUID = 1L;
    public final String payload;

    public Work(String payload) {
        this.payload = payload;
    }
    public String getPayload() {
        return payload;
    }
}