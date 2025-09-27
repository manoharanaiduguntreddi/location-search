package com.manohar.location.service;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author Manohar
 * Project Name: location-search
 */
@Service
public class LocationService {

    private final LocationStore store;

    public LocationService(LocationStore store) {
        this.store = store;
    }

    public List<String> searchCitiesByZip(String zip) {
        return store.getCitiesByZip(zip);
    }

    public List<String> searchZipsByCity(String city) {
        return store.getZipsByCity(city);
    }
}
