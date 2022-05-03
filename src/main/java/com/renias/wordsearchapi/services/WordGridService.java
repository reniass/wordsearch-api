package com.renias.wordsearchapi.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordGridService {

    public void displayGrid(char[][] contents) {
        int gridSize = contents[0].length;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(contents[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public char[][] generateGrid(int gridSize, List<String> words) {
        char[][] contents = new char[gridSize][gridSize];
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                contents[i][j] = '_';
                coordinates.add(new Coordinate(i, j));
            }
        }

        for (String word : words) {
            int wordLength = word.length();
            Collections.shuffle(coordinates);
            for (Coordinate coordinate : coordinates) {
                int x = coordinate.x;
                int y = coordinate.y;
                Direction direction = getDirectionForFit(word, coordinate, contents);
                if (direction == null) {
                    continue;
                } else {
                    switch (direction) {
                        case DIAGONAL:
                            for (int i = 0; i < wordLength; i++) {
                                contents[x + i][y + i] = word.charAt(i);
                            }
                            break;
                        case VERTICAL:
                            for (int i = 0; i < wordLength; i++) {
                                contents[x + i][y] = word.charAt(i);
                            }
                            break;
                        case HORIZONTAL:
                            for (int i = 0; i < wordLength; i++) {
                                contents[x][y + i] = word.charAt(i);
                            }
                            break;
                        case DIAGONAL_INVERSE:
                            for (int i = 0; i < wordLength; i++) {
                                contents[x - i][y - i] = word.charAt(i);
                            }
                            break;
                        case VERTICAL_INVERSE:
                            for (int i = 0; i < wordLength; i++) {
                                contents[x - i][y] = word.charAt(i);
                            }
                            break;
                        case HORIZONTAL_INVERSE:
                            for (int i = 0; i < wordLength; i++) {
                                contents[x][y - i] = word.charAt(i);
                            }
                            break;
                    }
                    break;
                }
            }
        }
        //randomFillGrid(contents);
        return contents;
    }


    boolean doesFit(Coordinate coordinate, Direction direction, String word, char[][] contents) {
        int gridSize = contents[0].length;


        int x = coordinate.x;
        int y = coordinate.y;

        switch (direction) {
            case DIAGONAL:
                if ((x - 1) + word.length() < gridSize && (y - 1) + word.length() < gridSize ) {
                    for (int i = 0; i < word.length(); i++) {
                        if (contents[x + i][y + i] != '_' && contents[x + i][y + i] != word.charAt(i)){
                            return false;
                        }
                    }
                    return true;
                }
                break;
            case VERTICAL:
                if ((x - 1) + word.length() < gridSize) {
                    for (int i = 0; i < word.length(); i++) {
                        if (contents[x + i][y] != '_' && contents[x + i][y] != word.charAt(i)) {
                            return false;
                        }
                    }
                    return true;
                }
                break;
            case HORIZONTAL:
                if ((y - 1) + word.length() < gridSize) {
                    for (int i = 0; i < word.length(); i++) {
                        if (contents[x][y + i] != '_' && contents[x][y + i] != word.charAt(i)) {
                            return false;
                        }
                    }
                    return true;
                }
                break;
            case DIAGONAL_INVERSE:
                if ((x + 1) - word.length() >= 0 && (y + 1) - word.length() >= 0) {
                    for (int i = 0; i < word.length(); i++) {
                        if (contents[x - i][y - i] != '_' && contents[x - i][y - i] != word.charAt(i)) {
                            return false;
                        }
                    }
                    return true;
                }
                break;
            case VERTICAL_INVERSE:
                if ((x + 1) - word.length() >= 0) {
                    for (int i = 0; i < word.length(); i++) {
                        if (contents[x - i][y] != '_' && contents[x - i][y] != word.charAt(i)) {
                            return false;
                        }
                    }
                    return true;
                }
                break;
            case HORIZONTAL_INVERSE:
                if ((y - 1) - word.length() >= 0) {
                    for (int i = 0; i < word.length(); i++) {
                        if (contents[x][y - i] != '_' && contents[x][y - i] != word.charAt(i)) {
                            return false;
                        }
                    }
                    return true;
                }
                break;
        }
        return false;
    }

    public Direction getDirectionForFit(String word, Coordinate coordinate, char[][] contents) {
        List<Direction> directionList = Arrays.asList(Direction.values());
        Collections.shuffle(directionList);

        for (Direction direction: directionList) {
            if (doesFit(coordinate, direction, word, contents)){
                return direction;
            }
        }
        return null;
    }


    private void randomFillGrid(char[][] contents) {
        int gridSizeHorizontally = contents[0].length;
        int gridSizeVertically = contents.length;
        String alphabet = "ABCDEFGHIJKLMOPQRSTUVWXYZ";
        char[] lettersArray = alphabet.toCharArray();
        for (int i = 0; i < gridSizeVertically; i++) {
            for (int j = 0; j < gridSizeHorizontally; j++) {
                if (contents[i][j] == '_') {
                    char randomLetter = lettersArray[ThreadLocalRandom.current().nextInt(0,25)];
                    contents[i][j] = randomLetter;
                }

            }
        }
    }

    private class Coordinate {
        private int x;
        private int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private enum Direction {
        HORIZONTAL,
        HORIZONTAL_INVERSE,
        VERTICAL,
        VERTICAL_INVERSE,
        DIAGONAL,
        DIAGONAL_INVERSE
    }

}
