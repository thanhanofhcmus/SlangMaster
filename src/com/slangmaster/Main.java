package com.slangmaster;

public class Main {
    public static void main(String[] args) {
        Index index = new Index();

        index.add("A", 1);
        index.add("B", 2);
        index.add("C", 3);

        index.map.containsKey("A");
    }
}
