package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGeometryPuzzle {

    @Test
    public void testUserInputConvexShape() {
        List<Coordinate> randomCoordinates = new ArrayList<>();
        randomCoordinates.add(new Coordinate(1,1));
        randomCoordinates.add(new Coordinate(5,1));
        randomCoordinates.add(new Coordinate(5,5));

        Shape shape = new Shape();
        shape.coordinates.addAll(randomCoordinates);
        assertTrue(shape.isPointInPolygon(randomCoordinates.get(0)));
        assertTrue(shape.isPointInPolygon(randomCoordinates.get(1)));
        assertTrue(shape.isPointInPolygon(randomCoordinates.get(2)));
        // within
        assertTrue(shape.isPointInPolygon(new Coordinate(4,1)));
        assertFalse(shape.isPointInPolygon(new Coordinate(12,45)));
    }
}
