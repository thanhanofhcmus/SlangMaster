package com.slangmaster;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileManager {
    public static ArrayList<Slang> readFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.lines()
                    .map(Slang::parse)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException ignored) {
            return null;
        }
    }

    public static void writeIndex(String filename, Index index) {
        try (BufferedWriter writer = new BufferedWriter((new FileWriter(filename)))) {
            writer.write(index.toCSVList());
        } catch (IOException ignored) {
        }
    }

    public static Index readIndex(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            HashMap<String, Integer> map = new HashMap<>();
            reader .lines()
                    .map(Index::fromCSVItem)
                    .filter(Objects::nonNull)
                    .forEach(e -> map.put(e.getKey(), e.getValue()));
            return new Index(map);
        } catch (IOException ignored) {
            return null;
        }
    }
}
