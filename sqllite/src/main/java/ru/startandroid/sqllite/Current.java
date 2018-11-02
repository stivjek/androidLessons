package ru.startandroid.sqllite;

public class Current {
    String last_updated_epoch;
    String last_updated;
    Double temp_c;
    Double temp_f;
    Boolean is_day;
    Condition condition;
    Double wind_mph;
    Double wind_kph;
    int wind_degree;
    String wind_dir;
    Double pressure_mb;
    Double pressure_in;
    Double precip_mm;
    Double precip_in;
    int humidity;
    int cloud;
    Double feelslike_c;
    Double feelslike_f;
    Double vis_km;
    Double vis_miles;

    public Current(){

    }

    public Current(String last_updated_epoch, String last_updated, Double temp_c, Double temp_f,
                   Boolean is_day, Condition condition, Double wind_mph, Double wind_kph, int wind_degree,
                   String wind_dir, Double pressure_mb, Double pressure_in, Double precip_mm, Double precip_in,
                   int humidity, int cloud, Double feelslike_c, Double feelslike_f, Double vis_km, Double vis_miles) {
        this.last_updated_epoch = last_updated_epoch;
        this.last_updated = last_updated;
        this.temp_c = temp_c;
        this.temp_f = temp_f;
        this.is_day = is_day;
        this.condition = condition;
        this.wind_mph = wind_mph;
        this.wind_kph = wind_kph;
        this.wind_degree = wind_degree;
        this.wind_dir = wind_dir;
        this.pressure_mb = pressure_mb;
        this.pressure_in = pressure_in;
        this.precip_mm = precip_mm;
        this.precip_in = precip_in;
        this.humidity = humidity;
        this.cloud = cloud;
        this.feelslike_c = feelslike_c;
        this.feelslike_f = feelslike_f;
        this.vis_km = vis_km;
        this.vis_miles = vis_miles;
    }
}
