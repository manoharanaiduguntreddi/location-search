package com.manohar.location.controller;

import java.util.List;
import java.util.Map;

import com.manohar.location.service.LocationService;
import com.manohar.location.service.NotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Manohar
 * Project Name: location-search
 */
@Controller
public class LocationController {

    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public String searchPage(HttpSession session) {
        return "search";
    }

    @GetMapping("/api/search/by-zip")
    @ResponseBody
    public ResponseEntity<?> searchByZip(@RequestParam("zip") String zip) {
        List<String> cities = service.searchCitiesByZip(zip);
        if (cities.isEmpty()) {
            String message = String.format("The entered ZIP code '%s' is not existed in in-memory list.", zip);

            throw new NotFoundException(message);
        }
        return ResponseEntity.ok(Map.of(
                "zip", zip,
                "cities", cities,
                "count", cities.size()
        ));
    }

    @GetMapping("/api/search/by-city")
    @ResponseBody
    public ResponseEntity<?> searchByCity(@RequestParam("city") String city) {
        List<String> zips = service.searchZipsByCity(city);
        if (zips.isEmpty()) {
            String message = String.format("No zip code associated with the specified city name '%s'", city);
            throw new NotFoundException(message);
        }
        return ResponseEntity.ok(Map.of(
                "city", city,
                "zips", zips,
                "count", zips.size()
        ));
    }
}

