package com.slangmaster;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Database {
    public History history;
    public Random random;
    private ArrayList<Slang> slangList;
    private Index definitionIndex;
    private Index meaningIndex;

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

    private <T> int findIndex(T value, BiFunction<? super T, Slang, Boolean> predicate) {
        for (int i = 0; i < slangList.size(); ++i) {
            if (predicate.apply(value, slangList.get(i))) {
                return i;
            }
        }
        return -1;
    }


    public <T> Slang find(T value, BiFunction<? super T, Slang, Boolean> predicate) {
        int index = findIndex(value, predicate);
        return index < 0 ? null : slangList.get(index);
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

    public boolean remove(String word) {
        int pos = findIndex(word, (w, s) -> w.equals(s.word()));
        if (pos < 0) {
            return false;
        }

        removeFromIndex(definitionIndex, word, pos);
        for (final String meaning : slangList.get(pos).meanings()) {
            removeFromIndex(meaningIndex, meaning, pos);
        }

        slangList.remove(pos);
        return true;
    }

    private void removeFromIndex(Index index, String word, int position) {
        for (final var entry : index.map.entrySet()) {
            if (entry.getValue() > position) {
                entry.setValue(entry.getValue() - 1);
            }
        }
        index.map.remove(word);

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
        pos = findIndex(word, (w, s) -> methodPref.apply(s).equals(w));
        if (pos >= 0) {
            index.add(word, pos);
        }
        Slang slang = pos >= 0 ? slangList.get(pos) : null;
        history.add(slang, word);
        return slang;
    }
}
