package com.slangmaster;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public record Slang(String word, ArrayList<String> meanings) {

    public Slang parse(String source) throws ParseException {
        String[] parts = source.split("`");

        if (parts.length < 2) {
            throw new ParseException(source, 0);
        }
        return new Slang(parts[0], new ArrayList<>(Arrays.asList(parts[1].split("\\|"))));
    }

    @Override
    public String toString() {
        String meaning = this.meanings
                .stream()
                .reduce((acc, n) -> acc + " | " + n)
                .orElse("<nil>");
        return String.format(Locale.getDefault(), "%s || %s", this.word, meaning);
    }
}