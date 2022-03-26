package com.slangmaster;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

public class Database {
    final private ArrayList<Slang> slangList;
    final public Index definitionIndex;
    final public Index meaningIndex;
    final public History history;

    Database(ArrayList<Slang> slangList) {
        this.slangList = slangList;
        this.definitionIndex = new Index();
        this.meaningIndex = new Index();
        this.history = new History();
    }

    Database(ArrayList<Slang> slangList, Index definitionIndex, Index meaningIndex, History history) {
        this.slangList = slangList;
        this.definitionIndex = Objects.requireNonNullElse(definitionIndex, new Index());
        this.meaningIndex = Objects.requireNonNullElse(meaningIndex, new Index());
        this.history = Objects.requireNonNullElse(history, new History());
    }

    public Slang queryByDefinition(String word) {
        return query(word, definitionIndex, Slang::word);
    }

    public Slang queryByMeaning(String word) {
        return query(
                word,
                meaningIndex,
                slang -> slang.meanings().stream()
                        .filter(s -> s.equals(word))
                        .findAny()
                        .orElse("")
        );
    }

    private Slang query(String word, Index index, Function<Slang, String> methodPref) {
        int pos = index.find(word);
        if (pos >= 0) {
            return slangList.get(pos);
        }
        for (int i = 0; i < slangList.size(); ++i) {
            Slang slang = slangList.get(i);
            if (methodPref.apply(slang).equals(word)) {
                index.add(word, i);
                return slang;
            }
        }
        return null;
    }
}
