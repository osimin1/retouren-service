package de.otto.retouren.controller;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
class RetourenResponse {
    private String message;
    Status status;

    public enum Status {
        SAVED,
        DUPLICATE,
        ERROR
    }

}
