package com.slangmaster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Interactor {

    final private BufferedReader reader;

    Interactor() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    private static void print(String message) {
        System.out.print(message);
    }

    public void pause() {
        try {
            this.reader.readLine();
        } catch (IOException ignored) {
        }
    }

    public int getInt(String message, int limit) {
        int cmd = 0;
        while (cmd <= 0 || cmd > limit) {
            String line = getString(message);
            try {
                cmd = Integer.parseInt(line);
            } catch (NumberFormatException ignored) {
                cmd = 0;
            }
        }
        return cmd;
    }

    public String getStringCanBeEmpty(String message) {
        String line = null;
        while (line == null) {
            try {
                print(message);
                line = this.reader.readLine();
            } catch (IOException ignored) {
            }
        }
        return line;
    }

    public String getString(String message) {
        String s = getStringCanBeEmpty(message);
        while (s.isBlank()) {
            s = getStringCanBeEmpty(message);
        }
        return s;
    }
}

