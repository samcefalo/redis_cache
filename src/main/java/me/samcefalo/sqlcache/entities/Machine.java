package me.samcefalo.sqlcache.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Machine {

    private String machineName;
    private String machineType;

}
