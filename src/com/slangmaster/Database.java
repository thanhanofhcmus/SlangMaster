package com.slangmaster;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

public class Database {
    private ArrayList<Slang> slangList;
    public Index definitionIndex;
    public Index meaningIndex;
    public History history;
    public Random random;

    Database(ArrayList<Slang> slangList) {
        this.slangList = slangList;
        this.definitionIndex = new Index();
        this.meaningIndex = new Index();
        this.history = new History();
        this.random = new Random();
    }

    Database(ArrayList<Slang> slangList, Index definitionIndex, Index meaningIndex, History history) {
        this.slangList = slangList;
        this.definitionIndex = Objects.requireNonNullElse(definitionIndex, new Index());
        this.meaningIndex = Objects.requireNonNullElse(meaningIndex, new Index());
        this.history = Objects.requireNonNullElse(history, new History());
        this.random = new Random();
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

    public void reset(ArrayList<Slang> slangList) {
        this.slangList = slangList;
        this.definitionIndex = new Index();
        this.meaningIndex = new Index();
        this.history = new History();
    }

    public Slang randomSlang() {
        return slangList.get(random.nextInt(slangList.size()));
    }

    private Slang query(String word, Index index, Function<Slang, String> methodPref) {
        int pos = index.find(word);
        if (pos >= 0) {
            Slang slang = slangList.get(pos);
            history.add(slang, word);
            return slang;
        }
        for (int i = 0; i < slangList.size(); ++i) {
            Slang slang = slangList.get(i);
            if (methodPref.apply(slang).equals(word)) {
                index.add(word, i);
                history.add(slang, word);
                return slang;
            }
        }
        history.add(null, word);
        return null;
    }
}
