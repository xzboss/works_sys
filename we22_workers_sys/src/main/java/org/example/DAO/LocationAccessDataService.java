package org.example.DAO;

import org.example.model.Employee;
import org.example.model.EmployeeRowMapper;
import org.example.model.Location;
import org.example.model.LocationRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("locationMySQL")
public class LocationAccessDataService implements LocationDAO{
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public LocationAccessDataService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Optional<Location> selectLocationById(String locationId) {
        final String sql = "select * from location where locationId=?";
        List<Location> locationList = jdbcTemplate.query(sql, new LocationRowMapper(), locationId);
        return locationList.stream().findFirst();
    }

    @Override
    public Optional<Location> selectLocationByName(String locationName) {
        final String sql = "select * from location where locationName=?";
        List<Location> locationList = jdbcTemplate.query(sql, new LocationRowMapper(), locationName);
        return locationList.stream().findFirst();
    }

    @Override
    public boolean insertLocation(Location location) {
        final String sql = "insert into location value(?,?,?,?)";
        int result = jdbcTemplate.update(
                sql,
                location.getLocationId(),
                location.getLocationName(),
                location.getLatitude(),
                location.getLongitude()
        );
        return result>0?true:false;
    }

    @Override
    public boolean updateLocation(String locationId,Location location) {
        final String sql = "update location set locationId=?,locationName=?,latitude=?,longitude=? where locationId=?";
        int result = jdbcTemplate.update(
                sql,
                location.getLocationId(),
                location.getLocationName(),
                location.getLatitude(),
                location.getLongitude(),
                locationId
        );
        return result>0?true:false;
    }

    @Override
    public boolean deleteLocation(String locationId) {
        final String sql = "delete from location where locationId=?";
        jdbcTemplate.update(sql, locationId);
        return true;
    }
}
