package de.otto.retouren.controller;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RetoureRequest {
    private String CustomerID;
    private String OrderId;
}
