package me.nekoh.hub.server;

import lombok.Getter;

@Getter
public enum Status {


    ONLINE("&a"),
    OFFLINE("&c"),
    WHITELISTED("&f");

    private String color;

    Status(String color) {
        this.color = color;
    }
}
