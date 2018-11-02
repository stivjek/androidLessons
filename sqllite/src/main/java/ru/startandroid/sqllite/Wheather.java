package ru.startandroid.sqllite;

public class Wheather {
    Location location;
    Current current;

    public Wheather() {
    }

    public Wheather(Location location, Current current) {
        this.location = location;
        this.current = current;
    }
}
