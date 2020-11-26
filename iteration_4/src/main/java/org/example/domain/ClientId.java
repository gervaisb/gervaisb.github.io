package org.example.domain;

import java.util.UUID;

public class ClientId {

    public static ClientId next() {
        return new ClientId(UUID.randomUUID());
    }


    private final UUID uuid;

    private ClientId(UUID uuid) {
        this.uuid = uuid;
    }
}
