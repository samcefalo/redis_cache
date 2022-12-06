package me.samcefalo.sqlcache.entities;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class Car implements Serializable {

    private UUID id;
    private String name;
    private String age;
    private Machine machine;

}
