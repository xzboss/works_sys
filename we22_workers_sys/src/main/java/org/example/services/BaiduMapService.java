package org.example.services;

import org.example.model.Place;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class BaiduMapService {
    public Place find(String locationName,String region){
        Place place = new Place();
        place.setName(locationName);
        place.setRegion(region);
        String ak = "IYpXR3yrxc47CZFAsKPV2ZX4TWhs4Z6a";
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
        HttpResponse response = null;
        String endPoint = "https://api.map.baidu.com/place/v2/search?";
        URI uri = URI.create(endPoint+"query="+locationName+"&region="+region+"&output=json"+"&ak="+ak);
        System.out.println(uri);
        try{
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
            response = client.send(request,HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            e.printStackTrace();
        }
        //System.out.println("Status code:" + response.statusCode());
        //System.out.println("Body" + response.body());
        JSONObject placeJson = null;
        try {
            placeJson = new JSONObject(response.body().toString());
            //System.out.println(placeJson);
        }catch (Exception e){
            System.out.println("Parsing to Json error");
        }
        try {
            String lng = placeJson.getJSONArray("results")
                    .getJSONObject(0).getJSONObject("location").getString("lng");
            String lat = placeJson.getJSONArray("results")
                    .getJSONObject(0).getJSONObject("location").getString("lat");
            place.setLat(lat);
            place.setLng(lng);
        }catch (Exception e){
            System.out.println("Getting Value from Json error");
        }
        return place;
    }
    public int getDistance(String latitude,String longitude){
        //29.566,103.738
        //https://api.map.baidu.com/direction/v2/driving?origin=29.566,103.738&destination=39.936404,116.452562&ak=您的AK
        int res = 0;
        String ak = "IYpXR3yrxc47CZFAsKPV2ZX4TWhs4Z6a";
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
        HttpResponse response = null;
        String endPoint = "https://api.map.baidu.com/direction/v2/driving?origin=29.566,103.738&";
        URI uri = URI.create(endPoint+"destination="+latitude+","+longitude+"&ak="+ak);
        System.out.println(uri);
        try{
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
            response = client.send(request,HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject disJson = null;
        try {
            disJson = new JSONObject(response.body().toString());
        }catch (Exception e){
            System.out.println("Parsing to Json error");
        }
        try {
            res = disJson.getJSONObject("result")
                    .getJSONArray("routes")
                    .getJSONObject(0)
                    .getInt("distance");
        }catch (Exception e){
            System.out.println("Getting Value from Json error");
        }
        return res;
    }
}
