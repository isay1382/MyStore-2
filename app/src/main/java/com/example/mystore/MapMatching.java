package com.example.mystore;

import java.util.List;


class Location{
    public double latitude;
    public double longitude;
}


class SnappedPoints{
    public Location location;
    public int originalIndex;
}


public class MapMatching {
    public List<SnappedPoints> snappedPoints;
}
