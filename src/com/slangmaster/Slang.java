package com.slangmaster;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public record Slang(String word, ArrayList<String> meanings) {

    public static Slang parse(String source) {
        String[] parts = source.split("`");
        if (parts.length < 2) {
            return null;
        }
        return new Slang(parts[0].trim(), new ArrayList<>(
                Arrays.stream(parts[1].split("\\|"))
                        .map(String::trim)
                        .collect(Collectors.toList())
        ));
    }

    public String meaningsString() {
        return this.meanings
                .stream()
                .reduce((acc, n) -> acc + " | " + n)
                .orElse("<nil>");
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s || %s", this.word, this.meaningsString());
    }
}
