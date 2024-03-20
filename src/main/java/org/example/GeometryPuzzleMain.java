package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GeometryPuzzleMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the GIC geometry puzzle app");
        System.out.println("[1] Create a custom shape");
        System.out.println("[2] Generate a random shape");
        System.out.print("Please select an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Shape shape = new Shape();
        if (option == 1) {
            shape = createCustomShape(scanner);
        } else if (option == 2) {
            shape = generateRandomShape();
        }

        System.out.println("Your finalized shape is:");
        for (int i = 0; i < shape.coordinates.size(); i++) {
            Coordinate coord = shape.coordinates.get(i);
            System.out.println((i + 1) + ":(" + coord.x + "," + coord.y + ")");
        }

        while (true) {
            System.out.print("Please key in test coordinates in x y format or enter # to quit the game: ");
            String input = scanner.nextLine().trim();
            if (input.equals("#")) {
                System.out.println("Thank you for playing the GIC geometry puzzle app\n" +
                        "Have a nice day!");
                break;
            }
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Invalid input. Please enter valid coordinates.");
                continue;
            }
            try {
                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                if (shape.isPointInPolygon(new Coordinate(x,y))) {
                    System.out.println("Coordinates (" + x + " " + y + ") is within your finalized shape");
                } else {
                    System.out.println("Sorry, coordinates (" + x + " " + y + ") is outside of your finalized shape");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid coordinates.");
            }
        }
    }

    private static Shape generateRandomShape() {
        List<Coordinate> coordinates = RandomConvexShapeGenerator.generateRandomConvexShape();
        Shape shape = new Shape();
        shape.coordinates.addAll(coordinates);
        return shape;
    }

    public static Shape createCustomShape(Scanner scanner) {
        Shape shape = new Shape();

        int i = 1;
        while (true) {
            System.out.print("Please enter coordinates " + i + " in x y format: ");
            String input = scanner.nextLine().trim();
            if (input.equals("#")) {
                if (shape.isValid()) {
                    System.out.println("Your current shape is valid and is complete");
                    break;
                } else {
                    System.out.println("Your current shape is incomplete");
                    continue;
                }
            }
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Invalid input. Please enter valid coordinates.");
                continue;
            }
            try {
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                if(shape.coordinates.size() < 2) {
                    shape.coordinates.add(new Coordinate(x, y));
                    System.out.println("Your current shape is incomplete");
                    printCoordinates(shape.coordinates);
                } else {
                    boolean alreadyExists = false;
                    if (shape.coordinates.contains(new Coordinate(x, y))) {
                        System.out.println("New coordinates (" + x + "," + y + ") is invalid!!!");
                        System.out.println("Not adding new coordinates to the current shape.");
                        continue;
                    }
                    shape.coordinates.add(new Coordinate(x, y));
                    if (!shape.isValid()) {
                        System.out.println("New coordinates (" + x + "," + y + ") is invalid!!!");
                        System.out.println("Not adding new coordinates to the current shape.");
                        shape.coordinates.removeLast();
                        continue;
                    }else {
                        System.out.println("Your current shape is valid and is complete");
                        printCoordinates(shape.coordinates);
                        System.out.print("Please enter # to finalize or");

                    }
                }

                i++;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid coordinates.");
            }
        }
        return shape;
    }

    private static void printCoordinates(List<Coordinate> coordinates) {
        int index = 1;
        for(Coordinate coordinate : coordinates) {
            System.out.println(index +": " + coordinate);
        }
    }



}
