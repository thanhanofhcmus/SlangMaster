package com.slangmaster;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Index {
    public final HashMap<String, Integer> map;

    Index() {
        this.map = new HashMap<>();
    }

    Index(HashMap<String, Integer> map) {
        this.map = map;
    }

    public void add(String word, int position) {
        if (map.containsKey(word)) {
            return;
        }
        map.put(word, position);
    }

    public int find(String word) {
        return map.getOrDefault(word, -1);
    }

    public int remove(String word) {
        return map.remove(word);
    }

    public int update(String word, int position) {
        try {
            return map.replace(word, position);
        } catch (NullPointerException ignored) {
            return -1;
        }
    }

    public String toCSVList() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            builder.append(entry.getKey())
                    .append(',')
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }

    public static HashMap.Entry<String, Integer> fromCSVItem(String source) {
        String[] parts = source.split(",");
        try {
            return Map.entry(Objects.requireNonNull(parts[0]), Integer.parseInt(parts[1]));
        } catch (NullPointerException ignored) {
            return null;
        }
    }
}
