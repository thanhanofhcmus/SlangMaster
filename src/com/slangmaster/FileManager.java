package com.slangmaster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class FileManager {
    public static ArrayList<Slang> readFile(String fileName) {
        ArrayList<Slang> slangList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while (line != null) {
                try {
                    slangList.add(Slang.parse(line));
                } catch (ParseException ignored) {
                }
                finally {
                    line = reader.readLine();
                }
            }
        } catch (IOException ignored) {
        }

        return slangList;
    }
}
