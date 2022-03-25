package com.slangmaster;

import java.util.ArrayList;

public class Database {
    final private ArrayList<Slang> slangList;
    final private Index index;

    Database(ArrayList<Slang> slangList) {
        this.slangList = slangList;
        this.index = new Index();
    }

    Database(ArrayList<Slang> slangList, Index index) {
        this.slangList = slangList;
        this.index = index;
    }

    public Slang queryByDefinition(String word) {
        int pos = index.find(word);
        if (pos >= 0) {
            return slangList.get(pos);
        }
        pos = slangList.indexOf(word);
        if (pos < 0) {
            return null;
        }
        Slang slang = slangList.get(pos);
        index.add(slang.word(), pos);
        return slang;
    }
}
