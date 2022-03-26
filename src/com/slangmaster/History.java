package com.slangmaster;

import java.util.ArrayList;

public class History {

    public record Item(Slang slang, String searchTerm) {}

    final private ArrayList<Item> items;

    public History() {
        this.items = new ArrayList<>();
    }

    public History(ArrayList<Item> items) {
        this.items = items;
    }

    public void add(Slang slang, String searchTerm) {
        this.items.add(new Item(slang, searchTerm));
    }

    public final ArrayList<Item> getItems() {
        return items;
    }
}
