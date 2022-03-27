package com.slangmaster;

public class Main {

    public static void main(String[] args) {
        UI ui = new UI(
                Constants.SLANG_FILENAME,
                Constants.DEFINITION_INDEX_FILENAME,
                Constants.MEANINGS_INDEX_FILENAME
        );
        ui.run();
    }
}
