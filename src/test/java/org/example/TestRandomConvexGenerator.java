package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestRandomConvexGenerator {

    @Test
    public void testGenerateRandomConvexShape() {
        List<Coordinate> randomCoordinates = RandomConvexShapeGenerator.generateRandomConvexShape();
        assertTrue(randomCoordinates.size() >= 3);
        Shape shape = new Shape();
        shape.coordinates.addAll(randomCoordinates);
        assertTrue(shape.isPointInPolygon(randomCoordinates.get(0)));
        assertTrue(shape.isPointInPolygon(randomCoordinates.get(1)));
        assertTrue(shape.isPointInPolygon(randomCoordinates.get(2)));
    }
}
