package com.slangmaster;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Database {
    private ArrayList<Slang> slangList;
    private Index definitionIndex;
    private Index meaningIndex;
    public History history;
    public Random random;

    Database(ArrayList<Slang> slangList, Index definitionIndex, Index meaningIndex, History history) {
        this.slangList = slangList;
        this.definitionIndex = Objects.requireNonNullElse(definitionIndex, new Index());
        this.meaningIndex = Objects.requireNonNullElse(meaningIndex, new Index());
        this.history = Objects.requireNonNullElse(history, new History());
        this.random = new Random();
    }

    public ArrayList<Slang> getSlangList() {
        return slangList;
    }

    public Index getDefinitionIndex() {
        return definitionIndex;
    }

    public Index getMeaningIndex() {
        return meaningIndex;
    }

    public <T> Slang find(T value, BiFunction<? super  T, Slang, Boolean> predicate) {
        for (final Slang slang : slangList) {
            if (predicate.apply(value, slang)) {
                return slang;
            }
        }
        return null;
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

    public void insertUnChecked(Slang slang) {
        slangList.add(slang);
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

    private Slang query(String word, Index index, final Function<Slang, String> methodPref) {
        int pos = index.find(word);
        if (pos >= 0) {
            Slang slang = slangList.get(pos);
            history.add(slang, word);
            return slang;
        }
        Slang slang = find(word, (w, s) -> methodPref.apply(s).equals(w));
        history.add(slang, word);
        return slang;
    }
}
