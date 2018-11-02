package ru.startandroid.sqllite;

public class Location {
    String name;
    String region;
    String country;
    Double lat;
    Double lon;
    String tz_id;
    String localtime_epoch;
    String localtime;

    public Location(Object location) {

    }

    public Location(String name, String region, String country, Double lat, Double lon, String tz_id, String localtime_epoch, String localtime) {
        this.name = name;
        this.region = region;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        this.tz_id = tz_id;
        this.localtime_epoch = localtime_epoch;
        this.localtime = localtime;
    }
}
