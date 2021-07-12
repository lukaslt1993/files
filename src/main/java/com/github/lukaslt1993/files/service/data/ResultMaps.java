package com.github.lukaslt1993.files.service.data;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class ResultMaps {

    private Map<String, Integer> agMap = new TreeMap<>();
    private Map<String, Integer> hnMap = new TreeMap<>();
    private Map<String, Integer> ouMap = new TreeMap<>();
    private Map<String, Integer> vzMap = new TreeMap<>();

}
