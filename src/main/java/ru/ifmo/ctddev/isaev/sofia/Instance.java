package ru.ifmo.ctddev.isaev.sofia;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author iisaev
 */
public class Instance {
    private final Integer clazz;

    private final Map<Integer, Integer> features;

    @Override
    public String toString() {
        return Stream.concat(
                Stream.of(
                        String.valueOf(clazz)),
                features.keySet().stream()
                        .sorted()
                        .map(k -> String.format("%d:%d", k, features.get(k)))
        ).collect(Collectors.joining(" "))+" ";
    }

    public Instance(Integer clazz, Map<Integer, Integer> features) {
        this.clazz = clazz;
        this.features = Collections.unmodifiableMap(features);
    }

    public Integer getClazz() {
        return clazz;
    }

    public Map<Integer, Integer> getFeatures() {
        return features;
    }
}
