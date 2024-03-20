package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shape {
    List<Coordinate> coordinates;

    public Shape() {
        this.coordinates = new ArrayList<>();
    }

    public boolean isValid() {
        if (coordinates.size() < 3)
            return false;
        for (int i = 0; i < coordinates.size(); i++) {
            Coordinate p1 = coordinates.get(i);
            Coordinate p2 = coordinates.get((i + 1) % coordinates.size());
            Coordinate p3 = coordinates.get((i + 2) % coordinates.size());
            if (!isConvex(p1, p2, p3))
                return false;
        }
        return true;
    }

    public boolean isConvex(Coordinate p1, Coordinate p2, Coordinate p3) {
        double crossProduct = (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
        return crossProduct >= 0;
    }

    public boolean isPointInPolygon(Coordinate point) {
        int n = coordinates.size();
        boolean inside = false;
        if (coordinates.contains(point)) {
            return true;
        }


        for (int i = 0, j = n - 1; i < n; j = i++) {
            if (onBoundary(coordinates.get(i), coordinates.get(j), point)) {
                return true;
            }
            if ((coordinates.get(i).y >= point.y) != (coordinates.get(j).y >= point.y) &&
                    (point.x < (coordinates.get(j).x - coordinates.get(i).x) * (point.y - coordinates.get(i).y) / (coordinates.get(j).y - coordinates.get(i).y) + coordinates.get(i).x)) {
                inside = !inside;
            }
        }
        return inside;
    }

    private static boolean onBoundary(Coordinate p1, Coordinate p2, Coordinate point) {
        double crossProduct = (point.y - p1.y) * (p2.x - p1.x) - (point.x - p1.x) * (p2.y - p1.y);
        if (Math.abs(crossProduct) > 0.00001) {
            return false;
        }
        double dotProduct = (point.x - p1.x) * (p2.x - p1.x) + (point.y - p1.y) * (p2.y - p1.y);
        if (dotProduct < 0) {
            return false;
        }
        double squaredLengthBA = (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y);
        if (dotProduct > squaredLengthBA) {
            return false;
        }
        return true;
    }


}
