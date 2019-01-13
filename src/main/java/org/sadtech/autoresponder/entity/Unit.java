package org.sadtech.autoresponder.entity;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Unit {

    private Integer idUnit;
    private List<Unit> nextUnits;
    private String answer;
    private Integer priority;
    private Set<String> keyWords;
    private Integer matchThreshold;

}
