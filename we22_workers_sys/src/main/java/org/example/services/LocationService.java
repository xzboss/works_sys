package org.example.services;

import org.example.DAO.EmployeeDAO;
import org.example.DAO.LocationDAO;
import org.example.model.Location;
import org.example.model.LocationRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Component("LocationService")
public class LocationService {
    private LocationDAO locationDAO;
    @Autowired
    public LocationService(@Qualifier("locationMySQL") LocationDAO locationDAO){
        this.locationDAO = locationDAO;
    }
    public Optional<Location> selectLocationById(String locationId) {
        return locationDAO.selectLocationById(locationId);
    }
    public Optional<Location> selectLocationByName(String locationName) {
        return locationDAO.selectLocationByName(locationName);
    }
    public boolean insertLocation(Location location) {
        return locationDAO.insertLocation(location);
    }
    public boolean updateLocation(String locationId,Location location) {
        return locationDAO.updateLocation(locationId,location);
    }
    public boolean deleteLocation(String locationId) {
        return locationDAO.deleteLocation(locationId);
    }
}
