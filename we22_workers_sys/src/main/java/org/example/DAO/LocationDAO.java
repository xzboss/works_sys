package org.example.DAO;

import org.example.model.Location;

import java.util.Optional;

public interface LocationDAO {
    Optional<Location> selectLocationById(String locationId);
    Optional<Location> selectLocationByName(String locationName);
    boolean insertLocation(Location location);
    boolean updateLocation(String locationId,Location location);
    boolean deleteLocation(String locationId);
}
