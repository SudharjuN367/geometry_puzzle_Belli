package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class RandomConvexShapeGenerator {
    private static final int MIN_COORDINATES = 3;
    private static final int MAX_COORDINATES = 8;
    private static final int COORDINATE_RANGE = 20; // Range of coordinates will be from -COORDINATE_RANGE to COORDINATE_RANGE

    public static void main(String[] args) {
        List<Coordinate> convexShape = generateRandomConvexShape();
        System.out.println("Random Convex Shape:");
        for (int i = 0; i < convexShape.size(); i++) {
            Coordinate current = convexShape.get(i);
            Coordinate next = convexShape.get((i + 1) % convexShape.size());
            System.out.println("Edge " + i + ": " + current + " to " + next);
        }
    }

    public static List<Coordinate> generateRandomConvexShape() {
        Random random = new Random();
        int numCoordinates = MIN_COORDINATES + random.nextInt(MAX_COORDINATES - MIN_COORDINATES + 1);
        List<Coordinate> coordinates = new ArrayList<>();

        // Generate random coordinates
        for (int i = 0; i < numCoordinates; i++) {
            int x = random.nextInt(2 * COORDINATE_RANGE + 1) - COORDINATE_RANGE;
            int y = random.nextInt(2 * COORDINATE_RANGE + 1) - COORDINATE_RANGE;
            coordinates.add(new Coordinate(x, y));
        }

        // Ensure convexity
        return ensureConvexity(coordinates);
    }

    private static List<Coordinate> ensureConvexity(List<Coordinate> coordinates) {
        // Sort the coordinates based on the lowest y-coordinate, then the angle with respect to the lowest y-coordinate
        Coordinate lowest = findLowestYCoordinate(coordinates);
        Collections.sort(coordinates, (p1, p2) -> {
            if (p1 == lowest) return -1;
            if (p2 == lowest) return 1;
            double angle1 = Math.atan2(p1.y - lowest.y, p1.x - lowest.x);
            double angle2 = Math.atan2(p2.y - lowest.y, p2.x - lowest.x);
            if (angle1 < angle2) return -1;
            if (angle1 > angle2) return 1;
            return Double.compare(distanceSquared(lowest, p1), distanceSquared(lowest, p2));
        });

        // Apply Graham's scan to find the convex hull
        Stack<Coordinate> stack = new Stack<>();
        stack.push(coordinates.get(0));
        stack.push(coordinates.get(1));

        for (int i = 2; i < coordinates.size(); i++) {
            Coordinate top = stack.pop();
            while (!isConvex(stack.peek(), top, coordinates.get(i))) {
                top = stack.pop();
            }
            stack.push(top);
            stack.push(coordinates.get(i));
        }

        List<Coordinate> convexHull = new ArrayList<>(stack);

        return convexHull;
    }

    private static Coordinate findLowestYCoordinate(List<Coordinate> Coordinates) {
        Coordinate lowest = Coordinates.get(0);
        for (Coordinate Coordinate : Coordinates) {
            if (Coordinate.y < lowest.y || (Coordinate.y == lowest.y && Coordinate.x < lowest.x)) {
                lowest = Coordinate;
            }
        }
        return lowest;
    }

    private static boolean isConvex(Coordinate p1, Coordinate p2, Coordinate p3) {
        long crossProduct = (long) ((long) (p2.x - p1.x) * (p3.y - p1.y) - (long) (p2.y - p1.y) * (p3.x - p1.x));
        return crossProduct >= 0;
    }

    private static long distanceSquared(Coordinate p1, Coordinate p2) {
        return (long) ((long) (p2.x - p1.x) * (p2.x - p1.x) + (long) (p2.y - p1.y) * (p2.y - p1.y));
    }

   
}
