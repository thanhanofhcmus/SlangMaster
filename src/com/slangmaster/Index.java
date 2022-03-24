package com.slangmaster;

import java.util.HashMap;

public class Index {
    public final HashMap<String, Integer> map = new HashMap<>();

    public boolean add(String word, int position) {
        if (map.containsKey(word)) {
            return false;
        }
        map.put(word, position);
        return true;
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
