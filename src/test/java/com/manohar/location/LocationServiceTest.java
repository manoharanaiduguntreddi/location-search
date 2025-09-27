package com.manohar.location;

import com.manohar.location.service.LocationService;
import com.manohar.location.service.LocationStore;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {

    private final LocationService service = new LocationService(new LocationStore());

    @Test
    void citiesByZip_returnsMultipleCities() {
        List<String> cities = service.searchCitiesByZip("24937");
        assertFalse(cities.isEmpty(), "Expected cities for 24937");
        assertTrue(cities.contains("Flensburg"));
        assertTrue(cities.contains("Jürgensby"));
        assertTrue(cities.contains("Sandberg"));
    }

    @Test
    void zipsByCity_caseInsensitiveMatch() {
        List<String> zips1 = service.searchZipsByCity("Flensburg");
        List<String> zips2 = service.searchZipsByCity("flensburg");
        assertEquals(zips1, zips2, "City search should be case-insensitive");
        assertTrue(zips1.contains("24937"));
    }

    @Test
    void unknownZip_returnsEmptyCities() {
        assertTrue(service.searchCitiesByZip("99999").isEmpty());
    }

    @Test
    void unknownCity_returnsEmptyZips() {
        assertTrue(service.searchZipsByCity("Atlantis").isEmpty());
    }
}
