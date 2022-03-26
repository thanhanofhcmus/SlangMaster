package com.slangmaster;

public class Main {

    public static void main(String[] args) {
        UI ui = new UI(new Database(FileManager.readFile("sample.txt")));
        ui.run();
    }
}
