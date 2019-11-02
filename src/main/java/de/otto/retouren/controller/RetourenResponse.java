package de.otto.retouren.controller;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RetourenResponse {
    private String message;
    Status status;

    public enum Status {
        SAVED,
        IGNORED,
        ERROR
    }

}
