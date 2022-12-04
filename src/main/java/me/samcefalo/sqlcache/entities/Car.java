package me.samcefalo.sqlcache.entities;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Car {

    private UUID id;
    private String name;
    private String age;
    private Machine machine;

}
