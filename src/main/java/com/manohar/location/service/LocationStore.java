package com.manohar.location.service;

import java.util.*;

/**
 * @author Manohar
 * Project Name: location-search
 */
public class LocationStore {
    private final Map<String, Set<String>> data = new HashMap<>();

    public LocationStore() {
        // Sample in memory data
        add("24937", "Flensburg");
        add("24937", "Jürgensby");
        add("24937", "Sandberg");

        add("10115", "Berlin");
        add("10115", "Mitte");

        add("20095", "Hamburg");
        add("20095", "Hamburg-Altstadt");
    }

    public void add(String zip, String city) {
        if (zip == null || city == null) return;
        data.computeIfAbsent(zip, z -> new LinkedHashSet<>()).add(city);
    }

    public List<String> getCitiesByZip(String zip) {
        return new ArrayList<>(data.getOrDefault(zip, Collections.emptySet()));
    }

    public List<String> getZipsByCity(String city) {
        if (city == null) return List.of();
        String needle = city.trim().toLowerCase(Locale.ROOT);
        if (needle.isEmpty()) return List.of();
        List<String> zips = new ArrayList<>();
        for (Map.Entry<String, Set<String>> e : data.entrySet()) {
            for (String c : e.getValue()) {
                if (c.equalsIgnoreCase(needle)) {
                    zips.add(e.getKey());
                    break;
                }
            }
        }
        return zips;
    }
}
