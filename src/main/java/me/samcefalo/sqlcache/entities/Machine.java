package me.samcefalo.sqlcache.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Machine implements Serializable {

    private String machineName;
    private String machineType;

}
