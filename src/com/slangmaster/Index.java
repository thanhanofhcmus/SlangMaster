package com.slangmaster;

import java.util.HashMap;

public class Index {
    public final HashMap<String, Integer> map = new HashMap<>();

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
}
