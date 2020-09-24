package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import org.springframework.lang.NonNull;

public class Person {

    private final UUID id;
    @NonNull
    private final String name;

    public Person(@JsonProperty("id") UUID id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }
}
