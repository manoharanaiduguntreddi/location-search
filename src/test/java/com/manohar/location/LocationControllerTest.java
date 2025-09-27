package com.manohar.location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void searchByZip_returnsListOfCities() throws Exception {
        mockMvc.perform(get("/api/search/by-zip").param("zip", "24937"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.zip", is("24937")))
                .andExpect(jsonPath("$.cities", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.count", greaterThan(0)));
    }

    @Test
    void searchByCity_returnsListOfZips() throws Exception {
        mockMvc.perform(get("/api/search/by-city").param("city", "Flensburg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.city", is("Flensburg")))
                .andExpect(jsonPath("$.zips", hasItem("24937")))
                .andExpect(jsonPath("$.count", greaterThan(0)));
    }

    @Test
    void searchByZip_unknown_returns404() throws Exception {
        mockMvc.perform(get("/api/search/by-zip").param("zip", "99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.message", is("The entered ZIP code '99999' is not existed in in-memory list.")));
    }

    @Test
    void searchByCity_unknown_returns404() throws Exception {
        mockMvc.perform(get("/api/search/by-city").param("city", "Atlantis"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.message", is("No zip code associated with the specified city name 'Atlantis'")));
    }
}

