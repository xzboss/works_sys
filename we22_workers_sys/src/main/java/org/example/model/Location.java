package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private String locationId;
    private String locationName;
    private double latitude;
    private double longitude;
}
