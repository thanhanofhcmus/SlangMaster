package com.slangmaster;

import java.util.ArrayList;

public class Database {
    final private ArrayList<Slang> slangList = new ArrayList<>();
    final private Index index = new Index();

    public Slang queryByDefinition(String word) {
        int pos = index.find(word);
        if (pos >= 0) {
            return slangList.get(pos);
        }
        return slangList.get(0);
    }
}
